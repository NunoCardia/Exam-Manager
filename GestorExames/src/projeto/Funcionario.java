package projeto;

/**
 * Created by cyberfox21 on 19/11/16.
 */
public abstract class Funcionario extends Pessoa{

    protected long numero;
    protected String categoria;

    public Funcionario(){}

    public Funcionario(String nome, String email, long numero, String categoria){
        super(nome,email);
        this.numero = numero;
        this.categoria = categoria;
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
