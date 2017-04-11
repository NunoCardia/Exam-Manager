/**
 * *
 * @author Nuno Ferreira
 */

package projeto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public class Sala implements Serializable{
    protected String nome;
    protected int ocupacao;
    private ArrayList<Pessoa> utilizadores;
    private ArrayList<Curso> cursos;
    private ArrayList<Exame> exames;
    private ArrayList<Sala> salas;
    private static final long serialVersionUID = -3680702783252467980L;



    public Sala() {
    }

    public Sala(ArrayList<Pessoa> utilizadores, ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas) {
        this.utilizadores = utilizadores;
        this.cursos = cursos;
        this.exames = exames;
        this.salas = salas;
    }

    public Sala(String nome, int ocupacao) {
        this.nome = nome;
        this.ocupacao = ocupacao;
    }

    /**
     * Método que permite proteger qualquer input que o utilizador introduza no caso de este ser uma String
     * @param input  String a ser protegida
     * @return bool Verifica se o input é válido ou não
     */

    public boolean protectChar(String input) {
        String simbols="?!,;:-_`´^/()%&$#[]{}=+*|\"";
        char [] items = input.replaceAll("\\s+","").toCharArray();
        for(char c: items) {
            if(simbols.indexOf(c)!=-1) {
                return false;

            }
        }
        return true;
    }

    /**
     * Método que permite proteger qualquer input que o utilizador introduza no caso de este ser inteiro
     * @param str String a ser protegida
     * @return bool Verifica se o input é válido ou não
     */

    public boolean isNumeric(String str) {
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

    /**
     * Método que permite verificar se uma sala está presente na lista de salas
     * @param salas ArrayList de salas
     * @param nome nome da sala
     * @return bool Verifica se o input é válido ou não
     */

    public boolean checkInfSala(ArrayList<Sala> salas, String nome) {
        for (Sala sl : salas) {
            if (sl.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método responsável por retornar um objeto do tipo Sala
     * @param salas ArrayList de salas
     * @param nome nome da Sala
     * @return Sala objeto do tipo sala
     */

    public Sala returnSala(ArrayList<Sala> salas, String nome) {
        for (Sala sl : salas) {
            if (sl.getNome().equalsIgnoreCase(nome)) {
                return sl;
            }
        }
        return null;
    }

    /**
     * Método responsável por alterar dados da sala
     * @param salas ArrayList de salas
     * @param nome  nome da sala
     * @param param  parâmetro a alterar
     * @return bool Verifica o resultado da operação
     */

    public boolean alteraSala(ArrayList<Sala> salas,String nome,String param) {
        String newValue;
        Scanner sc = new Scanner(System.in);
        for(Sala sl: salas){
            if(sl.getNome().equalsIgnoreCase(nome)){
                switch (param){
                    case "nome":
                        System.out.println("Novo nome: ");
                        newValue = sc.nextLine();
                        while(!protectChar(newValue)){
                            System.out.println("Nome inválido, por favor introduza um nome válido: ");
                            newValue = sc.nextLine();
                        }
                        sl.setNome(newValue);
                        System.out.println("Nome alterado para "+newValue);
                        return true;
                    case "ocupacao":
                        System.out.println("Novo numero de lugares da sala: ");
                        newValue = sc.nextLine();
                        while(!isNumeric(newValue)){
                            System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
                            newValue = sc.nextLine();
                        }
                        sl.setOcupacao(Integer.parseInt(newValue));
                        System.out.println("Número de lugares alterado para "+newValue);
                        return true;
                    default:
                        System.out.println("Parâmetro não reconhecido");
                        break;
                }
            }
        }
        return false;
    }

    /**
     * Método que apresenta ao utilizador o menu para esta classe
     */

    public void menuSala(){
        int valor;
        Scanner sc = new Scanner(System.in);
        Sala sl;
        String param,newValue;
        Departamento dep = new Departamento();
        do {
            System.out.println("MENU DE SALAS\n1 - Adicionar sala\n2 - Alterar dados de uma sala\n3 - Remover sala\n0 - voltar ao menu inicial");
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
                    sl = adicionaSala(salas);
                    if(sl==null){
                        System.out.println("Sala já existe");
                    }
                    else{
                        salas.add(sl);
                    }
                    break;
                case 2:
                    boolean t = false;
                    System.out.println("Nome da sala a alterar: ");
                    newValue = sc.nextLine();
                    while(!protectChar(newValue)){
                        System.out.println("Nome inválido, por favor introduza um nome válido: ");
                        newValue = sc.nextLine();
                    }
                    System.out.println("Parâmetro a alterar: ");
                    param = sc.nextLine();
                    while(!protectChar(param)){
                        System.out.println("Parâmtro inválido, por favor introduza um parâmetro válido: ");
                        param = sc.nextLine();
                    }
                    t = alteraSala(salas,newValue,param);
                    if(!t){
                        System.out.println("Sala não existe no sistema");
                    }
                    break;
                case 3:
                    System.out.println("Nome da sala a remover: ");
                    newValue = sc.nextLine();
                    while(!protectChar(newValue)){
                        System.out.println("Nome inválido, por favor introduza um nome válido: ");
                        newValue = sc.nextLine();
                    }
                    if(checkInfSala(salas,newValue)){
                        for(Sala sl1: salas){
                            if(sl1.getNome().equals(newValue)){
                                salas.remove(sl1);
                                System.out.println("Sala removida do sistema");
                                break;
                            }
                        }
                    }
                    else{
                        System.out.println("Sala não existe no sistema");
                    }
                    break;
                case 0:
                    dep.handleChoice(utilizadores,cursos,exames,salas);
                    break;
                default:
                    System.out.println("Parâmetro não reconhecido");
                    break;
            }
        }while(valor!=0);
    }

    /**
     * Método responsável por adicionar uma sala
     * @param salas ArrayList de salas
     * @return Sala objeto do tipo Sala
     */

    public Sala adicionaSala(ArrayList<Sala> salas) {
        System.out.println("ADICIONAR SALA");
        String nome;
        int ocup;
        Scanner sc = new Scanner(System.in);
        System.out.println("Nome da sala: ");
        nome = sc.nextLine();
        while(!protectChar(nome)){
            System.out.println("Nome inválido, por favor introduza um nome válido: ");
            nome = sc.nextLine();
        }
        System.out.println("Ocupação da sala: ");
        ocup = Integer.parseInt(sc.nextLine());
        if(checkInfSala(salas, nome)){
            return null;
        }
        return new Sala(nome, ocup);
    }

    /**
     * Método responsável por verificar se uma sala pode receber um exame
     * @param salas ArrayList de salas
     * @param exames ArrayList de exames
     * @param nomeSala  nome da sala
     * @param horaExame hora do exame
     * @param duracao duração do exame
     * @return bool Verifica o resultado da operação
     */

    public boolean verificaSala(ArrayList<Sala> salas, ArrayList<Exame> exames, String nomeSala, Date horaExame,int duracao) {
        //ver se a sala existe; ver se está disponivel;
        Exame exame = new Exame();
        Date horafinalExame,horaFinalNovoExame, date = new Date();
        horaFinalNovoExame = exame.addMinutesToDate(duracao,horaExame);
        boolean test;
        test = checkInfSala(salas, nomeSala);
        if (test) {
            for (Exame ex : exames) {
                if (ex.getSala().getNome().equalsIgnoreCase(nomeSala)) {
                    horafinalExame = exame.addMinutesToDate(ex.getDuracao(), ex.getDate());
                    //se a nova hora estiver entre a hora inicial do exame e a hora final do exame
                    boolean f1 = horaExame.after(ex.getDate()) && horaExame.before(horafinalExame);
                    boolean f2 = horaFinalNovoExame.before(horafinalExame) && horaFinalNovoExame.after(ex.getDate());
                    boolean f3 = horaExame.before(ex.getDate()) && horaFinalNovoExame.after(horafinalExame);
                    if(f1 || f2 || f3) {
                        System.out.println("Sala ocupada");
                        return false;
                    }
                }
            }
            System.out.println("Sala disponível para a data e hora indicadas");
            return true;
        }
        return false;

    }
    
    public int getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(int ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString(){
        return "Nome: "+nome+"\tOcupação: "+ocupacao;
    }
}

