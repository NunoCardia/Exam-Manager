/**
 * *
 * @author Nuno Ferreira
 */

package projeto;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public class Aluno extends Pessoa implements Serializable{
    protected long numeroAluno;
    protected int anoMatricula;
    protected String regime;
    protected ArrayList<Pessoa> utilizadores;
    protected ArrayList<Curso> cursos;
    protected ArrayList<Exame> exames;
    protected ArrayList<Sala> salas;
    private static final long serialVersionUID = 2754295480662076666L;

    public Aluno(){}

    public Aluno(String nome, String email, long numeroAluno, int anoMatricula,String regime){
        super(nome,email);
        this.numeroAluno = numeroAluno;
        this.anoMatricula = anoMatricula;
        this.regime = regime;
    }

    public Aluno(ArrayList<Pessoa> utilizadores, ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas){
        this.utilizadores = utilizadores;
        this.cursos = cursos;
        this.exames = exames;
        this.salas = salas;
    }

    /**
     * Método que protege o input param quando o utilizador quer alterar algum dado sobre o objeto
     * @param param String a ser protegida
     * @return bool Verifica se o input é válido ou não
     */
    public boolean protectArgs(String param){
        if(!param.equalsIgnoreCase("nome") && !param.equalsIgnoreCase("email") && !param.equalsIgnoreCase("numero de aluno") && !param.equalsIgnoreCase("ano de matricula") &&
                !param.equalsIgnoreCase("regime")){
            return false;
        }
        else return true;
    }

    /**
     * Método que protege o input Regime quando o utilizador quer introduzir o argumento Regime
     * @param regime String a ser protegida
     * @return bool Verifica se o input é válido ou não
     */

    private boolean protectRegime(String regime) {
        if(!regime.equalsIgnoreCase("normal") && !regime.equalsIgnoreCase("trabalhador-estudante") &&!regime.equalsIgnoreCase("atleta") &&!regime.equalsIgnoreCase("dirigente associativo") &&!regime.equalsIgnoreCase("aluno de erasmus")){
            return false;
        }
        else return true;
    }

    /**
     * Método que permite proteger qualquer input que o utilizador introduza no caso de este ser inteiro
     * @param str String a ser protegida
     * @return bool Verifica se o input é válido ou não
     */

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

    /**
     * Método que permite proteger qualquer input que o utilizador introduza no caso de este ser uma String
     * @param input  String a ser protegida
     * @return bool Verifica se o input é válido ou não
     */

    private static boolean protectChar(String input) {
        String simbols="?!.,;:_`´^/()%&$#[]{}=+*|\"";
        char [] items = input.replaceAll("\\s+","").toCharArray();
        for(char c: items) {
            if(!Character.isLetter(c) || simbols.indexOf(c)!=-1) {
                return false;

            }
        }
        return true;
    }

    /**
     * Método que permite devolver um Aluno baseado no seu número
     * @param utilizadores  ArrayList de pessoas
     * @param numero  Número do aluno
     * @return Aluno Retorna um aluno
     */

    public Aluno parseAluno(ArrayList<Pessoa> utilizadores,long numero){
        for(Pessoa ps: utilizadores){
            if(ps instanceof Aluno){
                if(((Aluno) ps).getNumeroAluno() == numero){
                    return ((Aluno) ps);
                }
            }
        }
        return null;
    }

    /**
     * Método que permite proteger a aplicação caso o utilizador introduza dois utilizadores iguais
     * @param utilizadores ArrayList de pessoas
     * @param email  email da nova Pessoa
     * @return bool Verifica se o aluno já existe no sistema ou não
     */

    public boolean checkEmail(ArrayList<Pessoa> utilizadores,String email){
        for(Pessoa ps: utilizadores){
            if(ps instanceof Aluno){
                if(((Aluno) ps).getEmail().equalsIgnoreCase(email)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método que permite proteger a aplicação caso o utilizador introduza dois utilizadores iguais
     * @param utilizadores ArrayList de pessoas
     * @param codigo  numero de aluno
     * @return bool Verifica se o aluno já existe no sistema ou não
     */

    public boolean checkCodigo(ArrayList<Pessoa> utilizadores,long codigo){
        for(Pessoa ps: utilizadores){
            if(ps instanceof Aluno){
                if(((Aluno) ps).getNumeroAluno()==codigo){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Método que apresenta ao utilizador o menu para esta classe
     */

    public void menuPessoas(){
        int valor;
        Scanner sc = new Scanner(System.in);
        Departamento dep = new Departamento();
        do {

            System.out.println("MENU DE ALUNO\n1 - Adicionar aluno\n2 - Alterar dados de aluno\n3 - Remover aluno\n0 - Voltar ao menu inicial");
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
                    inserirPessoa();
                    break;
                case 2:
                    alterarPessoa();
                    break;
                case 3:
                    removerPessoa();
                    break;
                case 0:
                    dep.handleChoice(utilizadores,cursos,exames,salas);
                    break;
                default:
                    System.out.println("Parâmetro não reconhecido");
                    break;
            }
        }while(valor !=0);
    }


    /**
     * Método responsável por inserir um novo aluno no sistema
     */
    public void inserirPessoa(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Nome do aluno: ");
        String test,nome;
        Aluno al;
        nome = sc.nextLine();
        while(!protectChar(nome)){
            System.out.println("Nome inválido, por favor introduza um nome válido: ");
            nome = sc.nextLine();
        }
        System.out.println("Email: ");
        email = sc.nextLine();
        if(checkEmail(utilizadores,email)){
            System.out.println("Aluno já se encontra no sistema");
            return;
        }
        System.out.println("Número de aluno: ");
        test = sc.nextLine();
        while(!isNumeric(test)){
            System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
            test = sc.nextLine();
        }
        numeroAluno = Long.parseLong(test);
        if(checkCodigo(utilizadores,numeroAluno)){
            System.out.println("Aluno já se encontra no sistema");
            return;
        }
        System.out.println("Ano de Matrícula: ");
        test = sc.nextLine();
        while(!isNumeric(test)){
            System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
            test = sc.nextLine();
        }
        anoMatricula = Integer.parseInt(test);
        System.out.println("Regime do aluno: ");
        regime = sc.nextLine();
        while(!protectChar(regime)){
            System.out.println("Regime inválido, por favor introduza um regime válido: ");
            regime = sc.nextLine();
        }
        while(!protectRegime(regime)){
            System.out.println("Regime inválido, por favor introduza um regime válido: \nRegimes válidos:\ntrabalhador-estudante\natleta\ndirigente associativo\naluno de erasmus\nnormal");
            regime = sc.nextLine();
        }
        al = new Aluno(nome,email,numeroAluno,anoMatricula,regime);
        for(Pessoa ps: utilizadores){
            if(ps instanceof Aluno){
                if(((Aluno) ps).equals(al)){
                    System.out.println("Aluno já existe no sistema");
                }
            }
        }
        utilizadores.add(al);
        System.out.println("Aluno adicionado ao sistema");
    }

    /**
     * Método responsável por alterar os dados de um aluno
     */

    public void alterarPessoa(){
        Scanner sc = new Scanner(System.in);
        String test,param;
        boolean t;
        System.out.println("Numero de aluno: ");
        test = sc.nextLine();
        while(!isNumeric(test)){
            System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
            test = sc.nextLine();
        }
        numeroAluno = Long.parseLong(test);
        if(!checkCodigo(utilizadores,numeroAluno)){
            System.out.println("O aluno não se encontra no sistema");
            return;
        }
        System.out.println("Parâmetro a alterar: ");
        param = sc.nextLine();
        while(!protectArgs(param)){
            System.out.println("Parâmetro não existente por favor introduza um parâmetro válido: ");
            param = sc.nextLine();
        }
        t = changeParam(utilizadores,numeroAluno,param);
        if(t){
            System.out.println("Parâmetro mudado com sucesso");
        }
    }

    /**
     * Método responsável por remover um aluno do sistema
     */

    public void removerPessoa(){
        Scanner sc = new Scanner(System.in);
        String test;
        System.out.println("Numero de aluno: ");
        test = sc.nextLine();
        while(!isNumeric(test)){
            System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
            test = sc.nextLine();
        }
        numeroAluno = Long.parseLong(test);
        if(!checkCodigo(utilizadores,numeroAluno)){
            System.out.println("O aluno não se encontra no sistema");
            return;
        }
        for(Pessoa ps: utilizadores){
            if(ps instanceof Aluno){
                if(((Aluno) ps).getNumeroAluno() == numeroAluno){
                    utilizadores.remove(((Aluno) ps));
                    nome = "done";
                    System.out.println("Aluno removido com sucesso");
                    break;
                }
            }
        }
        if(!nome.equals("done")){
            System.out.println("Aluno não existe no sistema");
        }
    }
    /**
     * Método responsável por alterar dados do aluno
     * @param utilizadores ArrayList de pessoas
     * @param numero  numero de aluno
     * @param param  parâmetro a alterar
     * @return bool Verifica o resultado da operação
     */

    public boolean changeParam(ArrayList<Pessoa> utilizadores,long numero, String param){
        String newValue;
        Scanner sc = new Scanner(System.in);
        Exame ex = new Exame();
        for(Pessoa ps: utilizadores){
            if(ps instanceof Aluno){
                if(((Aluno) ps).getNumeroAluno() == numero){
                    switch (param){
                        case "nome":
                            System.out.println("Novo nome: ");
                            newValue = sc.nextLine();
                            while(!protectChar(newValue)){
                                System.out.println("Nome inválido, por favor introduza um nome válido: ");
                                newValue = sc.nextLine();
                            }
                            ((Aluno) ps).setNome(newValue);
                            System.out.println("Nome alterado para "+newValue);
                            return true;
                        case "email":
                            System.out.println("Novo email: ");
                            newValue = sc.nextLine();
                            if(checkEmail(utilizadores,newValue)){
                                System.out.println("Aluno já se encontra no sistema");
                                break;
                            }
                            if(checkCodigo(utilizadores,numero)){
                                System.out.println("Número já se encontra associado a um aluno");
                                return false;
                            }
                            ((Aluno) ps).setEmail(newValue);
                            System.out.println("Email alterado para "+newValue);
                            return true;
                        case "numero de aluno":
                            System.out.println("Novo numero de aluno: ");
                            newValue = sc.nextLine();
                            while(!isNumeric(newValue)){
                                System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
                                newValue = sc.nextLine();
                            }
                            ((Aluno) ps).setNumeroAluno(Long.parseLong(newValue));
                            System.out.println("Numero de aluno alterado para "+Long.parseLong(newValue));
                            return true;
                        case "ano de matricula":
                            System.out.println("Novo ano de matricula: ");
                            newValue = sc.nextLine();
                            while(!isNumeric(newValue)){
                                System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
                                newValue = sc.nextLine();
                            }
                            ((Aluno) ps).setAnoMatricula(Integer.parseInt(newValue));
                            System.out.println("Ano de matrícula alterado para "+Integer.parseInt(newValue));
                            verificaAnoAluno(exames,Integer.parseInt(newValue),(Aluno) ps);
                            return true;
                        case "regime":
                            System.out.println("Novo regime: ");
                            newValue = sc.nextLine();
                            while(!protectChar(newValue)){
                                System.out.println("Regime inválido, por favor introduza um regime válido: ");
                                newValue = sc.nextLine();
                            }
                            ((Aluno) ps).setRegime(newValue);
                            System.out.println("Regime alterado para "+newValue);
                            verificaRegimeAluno(exames,newValue,(Aluno) ps);
                            return true;
                        default:
                            System.out.println("Parâmetro não reconhecido");
                            break;
                    }
                }
            }
        }
        System.out.println("Aluno não existe no sistema");
        return false;
    }

    /**
     * Método que permite verificar o ano do aluno e alterar o seu acesso a exames
     * @param exames ArrayList de exames
     * @param al  Aluno a verificar
     * @param nAnos  número de anos
     */

    public void verificaAnoAluno(ArrayList<Exame> exames,int nAnos,Aluno al){
        for(Exame exame : exames){
            if(exame.getTipoExame().equalsIgnoreCase("especial")){
                Iterator<Map.Entry<Aluno,Integer>> it = exame.getNotas().entrySet().iterator();
                while(it.hasNext()) {
                    Map.Entry<Aluno,Integer> entry = it.next();
                    if (entry.getKey().getNumeroAluno() == al.getNumeroAluno()) {
                        if(nAnos!=3){
                            it.remove();
                            System.out.println("Aluno removido do exame de "+exame.getDisciplina().getNome()+" devido à mudança de ano");
                        }
                    }
                }
                if(nAnos==3){
                    exame.getNotas().put(al,-1);
                    System.out.println("Aluno adicionado ao exame de "+exame.getDisciplina().getNome()+" da época "+exame.getTipoExame()+" devido à mudança de ano");
                }
            }
        }
    }

    /**
     * Método que permite verificar o regime do aluno e alterar o seu acesso a exames
     * @param exames ArrayList de exames
     * @param regime  regime do aluno
     * @param al  Aluno a verificar
     */

    public void verificaRegimeAluno(ArrayList<Exame> exames,String regime,Aluno al){
        for(Exame exame : exames){
            if(exame.getTipoExame().equalsIgnoreCase("especial")){
                Iterator<Map.Entry<Aluno,Integer>> it = exame.getNotas().entrySet().iterator();
                while(it.hasNext()) {
                    Map.Entry<Aluno,Integer> entry = it.next();
                    if (entry.getKey().getNumeroAluno() == al.getNumeroAluno()) {
                        if(regime.equalsIgnoreCase("normal")){
                            it.remove();
                            System.out.println("Aluno removido do exame de "+exame.getDisciplina().getNome()+" devido à mudança de regime");
                        }
                    }
                }
                if(regime.equalsIgnoreCase("atleta") || regime.equalsIgnoreCase("dirigente associativo") || regime.equalsIgnoreCase("trabalhador-estudante")){
                    exame.getNotas().put(al,-1);
                    System.out.println("Aluno adicionado ao exame de "+exame.getDisciplina().getNome()+" da época "+exame.getTipoExame()+" devido à mudança de regime");
                }
            }
        }
    }

    public long getNumeroAluno() {
        return numeroAluno;
    }

    public void setNumeroAluno(long numeroAluno) {
        this.numeroAluno = numeroAluno;
    }

    public int getAnoMatricula() {
        return anoMatricula;
    }

    public void setAnoMatricula(int anoMatricula) {
        this.anoMatricula = anoMatricula;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String toString(){
        return "Nome: "+nome+"\tEmail: "+email+"\tNúmero de aluno: "+numeroAluno;
    }
}
