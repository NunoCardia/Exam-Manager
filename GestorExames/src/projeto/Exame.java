package projeto;

import javax.print.Doc;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public class Exame implements Serializable{
    protected Disciplina disciplina;
    protected Date date;
    protected int duracao;
    protected Sala sala;
    protected Docente docenteResponsavel;
    protected ArrayList<Docente> vigilantes;
    protected ArrayList<NaoDocente> funcionarios;
    protected HashMap<Aluno,Integer> notas;
    protected String tipoExame;
    protected Curso curso;
    private static final long serialVersionUID = 8376677555692791479L;

    public Exame() {
    }

    public Exame(Disciplina disciplina, Date date, int duracao, Sala sala, Docente docenteResponsavel, ArrayList<Docente> vigilantes, ArrayList<NaoDocente> funcionarios, HashMap<Aluno, Integer> notas, String tipoExame,Curso curso) {
        this.disciplina = disciplina;
        this.date = date;
        this.duracao = duracao;
        this.sala = sala;
        this.docenteResponsavel = docenteResponsavel;
        this.vigilantes = vigilantes;
        this.funcionarios = funcionarios;
        this.notas = notas;
        this.tipoExame = tipoExame;
        this.curso = curso;
    }


    public void menuExames(ArrayList<Pessoa> utilizadores,ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas){
        int option;
        String disciplina,curso,epoca,parametro;
        boolean control;
        Scanner sc = new Scanner(System.in);
        Departamento dep = new Departamento();
        Exame exa = new Exame(),temp;
        boolean ctrl;
        do {
            System.out.println("Menu de exames\n1 - Criar Exame\n2 - Lançar as notas de um exame\n3 - Alterar um exame já marcado\n4 - Remover exame\n0 - voltar ao menu inicial");
            option = Integer.parseInt(sc.nextLine());
            switch (option){
                case 1:
                    exames.add(novoExame(utilizadores,cursos,exames,salas));
                    System.out.println("Exame adicionado com sucesso");
                    break;
                case 2:
                    do {
                        System.out.println("Curso do exame: ");
                        curso = sc.nextLine();
                        System.out.println("Disciplina do exame: ");
                        disciplina = sc.nextLine();
                        System.out.println("Época do exame: ");
                        epoca = sc.nextLine();//mandar o exame como argumento em vez do curso disciplina epoca
                        control = exa.inExameList(exames,cursos,disciplina,curso,epoca);
                        temp = exa.returnExame(exames,disciplina,epoca,curso);
                    } while(control || temp == null);
                    lancarNotas(temp);
                    break;
                case 3:
                    System.out.println("Disciplina do exame: ");
                    disciplina = sc.nextLine();
                    System.out.println("Curso do exame: ");
                    curso = sc.nextLine();
                    System.out.println("Época do exame: ");
                    epoca = sc.nextLine();
                    do{
                        System.out.println("Parâmetro a alterar");
                        parametro = sc.nextLine();
                        control = checkParameter(parametro);
                    }while(!control);
                    alterarExame(utilizadores,cursos,exames,salas,parametro,disciplina,curso,epoca);//mandar o exame como argumento em vez do curso disciplina epoca
                    break;
                case 4:
                    do{
                        System.out.println("Nome do curso: ");
                        curso = sc.nextLine();
                        System.out.println("Nome da disciplina: ");
                        disciplina = sc.nextLine();
                        System.out.println("Época: ");
                        epoca = sc.nextLine();
                        ctrl = exa.inExameList(exames,cursos,disciplina,curso,epoca);
                        temp = exa.returnExame(exames,disciplina,epoca,curso);
                    }while(!ctrl || temp == null);
                    for(Exame ex: exames){
                        if(ex.equals(temp)){
                            exames.remove(ex);
                            System.out.println("Exame removido com sucesso");
                        }
                    }
                    recolocarDocentes(exames);
                case 0:
                    dep.handleChoice(utilizadores,cursos,exames,salas);
                    break;
                default:
                    System.out.println("Parâmetro não reconhecido");
                    break;

            }
        }while(option!=0);
    }

    private void recolocarDocentes(ArrayList<Exame> exames) {
        for(Exame ex: exames){
            for(Docente doc: ex.getDisciplina().getDocentesAuxiliares()){
                Date exDuration = ex.addMinutesToDate(ex.getDuracao(), ex.getDate());
                if(doc.checkDocenteDisponivel(ex,doc,disciplina,ex.getDate(),exDuration)){
                    ex.getVigilantes().add(doc);
                    System.out.println("Docente que estava indisponível para o exame foi agora adicionado");
                }
            }
        }
    }

    private boolean checkExame(ArrayList<Exame> exames, String curso, String disciplina, String epoca) {
        for(Exame ex: exames){
            System.out.println(ex.toString());
        }
        for(Exame ex: exames){
            System.out.println(ex.getCurso().getNome());
            System.out.println(ex.getDisciplina().getNome());
            System.out.println(ex.getTipoExame());
            if(ex.getCurso().getNome().equalsIgnoreCase(curso) && ex.getDisciplina().getNome().equalsIgnoreCase(disciplina) && ex.getTipoExame().equalsIgnoreCase(epoca)){
                return true;
            }
        }
        return false;
    }

    public boolean inscreverAluno(String epoca,Aluno al, int nAnos){
        switch (epoca.toLowerCase()){
            case "normal":
                return true;
            case "recurso":
                return true;
            case "especial":
                if(al.getAnoMatricula() == nAnos || al.getRegime().equalsIgnoreCase("trabalhor-estudante") || al.getRegime().equalsIgnoreCase("atleta") ||
                        al.getRegime().equalsIgnoreCase("dirigente associativo")){
                    return true;
                }
        }
        return false;
    }

    public boolean checkParameter(String parameter){
        if(parameter.equalsIgnoreCase("disciplina")||parameter.equalsIgnoreCase("curso")||parameter.equalsIgnoreCase("data")||parameter.equalsIgnoreCase("duracao")||
                parameter.equalsIgnoreCase("sala")||parameter.equalsIgnoreCase("vigilantes")||parameter.equalsIgnoreCase("funcionarios")||parameter.equalsIgnoreCase("alunos")||
                parameter.equalsIgnoreCase("epoca")){
            return true;
        }
        else{
            System.out.println("Parâmetro não existente");
            return false;
        }
    }

    public HashMap<Aluno,Integer> checkInscricoesExame(Exame ex,String epoca,Disciplina dsc, int nAnos){
        HashMap<Aluno,Integer> alunos = new HashMap<Aluno,Integer>();
        switch (epoca.toLowerCase()){
            case "normal":
                for(Aluno al: dsc.getAlunos()){
                    alunos.put(al,-1);
                }
                return alunos;
            case "recurso":
                for(Aluno al: dsc.getAlunos()){
                    alunos.put(al,-1);
                }
                return alunos;
            case "especial":
                for(Aluno al: dsc.getAlunos()){
                    if(al.getAnoMatricula() == nAnos || al.getRegime().equalsIgnoreCase("trabalhor-estudante") || al.getRegime().equalsIgnoreCase("atleta") ||
                            al.getRegime().equalsIgnoreCase("dirigente associativo")){
                        alunos.put(al,-1);
                    }
                }
                return alunos;

        }
        return null;
    }

    public void alterarExame(ArrayList<Pessoa> utilizadores,ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas,String param,String disciplina,String curso,String epoca){
        String newValue,value2,response;
        int value,test,i;
        boolean control = false, control2 = false;
        long number;
        Curso csr = new Curso();
        Scanner sc = new Scanner(System.in);
        Exame ex = new Exame();
        Curso newCurso;
        Date exameDate = null;
        Sala newSala,sala = new Sala();
        Docente docente = new Docente();
        ex = returnExame(exames,disciplina,epoca,curso);
        switch (param.toLowerCase()){
            case "data": //verificar docentes e funcionarios
                System.out.println("Nova data de realização do exame: ");
                newValue = sc.nextLine();
                try {
                    exameDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(newValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("Nova duração do exame(minutos): ");
                value = Integer.parseInt(sc.nextLine());
                control = sala.verificaSala(salas,exames,ex.getSala().getNome(),exameDate,value);
                if(!control){
                    do {
                        System.out.println("Terá de mudar a sala de exame se quiser alterar a data do mesmo\nPretende fazê-lo(sim/nao)");
                        response = sc.nextLine();
                    }while(!response.equalsIgnoreCase("sim") && !response.equalsIgnoreCase("nao"));
                    if(response.equalsIgnoreCase("sim")){
                        do{
                            System.out.println("Nova sala para a realização do exame: ");
                            newValue = sc.nextLine();
                            control = sala.verificaSala(salas,exames,newValue,exameDate,value);
                            newSala = sala.returnSala(salas,newValue);
                        }while(!control);
                        ex.setSala(newSala);
                        System.out.println("Sala de exame alterada");
                    }
                    else break;
                }
                Date novaDuration = ex.addMinutesToDate(ex.getDuracao(),ex.getDate());
                Docente docente1 = new Docente();
                for(Exame exam: exames){
                    if(!exam.equals(ex)){
                        if(docente1.inVigilantesList(ex.getDocenteResponsavel(),exam.getVigilantes())){
                            System.out.println("Docente "+ex.getDocenteResponsavel().getNome()+" não pode vigiar o exame uma vez que já tem um exame marcado para essa hora e é o regente dessa cadeira");
                            exam.getVigilantes().remove(ex.getDocenteResponsavel());
                        }
                        for(Iterator<Docente> it = ex.getVigilantes().iterator();it.hasNext();){
                            Docente doc = it.next();
                            if(!docente.checkDocenteDisponivel(exam,doc,exam.getDisciplina(),exameDate,novaDuration)){
                                it.remove();
                            }
                        }
                    }
                }
                ex.setDate(exameDate);
                ex.setDuracao(value);
                System.out.println("Data do exame alterada com sucesso");
                System.out.println("Duração do exame alterada com sucesso");
                break;
            case "sala": //ver se a sala está ocupada
                do{
                    System.out.println("Nova sala para a realização do exame: ");
                    newValue = sc.nextLine();
                    control = sala.verificaSala(salas,exames,newValue,ex.getDate(),ex.getDuracao());
                    newSala = sala.returnSala(salas,newValue);
                }while(!control);
                ex.setSala(newSala);
                System.out.println("Sala de exame alterada");
                break;
            case "vigilantes": //remover não há problema adicionar ver disponibilidade
                control = false;
                Docente doc;
                Date sumDate;
                do {
                    System.out.println("Pretende remover ou adicionar vigilantes?");
                    newValue = sc.nextLine();
                }while(!newValue.equalsIgnoreCase("remover") && !newValue.equalsIgnoreCase("adicionar"));
                if(newValue.equals("remover")){
                    System.out.println("Indique o nome do vigilante a remover: ");
                    value2 = sc.nextLine();
                    if(ex.getDocenteResponsavel().getNome().equalsIgnoreCase(value2)){
                        System.out.println("Não pode remover o regente da cadeira");
                        break;
                    }
                    for(i=0;i < ex.getVigilantes().size();i++){
                        if(ex.getVigilantes().get(i).getNome().equals(value2)){
                            ex.getVigilantes().remove(i);
                            System.out.println("Vigilante removido com sucesso");
                            control = true;
                        }
                    }
                    if(!control){
                        System.out.println("Vigilante não está associado ao exame indicado");
                        break;
                    }
                }
                else if(newValue.equals("adicionar")){
                    control = false;
                    System.out.println("Indique o nome do vigilante a adicionar: ");
                    value2 = sc.nextLine();
                    for(Pessoa ps: utilizadores){
                        if(ps instanceof Docente){
                            if(((Docente) ps).getNome().equalsIgnoreCase(value2)){
                                doc = (Docente) ps;
                                if(docente.inVigilantesList(doc,ex.getVigilantes()) || doc.getNome().equalsIgnoreCase(ex.getDocenteResponsavel().getNome())){
                                    System.out.println("Vigilante ja pertence à lista de vigilantes do exame");
                                    break;
                                }
                                for(Exame ex1: exames){
                                    if(!ex1.equals(ex)){
                                        sumDate = addMinutesToDate(ex1.getDuracao(),ex1.getDate());
                                        if(docente.checkDocenteDisponivel(ex1,doc,ex1.getDisciplina(),ex1.getDate(),sumDate)){
                                            ex.getVigilantes().add((Docente) ps);
                                            System.out.println("Vigilante adicionado");
                                            control = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(!control){
                        System.out.println("Docente não existe no sistema");
                    }
                }
                break;
            case "funcionarios":
                control = false;
                do {
                    System.out.println("Pretende remover ou adicionar funcionarios?: ");
                    newValue = sc.nextLine();
                }while(!newValue.equals("remover") && !newValue.equals("adicionar"));
                if(newValue.equals("remover")){
                    System.out.println("Indique o número do funcionario a remover: ");
                    value2 = sc.nextLine();//todo protect
                    number = Long.parseLong(value2);
                    for(i=0;i < ex.getFuncionarios().size();i++){
                        if(ex.getFuncionarios().get(i).getNumero() == number){
                            ex.getFuncionarios().remove(i);
                            System.out.println("Funcionario removido com sucesso");
                            control = true;
                        }
                    }
                    if(!control){
                        System.out.println("Funcionario não está associado ao exame indicado");
                        break;
                    }
                }
                else if(newValue.equals("adicionar")){
                    control = false;
                    System.out.println("Indique o numero do funcionario a adicionar: ");
                    value2 = sc.nextLine();
                    number = Long.parseLong(value2);
                    for(Pessoa ps: utilizadores){
                        if(ps instanceof NaoDocente){
                            if(((NaoDocente) ps).getNumero() == number){
                                ex.getFuncionarios().add((NaoDocente) ps);
                                System.out.println("Funcionario adicionado com sucesso");
                                control = true;
                            }
                        }
                    }
                    if(!control){
                        System.out.println("Funcionario não existe no sistema");
                        break;
                    }
                }
                break;
            case "epoca":
                Disciplina disc = csr.parseDisciplina(cursos,disciplina,curso);
                newCurso = csr.parseCurso(cursos,curso);
                do {
                    System.out.println("Nova época do exame: ");
                    newValue = sc.nextLine();
                }while(!newValue.equalsIgnoreCase("normal")|| !newValue.equalsIgnoreCase("recurso")|| !newValue.equalsIgnoreCase("especial") || ex.getTipoExame().equalsIgnoreCase(newValue));
                ex.setTipoExame(newValue);
                ex.setNotas(checkInscricoesExame(ex,newValue,disc,newCurso.getDuracao()));
                System.out.println("Época de exame alterada\nFoi feita a inscrição dos alunos");
                break;

        }
    }

    public Exame novoExame(ArrayList<Pessoa> utilizadores,ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas){
        String curso,disc,epoca,docente,nDocente,novaSala,date;
        int controlo, checkInf,nDocentes,i,nFuncionarios,duracao,nAlunos;
        long aluno;
        boolean ctrl = false,ctrlSala = false,docAux = true;
        Date exameDate = null;
        Disciplina disciplina;
        ArrayList<Docente> vigilantes = new ArrayList<Docente>();
        ArrayList<NaoDocente> funcionarios = new ArrayList<NaoDocente>();
        Aluno newAluno  = new Aluno(),al;
        HashMap<Aluno,Integer> notas = new HashMap<Aluno,Integer>();
        Scanner sc = new Scanner(System.in);
        Sala sala = new Sala(), newSala;
        Docente doc = new Docente(),newDoc,DocenteResponsavel;
        NaoDocente ndoc = new NaoDocente(),newNdoc;
        Curso csr = new Curso(),finalCurso = new Curso();
        System.out.println("CRIAÇÃO DE NOVO EXAME");
        do{
            System.out.println("Nome do curso: ");
            curso = sc.nextLine();
            System.out.println("Nome da disciplina: ");
            disc = sc.nextLine();
            System.out.println("Época: ");
            epoca = sc.nextLine();
            ctrl = inExameList(exames,cursos,disc,curso,epoca);
        }while(!ctrl);
        disciplina = csr.parseDisciplina(cursos,disc,curso);
        finalCurso = csr.parseCurso(cursos,curso);
        //data e hora do exame ; duraçao em minutos do exame
        System.out.println("Data e hora do exame(yyyy-MM-dd HH:mm)");
        date = sc.nextLine();
        try {
            exameDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Duração do exame(minutos): ");
        duracao = Integer.parseInt(sc.nextLine());
        //docente responsável
        DocenteResponsavel = csr.getDocenteResponsavel(cursos,disc,curso);
        System.out.println("Docente responsável: "+DocenteResponsavel.getNome());
        //docentes vigilantes e funcionarios
        System.out.println("A adicionar docentes da disciplina à lista de vigilantes...");
        ctrl = false;
        for(Exame ex: exames) {
            Date exDuration = ex.addMinutesToDate(ex.getDuracao(), ex.getDate());
            Date novaDuration = ex.addMinutesToDate(duracao,exameDate);
            if ((exameDate.after(ex.getDate()) && exameDate.before(exDuration)) || (novaDuration.before(exDuration) && novaDuration.after(ex.getDate()))) {
                ctrl = true;
                if(doc.checkDocenteDisponivel(ex, DocenteResponsavel,disciplina,exameDate,exDuration)){
                    vigilantes.add(DocenteResponsavel);
                }
                for(i=0;i<disciplina.getDocentesAuxiliares().size();i++){
                    if(doc.checkDocenteDisponivel(ex,disciplina.getDocentesAuxiliares().get(i),disciplina,exameDate,exDuration)){
                        vigilantes.add(disciplina.getDocentesAuxiliares().get(i));
                    }
                }
                while (docAux){
                    System.out.println("Número de docentes adicionais para vigiar o exame: ");
                    nDocentes = Integer.parseInt(sc.nextLine());
                    docAux = false;
                    for(i=0;i<nDocentes;i++){
                        do{
                            System.out.println("Nome do docente: ");
                            docente = sc.nextLine();
                            newDoc = doc.parseDocente(utilizadores,docente);
                            if(doc.checkDocenteDisponivel(ex,newDoc,disciplina,exameDate,exDuration)){
                                vigilantes.add(newDoc);
                                System.out.println("Docente vigilante adicionado");
                                docAux = true;
                            }
                            else{
                                docAux = false;
                            }
                        }while(newDoc==null);
                    }
                }
            }
        }
        if(!ctrl){
            for(i=0;i<disciplina.getDocentesAuxiliares().size();i++){
                vigilantes.add(disciplina.getDocentesAuxiliares().get(i));
            }
            System.out.println("Professores auxiliares da disciplina adicionados à lista de vigilantes\nNúmero de docentes adicionais para vigiar o exame: ");
            nDocentes = Integer.parseInt(sc.nextLine());
            for(i=0;i<nDocentes;i++){
                do{
                    System.out.println("Nome do docente: ");
                    docente = sc.nextLine();
                    newDoc = doc.parseDocente(utilizadores,docente);
                    vigilantes.add(newDoc);
                    System.out.println("Docente vigilante adicionado");
                }while(newDoc==null);
            }
        }
        System.out.println(vigilantes.size()+" vigilantes adicionados ao exame de "+disc);
        System.out.println("Número de funcionários:");
        nFuncionarios = Integer.parseInt(sc.nextLine());
        for(i=0;i<nFuncionarios;i++){
            System.out.println("Número mecanicográfico do funcionário: ");
            nDocente = sc.nextLine();
            newNdoc = ndoc.checkNaoDocente(utilizadores,Long.parseLong(nDocente));
            if(newNdoc!=null){
                funcionarios.add(newNdoc);
                System.out.println("Funcionário adicionado");
            }
        }
        //sala
        do{
            System.out.println("Sala para realizar o exame: ");
            for(Sala sl: salas){
                System.out.println(sl.toString());
            }
            novaSala = sc.nextLine();
            ctrlSala = sala.verificaSala(salas,exames,novaSala,exameDate,duracao);
            newSala = sala.returnSala(salas,novaSala);
        }while(!ctrlSala);
        //associar alunos
        System.out.println("Número de alunos a associar ao exame: ");
        nAlunos = Integer.parseInt(sc.nextLine());
        for(i=0;i<nAlunos;i++){
            ctrl = false;
            while(!ctrl){
                System.out.println("Número do aluno("+(i+1)+"º"+"): ");
                aluno = Long.parseLong(sc.nextLine());
                al = newAluno.parseAluno(utilizadores,aluno);
                if(al==null){
                    ctrl = false;
                    System.out.println("Aluno não existe no sistema");
                }
                else{
                    ctrl = true;
                    if(!inscreverAluno(epoca,al,finalCurso.getDuracao())){
                        System.out.println("Aluno não pode ser inscrito no exame");
                    }
                    else{
                        notas.put(al,-1);
                        System.out.println("Aluno inscrito no exame");
                    }
                }
            }

        }
        System.out.println(notas.size()+" alunos inscritos no exame");
        return new Exame(disciplina,exameDate,duracao,newSala,DocenteResponsavel,vigilantes,funcionarios,notas,epoca,finalCurso);
    }


    public boolean inExameList(ArrayList<Exame> exames,ArrayList<Curso> cursos, String nomeDisciplina, String curso, String epoca){
        Curso csr = new Curso();
        int checkInf;
        switch (checkInf = (csr.checkCursoInf(cursos,nomeDisciplina,curso))){
            case -1:
                System.out.println("Não existe "+nomeDisciplina+" no curso: "+curso);
                return false;
            case 0:
                System.out.println("Não existe o curso: "+curso);
                return false;
            case 1:
                for (Exame exm: exames) {
                    if(exm.getDisciplina().getNome().equalsIgnoreCase(nomeDisciplina) && exm.getTipoExame().equalsIgnoreCase(epoca)){
                        System.out.println("Exame para a disciplina "+nomeDisciplina+" na época "+epoca+" já existe");
                        return false;
                    }
                }
                System.out.println("Não existe um exame marcado para a disciplina "+nomeDisciplina+" na época "+epoca);
                return true;

        }
        return false;
    }

    public Date addMinutesToDate(int minutes, Date beforeTime){
        final long ONE_MINUTE_IN_MILLIS = 60000;

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    public Exame returnExame(ArrayList<Exame> exames,String disciplina,String epoca,String curso){
        for(Exame ex: exames){
            if(ex.getDisciplina().getNome().equalsIgnoreCase(disciplina) && ex.getCurso().getNome().equalsIgnoreCase(curso) && ex.getTipoExame().equalsIgnoreCase(epoca)){
                return ex;
            }
        }
        return null;
    }

    public void lancarNotas(Exame ex){
        int nota;
        Scanner sc = new Scanner(System.in);
        if(!ex.getNotas().containsValue(-1)){
            System.out.println("Notas já lançadas");
            return;
        }
        else{
            for (Map.Entry<Aluno, Integer> entry : ex.getNotas().entrySet())
            {
                System.out.print(entry.getKey().getNumeroAluno()+": ");
                nota = Integer.parseInt(sc.nextLine());
                while (nota< 0 || nota>20){
                    System.out.println("A nota que inseriu não é válida, por favor introduza uma nota válida: ");
                    nota = Integer.parseInt(sc.nextLine());
                }
                entry.setValue(nota);
            }
        }


        System.out.println("Notas do exame de "+ex.getDisciplina().getNome()+" lançadas");
    }


    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Docente getDocenteResponsavel() {
        return docenteResponsavel;
    }

    public void setDocenteResponsavel(Docente docenteResponsavel) {
        this.docenteResponsavel = docenteResponsavel;
    }

    public ArrayList<Docente> getVigilantes() {
        return vigilantes;
    }

    public void setVigilantes(ArrayList<Docente> vigilantes) {
        this.vigilantes = vigilantes;
    }

    public ArrayList<NaoDocente> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(ArrayList<NaoDocente> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public HashMap<Aluno, Integer> getNotas() {
        return notas;
    }

    public void setNotas(HashMap<Aluno, Integer> notas) {
        this.notas = notas;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
       return "Exame de "+disciplina.getNome()+"\tData e hora: "+date1.format(date)+"\tDuracao: "+duracao+"\tSala: "+sala.getNome()+"\tNúmero de vigilantes convocados: "
               +vigilantes.size()+"\tAlunos inscritos: "+notas.size();
    }
}
