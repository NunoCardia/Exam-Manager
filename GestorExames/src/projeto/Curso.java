package projeto;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public class Curso implements Serializable{

    protected String nome;
    protected int duracao;
    protected String grau;
    protected ArrayList<Disciplina> cadeiras;
    private ArrayList<Pessoa> utilizadores;
    private ArrayList<Curso> cursos;
    private ArrayList<Exame> exames;
    private ArrayList<Sala> salas;
    private static final long serialVersionUID = -266092762869874067L;

    public Curso() {}

    public Curso(String nome, int duracao, String grau,ArrayList<Disciplina> cadeiras) {
        this.nome = nome;
        this.duracao = duracao;
        this.grau = grau;
        this.cadeiras = cadeiras;
    }

    public Curso(ArrayList<Pessoa> utilizadores, ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas) {
        this.utilizadores = utilizadores;
        this.cursos = cursos;
        this.exames = exames;
        this.salas = salas;
    }

    public boolean protectArgs(String param){
        if(!param.equalsIgnoreCase("nome") && !param.equalsIgnoreCase("duracao") && !param.equalsIgnoreCase("grau") && !param.equalsIgnoreCase("disciplinas")){
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
            if(str.equals("0")) return false;
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

    public int checkCursoInf(ArrayList<Curso> cursos, String nomeDisciplina, String curso){
        //1 - existe curso e disciplina
        //-1 - existe curso mas não disciplina
        //0 - não existe curso
        for(Curso csr: cursos){
            if(csr.getNome().equalsIgnoreCase(curso)){
                for(Disciplina dsc: csr.getCadeiras()){
                    if(dsc.getNome().equalsIgnoreCase(nomeDisciplina)){
                        return 1;
                    }
                }
                return -1;
            }
        }
        return 0;
    }

    public Curso parseCurso(ArrayList<Curso> cursos, String curso){
        for(Curso csr: cursos){
            if(csr.getNome().equalsIgnoreCase(curso)){
                return csr;
            }
        }
        return null;
    }

    public int parseCurso(Curso curso, String disciplina){
        int i=0;
        for(i=0;i<curso.getCadeiras().size();i++){
            if(curso.getCadeiras().get(i).equals(disciplina)){
                return 1;
            }
        }
        return 0;
    }

    public Disciplina parseDisciplina(ArrayList<Curso> cursos, String nomeDisciplina, String curso){
        for(Curso csr: cursos){
            if(csr.getNome().equalsIgnoreCase(curso)){
                for(Disciplina dsc: csr.getCadeiras()){
                    if(dsc.getNome().equalsIgnoreCase(nomeDisciplina)){
                        return dsc;
                    }
                }
            }
        }
        return null;
    }

    public void menuCurso(){
        Scanner sc = new Scanner(System.in);
        String nome,grau,param,nomeTemp = null;
        int i,valor;
        ArrayList<Disciplina> cadeiras = new ArrayList<Disciplina>();
        Disciplina disc = new Disciplina();
        Curso csr;
        Departamento dep = new Departamento();
        do {
            System.out.println("MENU DE CURSOS\n1 - Adicionar curso\n2 - Alterar curso\n3 - Remover curso\n0 - voltar ao menu inicial");
            while (true){
                try {
                    valor = Integer.parseInt(sc.nextLine());
                    if(valor< 0 ||valor>3){
                        System.out.println("Número inválido");
                    }
                    else break;
                }catch (NumberFormatException e){
                    System.err.println("Introduza um número entre 1 e 3");
                }
            }
            switch (valor){
                case 1:
                    System.out.println("Adicionar curso\nNome do curso: ");
                    nome = sc.nextLine();
                    while(!protectChar(nome)){
                        System.out.println("Nome inválido, por favor introduza um nome válido: ");
                        nome = sc.nextLine();
                    }
                    do {
                        System.out.println("Duração do curso(1,2,3,4 ou 5 anos): ");
                        nomeTemp = sc.nextLine();
                        while(!isNumeric(nomeTemp)){
                            System.out.println("O que introduziu não é um número válido, por favor introduza um número válido: ");
                            nomeTemp = sc.nextLine();
                        }
                        duracao = Integer.parseInt(nomeTemp);
                    }while(duracao < 1 || duracao > 5);
                    do {
                        System.out.println("Grau que o curso confere(Licenciatura,Mestrado,Doutoramento): ");
                        grau = sc.nextLine();
                    }while(!grau.equalsIgnoreCase("Licenciatura") && !grau.equalsIgnoreCase("Mestrado") && !grau.equalsIgnoreCase("Doutoramento"));
                    System.out.println("Número de disciplinas do curso: ");
                    nomeTemp = sc.nextLine();
                    while(!isNumeric(nomeTemp)){
                        System.out.println("O que introduziu não é um número válido, por favor introduza um número válido: ");
                        nomeTemp = sc.nextLine();
                    }
                    valor = Integer.parseInt(nomeTemp);
                    for(i=0;i<valor;i++){
                        cadeiras.add(disc.newDisciplina(utilizadores));
                    }
                    if(parseCurso(cursos,nome)==null){
                        cursos.add(new Curso(nome,duracao,grau,cadeiras));
                        System.out.println("Curso adicionado com sucesso");
                    }
                    else{
                        System.out.println("Curso já existe no sistema");
                    }
                    break;
                case 2:
                    System.out.println("Nome do curso a alterar: ");
                    nome = sc.nextLine();
                    while(!protectChar(nome)){
                        System.out.println("Nome inválido, por favor introduza um nome válido: ");
                        nome = sc.nextLine();
                    }
                    csr = parseCurso(cursos,nome);
                    if(csr==null){
                       System.out.println("Curso não existe no sistema");
                        break;
                    }
                    do {
                        System.out.println("Nome do parâmetro a alterar: ");
                        param = sc.nextLine();
                    }while (!param.equalsIgnoreCase("nome") && !param.equalsIgnoreCase("duracao") &&!param.equalsIgnoreCase("grau") && !param.equalsIgnoreCase("disciplinas"));
                    for(Exame ex: exames){
                        if(ex.getCurso().getNome().equalsIgnoreCase(csr.getNome())){
                            System.out.println("Exames a decorrer por isso não pode alterar dados do curso");
                            break;
                        }
                        else{
                            mudaCurso(cursos,utilizadores,csr,param);
                        }
                    }
                    break;
                case 3:
                    boolean t = false;
                    System.out.println("Nome do curso: ");
                    nome = sc.nextLine();
                    while(!protectChar(nome)){
                        System.out.println("Nome inválido, por favor introduza um nome válido: ");
                        nome = sc.nextLine();
                    }
                    for(Curso csr1: cursos){
                        if(csr1.getNome().equalsIgnoreCase(nome)){
                            cursos.remove(csr1);
                            nome = "done";
                            System.out.println("Curso removido com sucesso");
                            break;
                        }
                    }
                    if(!nome.equals("done")){
                        System.out.println("Curso não existe no sistema");
                    }
                    break;
                case 0:
                    dep.handleChoice(utilizadores,cursos,exames,salas);
                    break;
                default:
                    System.out.println("Parâmetro não reconhecido");
                    break;

            }
        } while(valor!=0);
    }

    private void mudaCurso(ArrayList<Curso> cursos,ArrayList<Pessoa> utilizadores,Curso csr, String param) {
        String newValue = null,grau;
        int valor;
        boolean control = false;
        Scanner sc = new Scanner(System.in);
        Disciplina disc = new Disciplina(),cadeira;
        switch (param){
            case "nome":
                do {
                    System.out.println("Novo nome para o curso");
                    newValue = sc.nextLine();
                } while(parseCurso(cursos,newValue)!=null);
                csr.setNome(newValue);
                System.out.println("Nome do curso mudado para "+newValue);
                break;
            case "duracao":
                do {
                    System.out.println("Nova duracao do curso: ");
                    valor = Integer.parseInt(sc.nextLine());
                }while(valor < 1 || valor > 5);
                csr.setDuracao(valor);
                System.out.println("Duraçao do curso alterada para "+valor);
                break;
            case "grau":
                do {
                    System.out.println("Novo grau do curso(Licenciatura,Mestrado,Doutoramento): ");
                    grau = sc.nextLine();
                }while(!grau.equalsIgnoreCase("Licenciatura") && !grau.equalsIgnoreCase("Mestrado") && !grau.equalsIgnoreCase("Doutoramento"));
                csr.setGrau(grau);
                System.out.println("Grau do curso alterado para "+grau);
                break;
            case "disciplinas":
                System.out.println("1 - Adicionar disciplina\n2 - Alterar disciplina\n3 - Remover disciplina");
                int choice=0;
                choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1:
                        System.out.println("Nome da disciplina a adicionar: ");
                        newValue = sc.nextLine();
                        control = false;
                        while(!control){
                            for(Disciplina dis: csr.getCadeiras()){
                                if(dis.getNome()!= null && dis.getNome().equalsIgnoreCase(newValue)){
                                    control = true;
                                }
                            }
                            if(control){
                                System.out.println("Disciplina já existe no curso");
                            }
                        }
                        csr.getCadeiras().add(disc.newDisciplina(utilizadores,nome));
                        break;
                    case 2:
                        int index=0;
                        while(!control){
                            System.out.println("Nome da disciplina a alterar: ");
                            newValue = sc.nextLine();
                            for(Disciplina dis: csr.getCadeiras()){
                                if(dis.getNome().equalsIgnoreCase(newValue)){
                                    index = csr.getCadeiras().indexOf(dis);
                                    control = true;
                                }
                            }
                            if(!control){
                                System.out.println("Disciplina não existe no curso");
                            }
                        }
                        cadeira = disc.alteraCadeiras(utilizadores,csr,disc.parseDisciplina(csr,newValue));
                        csr.getCadeiras().set(index,cadeira);
                        break;
                    case 3:
                        while(!control){
                            System.out.println("Nome da disciplina a remover: ");
                            newValue = sc.nextLine();
                            for(Disciplina dis: csr.getCadeiras()){
                                if(dis.getNome().equalsIgnoreCase(newValue)){
                                    control = true;
                                }
                            }
                            if(!control){
                                System.out.println("Disciplina não existe no curso");
                            }
                        }
                        Disciplina temp = disc.parseDisciplina(csr,newValue);
                        csr.getCadeiras().remove(temp);
                        System.out.println("Disciplina removida");
                        break;
                }
        }
    }


    public Docente getDocenteResponsavel(ArrayList<Curso> cursos, String disciplina,String curso){
        for(Curso csr: cursos){
            if(csr.getNome().equalsIgnoreCase(curso)){
                for(Disciplina dsc: csr.getCadeiras()){
                    if(dsc.getNome().equalsIgnoreCase(disciplina)){
                        return dsc.getRegente();
                    }
                }
            }
        }
        return null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    public ArrayList<Disciplina> getCadeiras() {
        return cadeiras;
    }

    public void setCadeiras(ArrayList<Disciplina> cadeiras) {
        this.cadeiras = cadeiras;
    }
}
