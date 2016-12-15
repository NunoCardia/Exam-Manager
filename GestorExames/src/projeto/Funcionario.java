package projeto;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public abstract class Funcionario extends Pessoa{

    protected long numero;
    protected String categoria;
    private static final long serialVersionUID = -769630088157646555L;

    public Funcionario(){}

    public Funcionario(String nome, String email, long numero, String categoria){
        super(nome,email);
        this.numero = numero;
        this.categoria = categoria;
    }



    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
