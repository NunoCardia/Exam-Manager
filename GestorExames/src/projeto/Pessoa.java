package projeto;

import java.io.Serializable;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public abstract class Pessoa implements Serializable{
    protected String nome;
    protected String email;
    private static final long serialVersionUID = -5106151026260138146L;

    public Pessoa(){}

    public Pessoa(String nome,String email){
        this.nome = nome;
        this.email = email;
    }

    public void menuPessoas() {

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
