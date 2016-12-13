package projeto;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public class Ficheiro {
    private ObjectInputStream iS;
    private ObjectOutputStream oS;

    public void abreLeitura(String nomeFich) throws IOException{
        iS = new ObjectInputStream(new FileInputStream(nomeFich));
    }

    public void abreEscrita(String nomeFich) throws IOException{
        oS = new ObjectOutputStream(new FileOutputStream(nomeFich));
    }

    public Object leObjeto() throws IOException,ClassNotFoundException{
            return iS.readObject();
    }

    public void escreveObjeto(Object o) throws IOException{
        oS.writeObject(o);
    }

    public void fechaLer() throws IOException{
        iS.close();
    }

    public void fechaEscrever() throws IOException{
        oS.close();
    }

    public void leFicheiroExames(ArrayList<Exame> exames) {
        FileInputStream is = null;
        ObjectInputStream ois= null;

        try {
            is = new FileInputStream(new File("exames.txt"));
            ois = new ObjectInputStream(is);
            while (true) {
                exames.add((Exame) ois.readObject());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro de exames inexistente.");
        } catch (EOFException e){
            System.out.println("Reach end of file");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ficheiro de utilizadores lido com sucesso.");
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

    public void leFicheiroCurso(ArrayList<Curso> cursos) {
        FileInputStream is = null;
        ObjectInputStream ois= null;

        try {
            is = new FileInputStream(new File("cursos.txt"));
            ois = new ObjectInputStream(is);
            while (true) {
                cursos.add((Curso) ois.readObject());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro de cursos inexistente.");
        } catch (EOFException e){
            System.out.println("Reach end of file");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ficheiro de utilizadores lido com sucesso.");
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

    public void leFicheiroSalas(ArrayList<Sala> salas) {
        FileInputStream is = null;
        ObjectInputStream ois= null;

        try {
            is = new FileInputStream(new File("salas.txt"));
            ois = new ObjectInputStream(is);
            while (true) {
                salas.add((Sala) ois.readObject());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro de salas inexistente.");
        } catch (EOFException e){
            System.out.println("Reach end of file");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ficheiro de utilizadores lido com sucesso.");
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
