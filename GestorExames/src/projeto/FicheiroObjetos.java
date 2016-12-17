/**
 * *
 * @author Nuno Ferreira
 */

package projeto;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public class FicheiroObjetos {
    private ObjectInputStream iS;
    private ObjectOutputStream oS;

    public FicheiroObjetos(){}

    /**
     *
     * @param nomeFich nome do ficheiro
     * @throws IOException
     */

    public void abreLeitura(String nomeFich) throws IOException{
        iS = new ObjectInputStream(new FileInputStream(nomeFich));
    }

    /**
     *
     * @param nomeFich nome do Ficheiro
     * @throws IOException
     */

    public void abreEscrita(String nomeFich) throws IOException{
        oS = new ObjectOutputStream(new FileOutputStream(nomeFich));
    }

    /**
     *
     * @return Object Retorna o Objeto lido no ficheiro
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public Object leObjeto() throws IOException,ClassNotFoundException{
            return iS.readObject();
    }

    /**
     *
     * @param o Escreve no ficheiro correspondente o Objeto
     * @throws IOException
     */

    public void escreveObjeto(Object o) throws IOException{
        oS.writeObject(o);
    }

    /**
     *
     * @throws IOException
     */

    public void fechaLer() throws IOException{
        iS.close();
    }

    /**
     *
     * @throws IOException
     */

    public void fechaEscrita() throws IOException
    {
        oS.close();
    }

    /**
     * Método que permite ler os dados do ficheiro de utilizadores e guardá-los na ArrayList
     * @param utilizadores  ArrayList de Pessoas
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws EOFException
     */

    public void initUtilizadores(ArrayList<Pessoa> utilizadores) {
        boolean t = true;
        FicheiroObjetos fich = new FicheiroObjetos();
        File file = new File("utilizadores.txt");
        if(!file.exists() || file.length()==0){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            return;
        }
        try {
            fich.abreLeitura("utilizadores.txt");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        while(t){
            try{
                utilizadores.add((Pessoa) fich.leObjeto());
            } catch (ClassNotFoundException e) {
                System.err.println(e.getMessage());
                t=false;
            } catch (EOFException e){
                System.out.println("Ficheiro de utilizadores lido");
                t=false;
            } catch (IOException e) {
                System.err.println(e.getMessage());
                t=false;
            }
        }
        try {
            fich.fechaLer();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método que permite ler os dados do ficheiro de exames e guardá-los na ArrayList
     * @param exames  ArrayList de Exames
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws EOFException
     */

    public void leFicheiroExames(ArrayList<Exame> exames) throws IOException {
        FileInputStream is = null;
        ObjectInputStream ois= null;
        File file = new File("exames.txt");
        if(!file.exists()){
            file.createNewFile();
            return;
        }

        try {
            is = new FileInputStream(new File("exames.txt"));
            ois = new ObjectInputStream(is);
            while (true) {
                exames.add((Exame) ois.readObject());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro de objetos de exames inexistente.");
        } catch (EOFException e){
            System.out.println("Ficheiro exames lido");
        } catch (ClassNotFoundException e) {
            System.out.println("Nao encontrou objecto.");
        } finally {
            try {
                if (ois != null) ois.close();
            } catch(IOException e) {
                System.out.println("Erro ao fechar o ficheiro.");
            }
        }
    }

    /**
     * Método que permite ler os dados do ficheiro de cursos e guardá-los na ArrayList
     * @param cursos  ArrayList de Cursos
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws EOFException
     */

    public void leFicheiroCurso(ArrayList<Curso> cursos) throws IOException {
        FileInputStream is = null;
        ObjectInputStream ois= null;

        File file = new File("cursos.txt");
        if(!file.exists()){
            file.createNewFile();
            return;
        }

        try {
            is = new FileInputStream(new File("cursos.txt"));
            ois = new ObjectInputStream(is);
            while (true) {
                cursos.add((Curso) ois.readObject());
            }
        } catch (FileNotFoundException e) {
            System.out.println("FicheiroObjetos de cursos inexistente.");
        } catch (EOFException e){
            System.out.println("Ficheiro de cursos lido");
        } catch (ClassNotFoundException e) {
            System.out.println("Nao encontrou objecto.");
        } finally {
            try {
                if (ois != null) ois.close();
            } catch(IOException e) {
                System.out.println("Erro ao fechar o ficheiro.");
            }
        }
    }

    /**
     * Método que permite ler os dados do ficheiro de salas e guardá-los na ArrayList
     * @param salas  ArrayList de Salas
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws EOFException
     */

    public void leFicheiroSalas(ArrayList<Sala> salas) throws IOException {
        FileInputStream is = null;
        ObjectInputStream ois= null;
        File file = new File("salas.txt");
        if(!file.exists()){
            file.createNewFile();
            return;
        }

        try {
            is = new FileInputStream(file);
            ois = new ObjectInputStream(is);
            while (true) {
                salas.add((Sala) ois.readObject());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro de objetos de salas inexistente.");
        } catch (EOFException e){
            System.out.println("Ficheiro de salas lido");
        } catch (ClassNotFoundException e) {
            System.out.println("Nao encontrou objecto.");
        } finally {
            try {
                if (ois != null) ois.close();
            } catch(IOException e) {
                System.out.println("Erro ao fechar o ficheiro.");
            }
        }
    }

    /**
     * Método que permite escrever os dados no ficheiro de cursos
     * @param cursos  ArrayList de Cursos
     * @throws IOException
     * @throws FileNotFoundException
     */

    public void updateCursos(ArrayList<Curso> cursos) {
        File temp = new File("cursos.txt");
        FileOutputStream osfinal = null;
        ObjectOutputStream oosfinal = null;
        temp.delete();
        try {
            osfinal = new FileOutputStream("cursos.txt");
            oosfinal = new ObjectOutputStream(osfinal);
            for (int i = 0; i < cursos.size(); i++) {
                oosfinal.writeObject(cursos.get(i));
            }
        } catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        } catch (IOException e){
            System.err.println(e.getMessage());
        } finally {
            try {
                if (oosfinal != null) oosfinal.close();
            } catch(IOException e){
                System.err.println("Erro ao fechar o ficheiro.");
            }
        }
    }

    /**
     * Método que permite escrever os dados no ficheiro de exames
     * @param exames  ArrayList de Exames
     * @throws IOException
     * @throws FileNotFoundException
     */

    public void updateExames(ArrayList<Exame> exames) {
        File temp = new File("exames.txt");
        FileOutputStream osfinal = null;
        ObjectOutputStream oosfinal = null;
        temp.delete();
        try {
            osfinal = new FileOutputStream(temp);
            oosfinal = new ObjectOutputStream(osfinal);
            for (int i = 0; i < exames.size(); i++) {
                oosfinal.writeObject(exames.get(i));
            }
        } catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        } catch (IOException e){
            System.err.println(e.getMessage());
        } finally {
            try {
                if (oosfinal != null) oosfinal.close();
            } catch(IOException e){
                System.err.println("Erro ao fechar o ficheiro.");
            }
        }
    }

    /**
     * Método que permite escrever os dados no ficheiro de salas
     * @param salas  ArrayList de Salas
     * @throws IOException
     * @throws FileNotFoundException
     */

    public void updateSalas(ArrayList<Sala> salas) {
        File temp = new File("salas.txt");
        FileOutputStream osfinal = null;
        ObjectOutputStream oosfinal = null;
        temp.delete();
        try {
            osfinal = new FileOutputStream(temp);
            oosfinal = new ObjectOutputStream(osfinal);
            for (int i = 0; i < salas.size(); i++) {
                oosfinal.writeObject(salas.get(i));
            }
        } catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        } catch (IOException e){
            System.err.println(e.getMessage());
        } finally {
            try {
                if (oosfinal != null) oosfinal.close();
            } catch(IOException e){
                System.err.println("Erro ao fechar o ficheiro.");
            }
        }
    }

    /**
     * Método que permite escrever os dados no ficheiro de utilizadores
     * @param utilizadores  ArrayList de Pessoas
     * @throws IOException
     */

    public void updateUtilizadores(ArrayList<Pessoa> utilizadores){
        int i=0;
        try {
            abreEscrita("utilizadores.txt");
            for(i=0;i<utilizadores.size();i++){
                escreveObjeto(utilizadores.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
