/**
 * *
 * @author Nuno Ferreira
 */

package projeto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public class NaoDocente extends Funcionario implements Serializable{
    protected String cargo;
    protected ArrayList<Pessoa> utilizadores;
    protected ArrayList<Curso> cursos;
    protected ArrayList<Exame> exames;
    protected ArrayList<Sala> salas;
    protected static final long serialVersionUID = -6556321652959160285L;

    public NaoDocente(){}

    public NaoDocente(String nome, String email, long numero, String categoria,String cargo){
        super(nome, email, numero, categoria);
        this.cargo = cargo;
    }

    public NaoDocente(ArrayList<Pessoa> utilizadores, ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas){
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
        if(!param.equalsIgnoreCase("nome") && !param.equalsIgnoreCase("email") && !param.equalsIgnoreCase("numero") && !param.equalsIgnoreCase("categoria") &&
                !param.equalsIgnoreCase("cargo")){
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

    public static boolean protectChar(String input) {
        String simbols="?!.,;:-_`´^/()%&$#[]{}=+*|\"";
        char [] items = input.replaceAll("\\s+","").toCharArray();
        for(char c: items) {
            if(!Character.isLetter(c) || simbols.indexOf(c)!=-1) {
                return false;

            }
        }
        return true;
    }

    /**
     * Método que permite proteger a aplicação caso o utilizador introduza dois utilizadores iguais
     * @param utilizadores ArrayList de pessoas
     * @param email  email da nova Pessoa
     * @return bool Verifica se o aluno já existe no sistema ou não
     */

    public boolean checkEmail(ArrayList<Pessoa> utilizadores,String email){
        for(Pessoa ps: utilizadores){
            if(ps instanceof NaoDocente){
                if(((NaoDocente) ps).getEmail().equalsIgnoreCase(email)){
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
            if(ps instanceof NaoDocente){
                if(((NaoDocente) ps).getNumero()==codigo){
                    return true;
                }
            }
        }
        return false;
    }

    @Override

    /**
     * Método que apresenta ao utilizador o menu para esta classe
     */
    public void menuPessoas() {
        String nome = null,email,categoria,cargo,param,test;
        Scanner sc = new Scanner(System.in);
        NaoDocente doc;
        Departamento dep = new Departamento();
        long numero;
        int valor;
        boolean control;
        do {
            System.out.println("MENU DE NÃO DOCENTE\n1 - Adicionar não docente\n2 - Alterar dados de não docente\n3 - Remover não docente\n0 - Voltar ao menu inicial");
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
        }while(valor!=0);
    }

    /**
     * Método responsável por remover um aluno do sistema
     */
    public void removerPessoa(){
        Scanner sc = new Scanner(System.in);
        String test;
        System.out.println("Número mecanicográfico do não docente: ");
        test = sc.nextLine();
        while(!isNumeric(test)){
            System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
            test = sc.nextLine();
        }
        numero = Long.parseLong(test);
        if(!checkCodigo(utilizadores,numero)){
            System.out.println("O não docente não se encontra no sistema");
            return;
        }
        for(Pessoa ps: utilizadores){
            if(ps instanceof NaoDocente){
                if(((NaoDocente) ps).getNumero() == numero){
                    utilizadores.remove(((NaoDocente) ps));
                    nome = "done";
                    System.out.println("Não docente removido com sucesso");
                    break;
                }
            }
        }
        if(!nome.equals("done")){
            System.out.println("Não docente não existe no sistema");
        }
    }
    /**
     * Método responsável por alterar os dados de um aluno
     */
    public void alterarPessoa(){
        String test,param;
        Scanner sc = new Scanner(System.in);
        boolean t;
        System.out.println("Número mecanicográfico do não docente: ");
        test = sc.nextLine();
        while(!isNumeric(test)){
            System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
            test = sc.nextLine();
        }
        numero = Long.parseLong(test);
        if(!checkCodigo(utilizadores,numero)){
            System.out.println("O não docente não se encontra no sistema");
            return;
        }
        System.out.println("Parâmetro a alterar: ");
        param = sc.nextLine();
        while(!protectArgs(param)){
            System.out.println("Parâmetro não existente por favor introduza um parâmetro válido: ");
            param = sc.nextLine();
        }
        t = changeParam(utilizadores,numero,param);
        if(t){
            System.out.println("Parâmetro mudado com sucesso");
        }
    }

    /**
     * Método responsável por inserir um novo aluno no sistema
     */
    public void inserirPessoa(){
        Scanner sc = new Scanner(System.in);
        String test;
        NaoDocente doc;
        System.out.println("Nome: ");
        nome = sc.nextLine();
        while(!protectChar(nome)){
            System.out.println("Nome inválido, por favor introduza um nome válido: ");
            nome = sc.nextLine();
        }
        System.out.println("Email: ");
        email = sc.nextLine();
        if(checkEmail(utilizadores,email)){
            System.out.println("Não docente já se encontra no sistema");
            return;
        }
        System.out.println("Número: ");
        test = sc.nextLine();
        while(!isNumeric(test)){
            System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
            test = sc.nextLine();
        }
        numero = Long.parseLong(test);
        if(checkCodigo(utilizadores,numero)){
            System.out.println("Não docente já se encontra no sistema");
            return;
        }
        System.out.println("Categoria: ");
        categoria = sc.nextLine();
        while(!protectChar(categoria)){
            System.out.println("Categoria inválida, por favor introduza uma categoria válida: ");
            categoria = sc.nextLine();
        }
        System.out.println("Cargo: ");
        cargo = sc.nextLine();
        while(!protectChar(cargo)){
            System.out.println("Cargo inválido, por favor introduza um cargo válido: ");
            cargo = sc.nextLine();
        }
        doc = new NaoDocente(nome,email,numero,categoria,cargo);
        utilizadores.add(doc);
        System.out.println("Não docente adicionado ao sistema");
    }

    /**
     * Método responsável por alterar dados do nao docente
     * @param utilizadores ArrayList de pessoas
     * @param numero  numero de aluno
     * @param param  parâmetro a alterar
     * @return bool Verifica o resultado da operação
     */

    public boolean changeParam(ArrayList<Pessoa> utilizadores, long numero, String param) {
        String newValue;
        Scanner sc = new Scanner(System.in);
        for(Pessoa ps: utilizadores){
            if(ps instanceof NaoDocente){
                if(((NaoDocente) ps).getNumero() == numero){
                    switch (param){
                        case "nome":
                            System.out.println("Novo nome: ");
                            newValue = sc.nextLine();
                            while(!protectChar(newValue)){
                                System.out.println("Nome inválido, por favor introduza um nome válido: ");
                                newValue = sc.nextLine();
                            }
                            ((NaoDocente) ps).setNome(newValue);
                            System.out.println("Nome alterado para "+newValue);
                            return true;
                        case "email":
                            System.out.println("Novo email: ");
                            newValue = sc.nextLine();
                            if(checkEmail(utilizadores,newValue)){
                                System.out.println("Não docente já se encontra no sistema");
                                break;
                            }
                            ((NaoDocente) ps).setEmail(newValue);
                            System.out.println("Email alterado para "+newValue);
                            return true;
                        case "numero":
                            System.out.println("Novo numero de não docente: ");
                            newValue = sc.nextLine();
                            while(!isNumeric(newValue)){
                                System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
                                newValue = sc.nextLine();
                            }
                            if(checkCodigo(utilizadores,numero)){
                                System.out.println("Número já se encontra associado a um não docente");
                                return false;
                            }
                            ((NaoDocente) ps).setNumero(Long.parseLong(newValue));
                            System.out.println("Numero alterado para "+Long.parseLong(newValue));
                            return true;
                        case "cargo":
                            System.out.println("Cargo: ");
                            newValue = sc.nextLine();
                            while(!protectChar(newValue)){
                                System.out.println("Cargo inválido, por favor introduza um cargo válido: ");
                                newValue = sc.nextLine();
                            }
                            ((NaoDocente) ps).setCargo(newValue);
                            System.out.println("Cargo alterado para "+newValue);
                            return true;
                        case "categoria":
                            System.out.println("Nova categoria: ");
                            newValue = sc.nextLine();
                            while(!protectChar(newValue)){
                                System.out.println("Categoria inválido, por favor introduza um categoria válida: ");
                                newValue = sc.nextLine();
                            }
                            ((NaoDocente) ps).setCategoria(newValue);
                            System.out.println("Categoria alterado para "+newValue);
                            return true;
                        default:
                            System.out.println("Parâmetro não reconhecido");
                            break;
                    }
                }
            }
        }
        System.out.println("O não docente não existe no sistema");
        return false;
    }

    /**
     * Método responsável por retornar um objeto do tipo Nao Docente
     * @param pessoas ArrayList de pessoas
     * @param numero número do Nao Docente
     * @return NaoDocente objeto do tipo Nao Docente
     */

    public NaoDocente checkNaoDocente(ArrayList<Pessoa> pessoas, long numero){
        for(Pessoa ndoc: pessoas){
            if(ndoc instanceof NaoDocente){
                if(((NaoDocente) ndoc).getNumero() == numero){
                    return ((NaoDocente) ndoc);
                }
            }
        }
        return null;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String toString(){
        return "Nome: "+nome+"\tEmail: "+email+"\tNúmero: "+numero;
    }
}
