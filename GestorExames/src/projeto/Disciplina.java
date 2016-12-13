package projeto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public class Disciplina implements Serializable{
    protected String nome;
    protected Docente regente;
    protected ArrayList<Docente> docentesAuxiliares;
    protected ArrayList<Aluno> alunos;
    private static final long serialVersionUID = -7255092941156893371L;
    public Disciplina() {
    }

    public Disciplina(String nome, Docente regente, ArrayList<Docente> docentesAuxiliares, ArrayList<Aluno> alunos) {
        this.nome = nome;
        this.regente = regente;
        this.docentesAuxiliares = docentesAuxiliares;
        this.alunos = alunos;
    }

    public Disciplina parseDisciplina(Curso csr,String disciplina){
        for(Disciplina disc: csr.getCadeiras()){
            if(disc.getNome().equalsIgnoreCase(disciplina)){
                return disc;
            }
        }
        return null;
    }

    public boolean protectArgs(String param){
        if(!param.equalsIgnoreCase("nome") && !param.equalsIgnoreCase("email") && !param.equalsIgnoreCase("numero") && !param.equalsIgnoreCase("categoria") &&
                !param.equalsIgnoreCase("area de investigacao")){
            return false;
        }
        else return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    private static boolean protectChar(String input) {
        String simbols="?!.,;:-_`´^/()%&$#[]{}=+*|\"";
        char [] items = input.replaceAll("\\s+","").toCharArray();
        for(char c: items) {
            if(!Character.isLetter(c) || simbols.indexOf(c)!=-1) {
                return false;

            }
        }
        return true;
    }

    public Docente adicionarRegente(ArrayList<Pessoa> utilizadores){
        Scanner sc = new Scanner(System.in);
        String regente;
        Docente DocRegente = null;
        System.out.println("lista size: "+utilizadores.size());
        for (Pessoa ps:utilizadores) {
            if(ps instanceof Docente){
                System.out.println(((Docente) ps).toString());

            }
        }
        while(DocRegente==null){
            System.out.println("Regente da cadeira: ");
            regente = sc.nextLine();
            for(Pessoa ps: utilizadores){
                if(ps instanceof Docente){
                    if(((Docente) ps).getNome().equalsIgnoreCase(regente)){
                        DocRegente = (Docente) ps;
                    }
                }
            }
            if(DocRegente==null){
                System.out.println("Docente não existe no sistema");
            }
        }
        return DocRegente;
    }

    public ArrayList<Docente> adicionarAuxiliares(ArrayList<Pessoa> utilizadores){
        Scanner sc = new Scanner(System.in);
        int valor,i;
        String aux;
        ArrayList<Docente> docentesAuxiliares = new ArrayList<Docente>();
        System.out.println("Número de Docentes Auxiliares: ");
        aux = sc.nextLine();
        while(!isNumeric(aux)){
            System.out.println("Valor que inseriu não é um número, por favor insira um numero válido: ");
            aux = sc.nextLine();
        }
        valor = Integer.parseInt(aux);
        for(i=0;i<valor;i++){
            do {
                System.out.println("Nome do docente auxiliar("+(i+1)+"º"+"): ");
                aux = sc.nextLine();
                for(Pessoa ps: utilizadores){
                    if(ps instanceof Docente){
                        if(((Docente) ps).getNome().equalsIgnoreCase(aux)){
                            docentesAuxiliares.add(i,((Docente) ps));
                        }
                    }
                }
            } while(docentesAuxiliares.get(i)==null);
        }
        return docentesAuxiliares;
    }

    public ArrayList<Aluno> adicionarAlunos(ArrayList<Pessoa> utilizadores){
        Scanner sc = new Scanner(System.in);
        int valor,i;
        Long aux;
        String test;
        boolean found = false;
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
        System.out.println("Número de Alunos a inscrever na cadeira: ");
        valor = Integer.parseInt(sc.nextLine());
        for (Pessoa ps:utilizadores) {
            if(ps instanceof Aluno){
                System.out.println(((Aluno) ps).toString());

            }
        }
        for(i=0;i<valor;i++){
            found = false;
            while(!found){
                System.out.println("Número de aluno("+(i+1)+"º"+"): ");
                test = sc.nextLine();
                while(!isNumeric(test)){
                    System.out.println("Valor que inseriu não é um número, por favor insira um número válido: ");
                    test = sc.nextLine();
                }
                aux = Long.parseLong(test);
                for(Pessoa ps: utilizadores){
                    if(ps instanceof Aluno){
                        if(((Aluno) ps).getNumeroAluno() == aux){
                            alunos.add(i,((Aluno) ps));
                            found = true;
                            System.out.println("Aluno adicionado à cadeira");
                        }
                    }
                }
                if(!found){
                    System.out.println("Aluno não se encontra no sistema");
                }
            }
        }
        return alunos;
    }

    public Disciplina newDisciplina(ArrayList<Pessoa> utilizadores){
        Scanner sc = new Scanner(System.in);
        int i,valor;
        String nome,regente,aux;
        Docente DocRegente = null;
        ArrayList<Docente> docentesAuxiliares = new ArrayList<Docente>();
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
        System.out.println("Nome da cadeira: ");
        nome = sc.nextLine();
        DocRegente = adicionarRegente(utilizadores);
        docentesAuxiliares = adicionarAuxiliares(utilizadores);
        alunos = adicionarAlunos(utilizadores);
        return new Disciplina(nome,DocRegente,docentesAuxiliares,alunos);
    }

    public Disciplina newDisciplina(ArrayList<Pessoa> utilizadores,String nome){
        Scanner sc = new Scanner(System.in);
        int i,valor;
        String regente,aux;
        Docente DocRegente = null;
        ArrayList<Docente> docentesAuxiliares = new ArrayList<Docente>();
        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
        DocRegente = adicionarRegente(utilizadores);
        docentesAuxiliares = adicionarAuxiliares(utilizadores);
        alunos = adicionarAlunos(utilizadores);
        return new Disciplina(nome,DocRegente,docentesAuxiliares,alunos);
    }

    public Disciplina alteraCadeiras(ArrayList<Pessoa> utilizadores,Curso csr,Disciplina disc){
        String newValue,param;
        int valor,i;
        boolean control = true;
        Scanner sc = new Scanner(System.in);
        Docente doc = new Docente(),newDoc;
        Aluno aluno = new Aluno(),al;
        do {
            System.out.println("Nome do parâmetro a alterar: ");
            param = sc.nextLine();
        } while(!param.equalsIgnoreCase("nome") && !param.equalsIgnoreCase("regente") && !param.equalsIgnoreCase("docentes auxiliares")
                && !param.equalsIgnoreCase("alunos"));
        switch (param){
            case "nome":
                do {
                    System.out.println("Novo nome da disciplina: ");
                    newValue = sc.nextLine();
                    for(Disciplina dis: csr.getCadeiras()){
                        if(dis.getNome().equalsIgnoreCase(newValue)){
                            control = false;
                        }
                    }
                } while(!control);
                disc.setNome(newValue);
                System.out.println("Nome da disciplina alterado para "+newValue);
                break;
            case "regente":
                    System.out.println("Nome do novo regente: ");
                    newValue = sc.nextLine();
                    newDoc = doc.parseDocente(utilizadores,newValue);
                    while(newDoc == null){
                        System.out.println("Docente não existe no sistema, por favor introduza um docente válido: ");
                        newValue = sc.nextLine();
                        newDoc = doc.parseDocente(utilizadores,newValue);
                    }
                disc.setRegente(newDoc);
                System.out.println("Regente alterado");
                break;
            case "docentes auxiliares":
                if(disc.getDocentesAuxiliares().size() != 0){
                    System.out.println("1 - adicionar docente auxiliar\n2 - remover docente auxiliar");
                }
                else{
                    System.out.println("1 - adicionar docente auxiliar");
                }
                valor = Integer.parseInt(sc.nextLine());
                switch (valor){
                    case 1:
                        String control2 = "";
                        System.out.println("Nome do novo docente auxiliar");
                        newValue = sc.nextLine();
                        newDoc = doc.parseDocente(utilizadores,newValue);
                         while(newDoc==null){
                             System.out.println("Docente não existe no sistema, por favor introduza um docente válido: ");
                             newValue = sc.nextLine();
                             newDoc = doc.parseDocente(utilizadores,newValue);
                         }
                        newDoc = doc.parseDocente(utilizadores,newValue);
                        if(newDoc.getNome().equalsIgnoreCase(disc.getRegente().getNome())){
                            System.out.println("Docente que pretende adicionar já é o regente da cadeira");
                        }
                        for(Docente docente: disc.getDocentesAuxiliares()){
                            if(docente.getNome().equalsIgnoreCase(newDoc.getNome())){
                                control2 = "found";
                                System.out.println("Docente auxiliar já se encontra associado a esta disciplina");
                                break;
                            }
                        }
                        if(!control2.equals("found")){
                            disc.getDocentesAuxiliares().add(newDoc);
                            System.out.println("Docente auxiliar adicionado");
                        }
                        break;
                    case 2:
                        String control1 = "";
                        System.out.println("Nome do docente auxiliar");
                        newValue = sc.nextLine();
                        newDoc = doc.parseDocente(utilizadores,newValue);
                        while(newDoc==null){
                            System.out.println("Docente não existe no sistema, por favor introduza um docente válido: ");
                            for (Pessoa ps: utilizadores) {
                                if (ps instanceof Docente){
                                    System.out.println(((Docente) ps).toString());
                                }
                            }
                            newValue = sc.nextLine();
                            newDoc = doc.parseDocente(utilizadores,newValue);
                        }
                        if(newDoc.getNome().equalsIgnoreCase(disc.getRegente().getNome())){
                            System.out.println("Docente é o regente da cadeira e por isso não pode ser removido");
                            break;
                        }
                        for(Docente docente: disc.getDocentesAuxiliares()){
                            if(docente.getNome().equalsIgnoreCase(newDoc.getNome())){
                                disc.getDocentesAuxiliares().remove(docente);
                                control1 = "found";
                                System.out.println("Docente auxiliar removido");
                                break;
                            }
                        }
                        if(!control1.equals("found")){
                            System.out.println("Docente não está associado à disciplina");
                        }
                        break;
                    default:
                        System.out.println("Parâmetro não reconhecido");
                        break;
                }
                break;
            case "alunos":
                if(disc.getAlunos().size() != 0){
                    System.out.println("1 - adicionar aluno\n2 - remover aluno");
                }
                else{
                    System.out.println("1 - adicionar aluno");
                }
                valor = Integer.parseInt(sc.nextLine());
                switch (valor){
                    case 1:
                        System.out.println("Número do novo aluno");
                        newValue = sc.nextLine();
                        al = aluno.parseAluno(utilizadores,Long.parseLong(newValue));
                        while(al==null){
                            newValue = sc.nextLine();
                            al = aluno.parseAluno(utilizadores,Long.parseLong(newValue));
                        }
                        for(Aluno al1: disc.getAlunos()){
                            if(al1.getNumeroAluno() == al.getNumeroAluno()){
                                control = true;
                                System.out.println("Aluno já se encontra inscrito na disciplina");
                                break;
                            }
                        }
                        if(!control){
                            disc.getAlunos().add(al);
                            System.out.println("Aluno adicionado");
                        }
                        break;
                    case 2:
                        control = false;
                        System.out.println("Número do aluno");
                        newValue = sc.nextLine();
                        al = aluno.parseAluno(utilizadores,Long.parseLong(newValue));
                        while(al==null){
                            System.out.println("Aluno não está associado à disciplina");
                            newValue = sc.nextLine();
                            al = aluno.parseAluno(utilizadores,Long.parseLong(newValue));
                        }
                        for(Aluno al1: disc.getAlunos()){
                            if(al1.getNumeroAluno() == al.getNumeroAluno()){
                                disc.getAlunos().remove(al);
                                control = true;
                                System.out.println("Aluno removido da disciplina");
                                break;
                            }
                        }
                        if(!control) System.out.println("Aluno não está associado à disciplina");
                        break;
                    default:
                        System.out.println("Parâmetro não reconhecido");
                        break;
                }
                break;
            default:
                System.out.println("Parâmetro não reconhecido");
                break;
        }
        return disc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Docente getRegente() {
        return regente;
    }

    public void setRegente(Docente regente) {
        this.regente = regente;
    }

    public ArrayList<Docente> getDocentesAuxiliares() {
        return docentesAuxiliares;
    }

    public void setDocentesAuxiliares(ArrayList<Docente> docentesAuxiliares) {
        this.docentesAuxiliares = docentesAuxiliares;
    }

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }
}
