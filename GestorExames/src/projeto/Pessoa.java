package projeto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public abstract class Pessoa implements Serializable{
    protected String nome;
    protected String email;

    public Pessoa(){}

    public Pessoa(String nome,String email){
        this.nome = nome;
        this.email = email;
    }

    public void menuPessoas(ArrayList<Pessoa> utilizadores, ArrayList<Curso> cursos, ArrayList<Exame> exames, ArrayList<Sala> salas) {
        //menu para inserir, alterar e remover utilizadores
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                +
                '}';
    }
}
