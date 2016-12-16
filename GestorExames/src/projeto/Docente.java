package projeto;

import java.io.Serializable;
import java.util.*;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public class Docente extends Funcionario implements Serializable{
    protected String areaInvestigacao;
    protected ArrayList<Pessoa> utilizadores;
    protected ArrayList<Curso> cursos;
    protected ArrayList<Exame> exames;
    protected ArrayList<Sala> salas;
    private static final long serialVersionUID = -4881665133653454264L;

    public Docente(ArrayList<Pessoa> utilizadores, ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas) {
        this.utilizadores = utilizadores;
        this.cursos = cursos;
        this.exames = exames;
        this.salas = salas;
    }

    public Docente(){}

    public Docente(String nome, String email, long numero, String categoria, String areaInvestigacao){
        super(nome,email,numero,categoria);
        this.areaInvestigacao = areaInvestigacao;
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

    public boolean checkEmail(ArrayList<Pessoa> utilizadores,String email){
        for(Pessoa ps: utilizadores){
            if(ps instanceof Docente){
                if(((Docente) ps).getEmail().equalsIgnoreCase(email)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCodigo(ArrayList<Pessoa> utilizadores,long codigo){
        for(Pessoa ps: utilizadores){
            if(ps instanceof Docente){
                if(((Docente) ps).getNumero()==codigo){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void menuPessoas() {
        String nome = null,email,categoria,areaInvestigacao,param,test;
        Scanner sc = new Scanner(System.in);
        long numero;
        int valor = 0;
        boolean control;
        Departamento dep = new Departamento();
        do {
            System.out.println(utilizadores.size());
            System.out.println("MENU DE DOCENTE\n1 - Adicionar docente\n2 - Alterar dados de docente\n3 - Remover docente\n0 - voltar ao menu inicial");
            Docente doc;
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
                    System.out.println("Nome do docente: ");
                    nome = sc.nextLine();
                    while(!protectChar(nome)){
                        System.out.println("Nome inválido, por favor introduza um nome válido: ");
                        nome = sc.nextLine();
                    }
                    System.out.println("Email: ");
                    email = sc.nextLine();
                    if(checkEmail(utilizadores,email)){
                        System.out.println("Docente já se encontra no sistema");
                        break;
                    }
                    System.out.println("Número de docente: ");
                    test = sc.nextLine();
                    while(!isNumeric(test)){
                        System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
                        test = sc.nextLine();
                    }
                    numero = Long.parseLong(test);
                    if(checkCodigo(utilizadores,numero)){
                        System.out.println("Docente já se encontra no sistema");
                        break;
                    }
                    System.out.println("Categoria do docente: ");
                    categoria = sc.nextLine();
                    while(!protectChar(categoria)){
                        System.out.println("Categoria inválida, por favor introduza uma categoria válida: ");
                        categoria = sc.nextLine();
                    }
                    System.out.println("Área de investigação do docente: ");
                    areaInvestigacao = sc.nextLine();
                    while(!protectChar(areaInvestigacao)){
                        System.out.println("Área de investigação inválida, por favor introduza uma área de investigação válida: ");
                        areaInvestigacao = sc.nextLine();
                    }
                    doc = new Docente(nome,email,numero,categoria,areaInvestigacao);
                    for(Pessoa ps: utilizadores){
                        if(ps instanceof Docente){
                            if(((Docente) ps).equals(doc)){
                                System.out.println("Docente já existe no sistema");
                                break;
                            }
                        }
                    }
                    utilizadores.add(doc);
                    System.out.println("Docente adicionado ao sistema");
                    break;
                case 2:
                    boolean t = false;
                    System.out.println("Número mecanicográfico do docente: ");
                    test = sc.nextLine();
                    while(!isNumeric(test)){
                        System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
                        test = sc.nextLine();
                    }
                    numero = Long.parseLong(test);
                    if(!checkCodigo(utilizadores,numero)){
                        System.out.println("O docente não se encontra no sistema");
                        break;
                    }
                    System.out.println("Parâmetro a alterar: ");
                    param = sc.nextLine();
                    while(!protectArgs(param)){
                        System.out.println("Parâmetro não existente por favor introduza um parâmetro válido: ");
                        param = sc.nextLine();
                    }
                    t = changeParam(utilizadores,numero,param);
                    break;
                case 3:
                    System.out.println("Número mecanicográfico do docente: ");
                    test = sc.nextLine();
                    while(!isNumeric(test)){
                        System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
                        test = sc.nextLine();
                    }
                    numero = Long.parseLong(test);
                    if(!checkCodigo(utilizadores,numero)){
                        System.out.println("O docente não se encontra no sistema");
                        break;
                    }
                    for(Pessoa ps: utilizadores){
                        if(ps instanceof Docente){
                            if(((Docente) ps).getNumero() == numero){
                                utilizadores.remove(((Docente) ps));
                                nome = "done";
                                System.out.println("Docente removido com sucesso");
                                break;
                            }
                        }
                    }
                    if(!nome.equals("done")){
                        System.out.println("Docente não existe no sistema");
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

    private boolean changeParam(ArrayList<Pessoa> utilizadores, long numero, String param) {
        String newValue;
        Scanner sc = new Scanner(System.in);
        for(Pessoa ps: utilizadores){
            if(ps instanceof Docente){
                if(((Docente) ps).getNumero() == numero){
                    switch (param){
                        case "nome":
                            System.out.println("Novo nome: ");
                            newValue = sc.nextLine();
                            while(!protectChar(newValue)){
                                System.out.println("Nome inválido, por favor introduza um nome válido: ");
                                newValue = sc.nextLine();
                            }
                            ((Docente) ps).setNome(newValue);
                            System.out.println("Nome alterado para "+newValue);
                            return true;
                        case "email":
                            System.out.println("Novo email: ");
                            newValue = sc.nextLine();
                            if(checkEmail(utilizadores,newValue)){
                                System.out.println("Docente já se encontra no sistema");
                                break;
                            }
                            ((Docente) ps).setEmail(newValue);
                            System.out.println("Email alterado para "+newValue);
                            return true;
                        case "numero":
                            System.out.println("Novo numero de docente: ");
                            newValue = sc.nextLine();
                            while(!isNumeric(newValue)){
                                System.out.println("O que introduziu não é um número, por favor introduza um número válido: ");
                                newValue = sc.nextLine();
                            }
                            if(checkCodigo(utilizadores,numero)){
                                System.out.println("Número já se encontra associado a um docente");
                                return false;
                            }
                            ((Docente) ps).setNumero(Long.parseLong(newValue));
                            System.out.println("Numero alterado para "+Long.parseLong(newValue));
                            return true;
                        case "area de investigacao":
                            System.out.println("Nova área de investigação: ");
                            newValue = sc.nextLine();
                            while(!protectChar(newValue)){
                                System.out.println("Área de investigação inválida, por favor introduza uma área de investigação válido: ");
                                newValue = sc.nextLine();
                            }
                            ((Docente) ps).setAreaInvestigacao(newValue);
                            System.out.println("Área de investigação alterada para "+newValue);
                            return true;
                        case "categoria":
                            System.out.println("Nova categoria: ");
                            newValue = sc.nextLine();
                            while(!protectChar(newValue)){
                                System.out.println("Categoria inválido, por favor introduza um categoria válida: ");
                                newValue = sc.nextLine();
                            }
                            ((Docente) ps).setCategoria(newValue);
                            System.out.println("Categoria alterado para "+newValue);
                            return true;
                        default:
                            System.out.println("Parâmetro não reconhecido");
                            break;
                    }
                }
            }
        }
        System.out.println("O docente não existe no sistema");
        return false;
    }


    public void desconvocarDocente(Exame ex ,Docente doc){
        Iterator<Docente> iter = ex.getVigilantes().iterator();

        while (iter.hasNext()) {
            Docente docente = iter.next();

            if(docente.getNome().equalsIgnoreCase(doc.getNome()))
                iter.remove();
        }
    }

    public boolean inVigilantesList(Docente doc, ArrayList<Docente> vigilantes){
        for (Docente docente: vigilantes){
            if(docente.getNome().equalsIgnoreCase(doc.getNome())){
                return true;
            }
        }
        return false;
    }

    public boolean checkDocenteDisponivel(Exame ex, Docente doc,Disciplina disc,Date date,Date finalDate) {
        //procurar em todos os exames: se existir um exame aquela hora ou que esteja a decorrer a essa hora
        if(ex.getDisciplina().getRegente().getNome().equalsIgnoreCase(doc.getNome())){
            System.out.println("Docente "+doc.getNome()+" não pode vigiar o exame uma vez que já tem um exame marcado para essa hora e é o regente " +
                    "dessa cadeira");
            return false;
        }
        if(!inVigilantesList(doc,ex.getVigilantes())){
            System.out.println("Docente "+doc.getNome()+" disponível para vigiar o exame");
            return true;
        }
        for (Docente docente: ex.getVigilantes()){
            if(disc.getRegente().getNome().equalsIgnoreCase(docente.getNome())){
                System.out.println("Docente "+docente.getNome()+" é convocado para este exame uma vez que é o regente\nA desconvocar docente do exame...");
                desconvocarDocente(ex,docente);
                System.out.println("Docente "+docente.getNome()+" desconvocado do exame");
                return true;
            }
        }
        if(inVigilantesList(doc,ex.getVigilantes())){
            System.out.println("Docente "+doc.getNome()+" não pode vigiar o exame uma vez que já se encontra a vigiar outro exame dentro do horário especificado");
            return false;
        }
        return true;
    }

    public Docente parseDocente(ArrayList<Pessoa> pessoas, String nome){
        for(Pessoa doc: pessoas){
            if(doc instanceof Docente){
                if(((Docente) doc).getNome().equalsIgnoreCase(nome)){
                    return (Docente) doc;
                }
            }
        }
        return null;
    }

    public String getAreaInvestigacao() {
        return areaInvestigacao;
    }

    public void setAreaInvestigacao(String areaInvestigacao) {
        this.areaInvestigacao = areaInvestigacao;
    }

    public String toString(){
        return "Nome: "+nome+"\tEmail: "+email+"\tNúmero: "+numero;
    }

}
