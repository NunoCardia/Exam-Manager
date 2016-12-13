package projeto;

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
        do {
            System.out.println("Menu de exames\n1 - Criar Exame\n2 - Lançar as notas de um exame\n3 - Alterar um exame já marcado\n0 - voltar ao menu inicial");
            option = Integer.parseInt(sc.nextLine());
            switch (option){
                case 1:
                    exames.add(novoExame(utilizadores,cursos,exames,salas));
                    System.out.println("Exame adicionado com sucesso");
                    break;
                case 2:
                    do {
                        System.out.println("Disciplina do exame: ");
                        disciplina = sc.nextLine();
                        System.out.println("Curso do exame: ");
                        curso = sc.nextLine();
                        System.out.println("Época do exame: ");
                        epoca = sc.nextLine();
                        control = checkExame(exames,curso,disciplina,epoca);
                    } while(!control);
                    lancarNotas(exames,disciplina,epoca,curso);
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
                    alterarExame(utilizadores,cursos,exames,salas,parametro,disciplina,curso,epoca);
                    break;
                case 0:
                    dep.handleChoice(utilizadores,cursos,exames,salas);
                    break;
                default:
                    System.out.println("Parâmetro não reconhecido");
                    break;

            }
        }while(option!=0);
    }

    private boolean checkExame(ArrayList<Exame> exames, String curso, String disciplina, String epoca) {
        System.out.println("curso: "+curso+" disciplina: "+disciplina+" epoca: "+epoca);
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

    public HashMap<Aluno,Integer> inscreverAlunos(ArrayList<Pessoa> pessoas,String epoca,Disciplina disc, int nAnos){
        HashMap<Aluno,Integer> temp = new HashMap<Aluno,Integer>();
        switch (epoca.toLowerCase()){
            case "normal":
                for(Aluno al: disc.getAlunos()){
                            temp.put(al,-1);
                }
                return temp;
            case "recurso":
                for(Aluno al: disc.getAlunos()){
                    temp.put(al,-1);
                }
                return temp;
            case "especial":
                for(Aluno al: disc.getAlunos()){
                    if(al.getAnoMatricula() == nAnos || al.getRegime().equalsIgnoreCase("trabalhor-estudante") || al.getRegime().equalsIgnoreCase("atleta") ||
                            al.getRegime().equalsIgnoreCase("dirigente associativo")){
                        temp.put(al,-1);
                    }
                }
                return temp;
        }
        return null;
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

    public void alterarExame(ArrayList<Pessoa> utilizadores,ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas,String param,String disciplina,String curso,String epoca){
        String newValue,value2;
        int value,test,i;
        boolean control = false, control2 = false;
        Curso csr = new Curso();
        Scanner sc = new Scanner(System.in);
        Exame ex = new Exame();
        Curso newCurso;
        Date exameDate = null;
        Sala newSala;
        Aluno alumni = new Aluno();
        ex = returnExame(exames,disciplina,epoca,curso);
        switch (param.toLowerCase()){
            case "disciplina":
                do {
                    System.out.println("Nova disciplina: ");
                    newValue = sc.nextLine();
                    value = csr.checkCursoInf(cursos, newValue, curso);
                    if (value == -1) {
                        System.out.println("Disciplina não existe no curso indicado");
                    }
                }while (value == -1);
                if(ex!=null){
                    ex.setDisciplina(csr.parseDisciplina(cursos,newValue,curso));
                    System.out.println("Disciplina alterada com sucesso");
                }
                break;
            case "curso":
                do{
                    System.out.println("Novo curso: ");
                    newValue = sc.nextLine();
                    value = csr.checkCursoInf(cursos,disciplina,newValue);
                    if(value == 0){
                        System.out.println("Curso não existe");
                    }
                }while(value == 0);
                newCurso = csr.parseCurso(cursos,newValue);
                test = csr.parseCurso(newCurso,disciplina);
                ex.setCurso(newCurso);
                if(test == 0){
                    System.out.println("Disciplina não existe no novo curso");
                    do {
                        System.out.println("Nova disciplina: ");
                        newValue = sc.nextLine();
                        value = csr.checkCursoInf(cursos, newValue, curso);
                        if (value == -1) {
                            System.out.println("Disciplina não existe no curso indicado");
                        }
                    }while (value == -1);

                    if(ex!=null){
                        ex.setDisciplina(csr.parseDisciplina(cursos,newValue,curso));
                        System.out.println("Disciplina alterada com sucesso");
                    }
                }
                break;
            case "data":
                do{
                    System.out.println("Nova data de realização do exame: ");
                    newValue = sc.nextLine();
                    try {
                        exameDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(newValue);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Nova duração do exame(minutos): ");
                    value = Integer.parseInt(sc.nextLine());
                    control = sala.verificaSala(salas,exames,ex.getSala().getNome(),exameDate);
                }while(!control);
                ex.setDate(exameDate);
                ex.setDuracao(value);
                System.out.println("Data do exame alterada com sucesso");
                System.out.println("Duração do exame alterada com sucesso");
                break;
            case "sala":
                do{
                    System.out.println("Nova data de realização do exame: ");
                    newValue = sc.nextLine();
                    control = sala.verificaSala(salas,exames,newValue,ex.getDate());
                    newSala = sala.returnSala(salas,newValue);
                }while(!control);
                ex.setSala(newSala);
                System.out.println("Sala de exame alterada");
                break;
            case "vigilantes":
                do {
                    System.out.println("Pretende remover ou adicionar vigilantes?: ");
                    newValue = sc.nextLine();
                }while(!newValue.equals("remover")|| !newValue.equals("adicionar"));
                do {
                    if(newValue.equals("remover")){
                        System.out.println("Indique o nome do vigilante a remover: ");
                        value2 = sc.nextLine();
                        for(i=0;i < ex.getVigilantes().size();i++){
                            if(ex.getVigilantes().get(i).getNome().equals(value2)){
                                ex.getVigilantes().remove(i);
                                System.out.println("Vigilante removido com sucesso");
                                control = true;
                            }
                        }
                        System.out.println("Vigilante não está associado ao exame indicado");
                        control=false;
                    }
                    else if(newValue.equals("adicionar")){
                        System.out.println("Indique o nome do vigilante a adicionar: ");
                        value2 = sc.nextLine();
                        for(Pessoa ps: utilizadores){
                            if(ps instanceof Docente){
                                if(((Docente) ps).getNome().equals(value2)){
                                    //TODO disponibilidade dos docentes
                                    ex.getVigilantes().add((Docente) ps);
                                    control = true;
                                }
                            }
                        }
                        System.out.println("Vigilante não existe no sistema volte a inserir o nome do vigilante");
                        control = false;
                    }
                }while(!control);
                break;
            case "funcionario":
                do {
                    System.out.println("Pretende remover ou adicionar funcionarios?: ");
                    newValue = sc.nextLine();
                }while(!newValue.equals("remover")|| !newValue.equals("adicionar"));
                do {
                    if(newValue.equals("remover")){
                        System.out.println("Indique o nome do vigilante a remover: ");
                        value2 = sc.nextLine();
                        for(i=0;i < ex.getFuncionarios().size();i++){
                            if(ex.getFuncionarios().get(i).getNome().equals(value2)){
                                ex.getFuncionarios().remove(i);
                                System.out.println("Funcionario removido com sucesso");
                                control = true;
                            }
                        }
                        System.out.println("Funcionario não está associado ao exame indicado");
                        control=false;
                    }
                    else if(newValue.equals("adicionar")){
                        System.out.println("Indique o nome do funcionario a adicionar: ");
                        value2 = sc.nextLine();
                        for(Pessoa ps: utilizadores){
                            if(ps instanceof NaoDocente){
                                if(((NaoDocente) ps).getNome().equals(value2)){
                                    ex.getFuncionarios().add((NaoDocente) ps);
                                    control = true;
                                }
                            }
                        }
                        System.out.println("Funcionario não existe no sistema volte a inserir o nome do funcionario");
                        control = false;
                    }
                }while(!control);
                break;
            case "epoca":
                Disciplina disc = csr.parseDisciplina(cursos,disciplina,curso);
                newCurso = csr.parseCurso(cursos,curso);
                do {
                    System.out.println("Nova época do exame: ");
                    newValue = sc.nextLine();
                }while(!newValue.equalsIgnoreCase("normal")|| !newValue.equalsIgnoreCase("recurso")|| !newValue.equalsIgnoreCase("especial") || ex.getTipoExame().equalsIgnoreCase(newValue));
                if(newValue.equals("normal")){
                    ex.setTipoExame(newValue);
                    ex.setNotas(inscreverAlunos(utilizadores,newValue,disc,newCurso.getDuracao()));
                    System.out.println("Época de exame alterada\nFoi feita a inscrição dos alunos");
                }
                else if(newValue.equals("recurso")){
                    ex.setTipoExame(newValue);
                    ex.setNotas(inscreverAlunos(utilizadores,newValue,disc,newCurso.getDuracao()));
                    System.out.println("Época de exame alterada\nFoi feita a inscrição dos alunos");
                }
                else if(newValue.equals("especial")){
                    ex.setTipoExame(newValue);
                    ex.setNotas(inscreverAlunos(utilizadores,newValue,disc,newCurso.getDuracao()));
                    System.out.println("Época de exame alterada\nFoi feita a inscrição dos alunos");
                }
                break;

        }
    }

    public Exame novoExame(ArrayList<Pessoa> utilizadores,ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas){
        //TODO proteçoes nos inputs, convocar automaticamente os vigilantes da disciplina em causa(exceto se tiverem sobreposição)
        //TODO preferência: se o docente tiver sobreposição e seja auxiliar dessa cadeira e não auxiliar desta vai para o outro exame
        //TODO preferencia: se o docente tiver sobreposição e não seja auxiliar em nenhum deles vai ao primeiro
        //TODO preferencia: se o docente tiver sobreposição e seja auxiliar em todos deles vai ao primeiro
        //TODO preferencia: se o docente tiver sobreposiçao e seja regente de uma dessas cadeiras deve ir para o exame onde é responsável
        String curso,disc,epoca,docente,nDocente,novaSala,local,date;
        int controlo, checkInf,nDocentes,i,nFuncionarios,duracao;
        boolean ctrl,ctrlSala = false;
        Date exameDate = null;
        Disciplina disciplina;
        ArrayList<Docente> vigilantes = new ArrayList<Docente>();
        ArrayList<NaoDocente> funcionarios = new ArrayList<NaoDocente>();
        Aluno newAluno  = new Aluno();
        HashMap<Aluno,Integer> notas = new HashMap<Aluno,Integer>();
        Scanner sc = new Scanner(System.in);
        Sala sala = new Sala(), newSala;
        Docente doc = new Docente(),newDoc,DocenteResponsavel;
        NaoDocente ndoc = new NaoDocente(),newNdoc;
        Curso csr = new Curso(),finalCurso = new Curso();
        System.out.println("CRIAÇÃO DE NOVO EXAME");
        //nome do curso , nome da disciplina , e época de exame
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
        System.out.println("hey..."+finalCurso.toString());
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
            if ((ex.getDate().before(exameDate) || ex.getDate().equals(exameDate)) && exDuration.after(exameDate)) {
                ctrl = true;
                for(i=0;i<disciplina.getDocentesAuxiliares().size();i++){
                    if(doc.checkDocenteDisponivel(ex,disciplina.getDocentesAuxiliares().get(i),disciplina,exameDate,exDuration)){
                        vigilantes.add(disciplina.getDocentesAuxiliares().get(i));
                    }
                }
                System.out.println("Número de docentes adicionais para vigiar o exame: ");
                nDocentes = Integer.parseInt(sc.nextLine());
                for(i=0;i<nDocentes;i++){
                    do{
                        System.out.println("Nome do docente: ");
                        docente = sc.nextLine();
                        newDoc = doc.parseDocente(utilizadores,docente);
                        //ver se pode -->horas
                        if(doc.checkDocenteDisponivel(ex,newDoc,disciplina,exameDate,exDuration)){
                            vigilantes.add(newDoc);
                            System.out.println("Docente vigilante adicionado");
                        }
                    }while(newDoc==null);
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
            ctrlSala = sala.verificaSala(salas,exames,novaSala,exameDate);
            newSala = sala.returnSala(salas,novaSala);
        }while(!ctrlSala);
        //associar alunos
        System.out.println("A associar alunos...");
        notas = inscreverAlunos(utilizadores,epoca,disciplina,finalCurso.getDuracao());
        System.out.println(notas.size()+" alunos inscritos no exame");
        return new Exame(disciplina,exameDate,duracao,newSala,DocenteResponsavel,vigilantes,funcionarios,notas,epoca,finalCurso);
    }

    public boolean inExameList(ArrayList<Exame> exames,ArrayList<Curso> cursos, String nomeDisciplina, String curso, String epoca){
        //confirma existência do curso e disciplina nesse curso
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
            if(ex.getDisciplina().getNome().equals(disciplina) && ex.getCurso().getNome().equals(curso) && ex.getTipoExame().equals(epoca)){
                return ex;
            }
        }
        return null;
    }

    public void lancarNotas(ArrayList<Exame> exames,String disciplina,String epoca,String curso){
        int nota;
        Scanner sc = new Scanner(System.in);
        for(Exame ex: exames){
            if(ex.getDisciplina().getNome().equals(disciplina) && ex.getTipoExame().equals(epoca) && ex.getCurso().getNome().equals(curso)){
                if(!ex.getNotas().containsValue(-1)){
                    System.out.println("Notas já lançadas");
                    return;
                }
                else{
                    for (Map.Entry<Aluno, Integer> entry : ex.getNotas().entrySet())
                    {
                        System.out.print(entry.getKey()+": ");
                        nota = Integer.parseInt(sc.nextLine());
                        entry.setValue(nota);
                    }
                }
            }
        }
        System.out.println("Notas do exame de "+disciplina+" lançadas");
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
        return "Exame{" +
                "disciplina=" + disciplina +
                ", date=" + date +
                ", duracao=" + duracao +
                ", sala=" + sala +
                ", docenteResponsavel=" + docenteResponsavel +
                ", vigilantes=" + vigilantes +
                ", funcionarios=" + funcionarios +
                ", notas=" + notas +
                ", tipoExame='" + tipoExame + '\'' +
                ", curso=" + curso +
                '}';
    }
}
