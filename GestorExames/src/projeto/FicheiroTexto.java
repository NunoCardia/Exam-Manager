/**
 * *
 * @author Nuno Ferreira
 */

package projeto;

import java.io.*;

/**
 * Created by cyberfox21 on 13/12/16.
 */
public class FicheiroTexto {
    BufferedWriter bw;
    FileWriter fw;

    public FicheiroTexto(){

    }

    /**
     * Método que permite escrever os dados do ficheiro de estatísticas
     * @param content  String que contém a informação a ser guardada
     */

    public void writeInFile(String content){
        try {


            File file = new File("estatisticas.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(content);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }

    }
}
