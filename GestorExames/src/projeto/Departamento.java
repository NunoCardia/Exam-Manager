/**
 * *
 * @author Nuno Ferreira
 */

package projeto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Departamento {

    public static void main(String[] args) {
        try {
            ArrayList<Pessoa> utilizadores = new ArrayList<Pessoa>();
            ArrayList<Curso> cursos = new ArrayList<Curso>();
            ArrayList<Exame> exames = new ArrayList<Exame>();
            ArrayList<Sala> salas = new ArrayList<Sala>();
            init(utilizadores,cursos,exames,salas);
            handleChoice(utilizadores,cursos,exames,salas);
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método que permite iniciar a aplicação lendo os ficheiros de objetos e texto
     * @param utilizadores ArrayList de pessoas
     * @param cursos ArrayList de cursos
     * @param exames ArrayList de exames
     * @param salas  ArrayList de salas
     * @throws IOException
     */

    public static void init(ArrayList<Pessoa> utilizadores,ArrayList<Curso> cursos,ArrayList<Exame> exames,ArrayList<Sala> salas) throws IOException {
        FicheiroObjetos fich = new FicheiroObjetos();
        fich.leFicheiroExames(exames);
        fich.leFicheiroSalas(salas);
        fich.leFicheiroCurso(cursos);
        fich.initUtilizadores(utilizadores);
    }

    /**
     * Método que permite ao utilizador escolher a opção no menu incial
     * @return int opção escolhida no menu
     */

    public static int menu(){
        Scanner sc = new Scanner(System.in);
        int option;
        while(true){
            System.out.println("GESTOR DE EXAMES\n1 - Exames\n2 - Pessoal\n3 - Cursos\n4 - Salas\n5 - Estatísticas\n0 - Sair");
            try{
                option = Integer.parseInt(sc.nextLine());
                if(option<0 || option>5)
                    System.out.println("Numero inválido");
                else
                    return option;
            }
            catch(NumberFormatException e){
                System.err.println("Introduza um número de 1 a 5");
            }
        }
    }

    /**
     * Método que permite fechar a aplicação escrevendo nos ficheiros de objetos o que está guardado nas ArrayLists
     * @param utilizadores ArrayList de pessoas
     * @param cursos ArrayList de cursos
     * @param exames ArrayList de exames
     * @param salas  ArrayList de salas
     * @throws IOException
     */

    public static void close(ArrayList<Pessoa> utilizadores, ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas) throws IOException{
        FicheiroObjetos fich = new FicheiroObjetos();
        fich.updateUtilizadores(utilizadores);
        fich.updateExames(exames);
        fich.updateCursos(cursos);
        fich.updateSalas(salas);
        System.out.println("Ficheiros atualizados");
    }

    /**
     * Método que redireciona o utilizador para a opção escolhida
     * @param utilizadores ArrayList de pessoas
     * @param cursos ArrayList de cursos
     * @param exames ArrayList de exames
     * @param salas  ArrayList de salas
     * @throws NumberFormatException
     * @throws IOException
     */

    public static void handleChoice( ArrayList<Pessoa> utilizadores, ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas){
        Exame exame = new Exame(utilizadores,cursos,exames,salas);
        Curso curso = new Curso(utilizadores,cursos,exames,salas);
        Estatisticas stats = new Estatisticas(utilizadores,salas,cursos,exames);
        Sala sala = new Sala(utilizadores,cursos,exames,salas);
        int number;
        int opcao = menu();
        Scanner sc = new Scanner(System.in);
        switch (opcao){
            case 1:
                System.out.println(exames.size());
                exame.menuExames();
                break;
            case 2:
                System.out.println("1 - Pessoal Docente\n2 - Pessoal Não Docente\n3 - Alunos");
                while (true){
                    try {
                        number = Integer.parseInt(sc.nextLine());
                        if(number< 0 ||number>3){
                            System.out.println("Número inválido");
                        }
                        else break;
                    }catch (NumberFormatException e){
                        System.err.println("Introduza um número entre 1 e 3");
                    }
                }
                switch (number){
                    case 1:
                        Pessoa ps = new Docente(utilizadores,cursos,exames,salas);
                        ps.menuPessoas();
                        break;
                    case 2:
                        Pessoa ps1 = new NaoDocente(utilizadores,cursos,exames,salas);
                        ps1.menuPessoas();
                        break;
                    case 3:
                        Pessoa ps2 = new Aluno(utilizadores,cursos,exames,salas);
                        ps2.menuPessoas();
                        break;
                }
                break;
            case 3:
                curso.menuCurso();
                break;
            case 4:
                sala.menuSala();
                break;
            case 5:
                stats.menuEstatisticas();
            case 0:
                try {
                    close(utilizadores,cursos,exames,salas);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

}
