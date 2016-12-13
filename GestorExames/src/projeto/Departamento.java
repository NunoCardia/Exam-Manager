package projeto;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Departamento {

    public static void main(String[] args) {
    //iniciar ficheiros
    //iniciar menu
        try {
            mainLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mainLoop() throws IOException {
        boolean t=true;
        ArrayList<Pessoa> utilizadores = new ArrayList<Pessoa>();
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        ArrayList<Exame> exames = new ArrayList<Exame>();
        ArrayList<Sala> salas = new ArrayList<Sala>();
        init(t, utilizadores,cursos,exames,salas);
        //fich.leFicheiroCurso(cursos);
        //fich.leFicheiroExames(exames);
        //fich.leFicheiroPessoa(utilizadores);
        //fich.leFicheiroSala(salas);
        handleChoice(utilizadores,cursos,exames,salas);
    }

    public static void initUtilizadores(boolean t, ArrayList<Pessoa> utilizadores) throws IOException {
        Ficheiro fich = new Ficheiro();
        File file = new File("utilizadores.txt");
        if(!file.exists() || file.length()==0){
            try {
                file.createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fich.abreLeitura("utilizadores.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(t){
            try{
                utilizadores.add((Pessoa) fich.leObjeto());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                t=false;
            } catch (EOFException e){
                System.out.println("reached end of file");
                t=false;
            } catch (IOException e) {
                e.printStackTrace();
                t=false;
            }
        }
        fich.fechaLer();
    }


    public static void init(boolean t,ArrayList<Pessoa> utilizadores,ArrayList<Curso> cursos,ArrayList<Exame> exames,ArrayList<Sala> salas) throws IOException {
        Ficheiro fich = new Ficheiro();
        fich.leFicheiroExames(exames);
        fich.leFicheiroSalas(salas);
        fich.leFicheiroCurso(cursos);
        initUtilizadores(t,utilizadores);
    }

    public static int menu(){
        Scanner sc = new Scanner(System.in);
        int option;
        while(true){
            System.out.println("GESTOR DE EXAMES\n1 - Exames\n2 - Pessoal\n3 - Cursos\n4 - Salas\n0 - Sair");
            try{
                option = Integer.parseInt(sc.nextLine());
                if(option<0 || option>5)
                    System.out.println("Numero inválido");
                else
                    return option;
            }
            catch(InputMismatchException e){
                System.out.println("INSERE UM DOS NUMEROS");
            }
        }
    }

    public static void close(ArrayList<Pessoa> utilizadores, ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas){
        Ficheiro fich = new Ficheiro();
        fich.updateUtilizadores(utilizadores);
        fich.updateExames(exames);
        fich.updateCursos(cursos);
        fich.updateSalas(salas);
        System.out.println("Ficheiros atualizados");
    }

    public static void handleChoice( ArrayList<Pessoa> utilizadores, ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas){
        Exame exame = new Exame();
        Curso curso = new Curso();
        Docente doc = new Docente();
        Ficheiro fich = new Ficheiro();
        Sala sala = new Sala();
        int number,i;
        int opcao = menu();
        Scanner sc = new Scanner(System.in);
        switch (opcao){
            case 1:
                System.out.println(exames.size());
                exame.menuExames(utilizadores,cursos,exames,salas);
                break;
            case 2:
                System.out.println("1 - Pessoal Docente\n2 - Pessoal Não Docente\n3 - Alunos");
                number = Integer.parseInt(sc.nextLine());
                switch (number){
                    case 1:
                        Pessoa ps = new Docente();
                        System.out.println("size utilizadores: "+utilizadores.size());
                        ps.menuPessoas(utilizadores,cursos,exames,salas);
                        break;
                    case 2:
                        Pessoa ps1 = new NaoDocente();
                        ps1.menuPessoas(utilizadores,cursos,exames,salas);
                        break;
                    case 3:
                        Pessoa ps2 = new Aluno();
                        ps2.menuPessoas(utilizadores,cursos,exames,salas);
                        break;
                }
                break;
            case 3:
                System.out.println(cursos.size());
                curso.menuCurso(utilizadores,cursos,exames,salas);
                break;
            case 4:
                System.out.println(salas.size());
                sala.menuSala(utilizadores,cursos,exames,salas);
                break;
            case 0:
                close(utilizadores,cursos,exames,salas);
                break;

        }
    }

}
