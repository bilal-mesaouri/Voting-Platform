
import java.util.Map;
import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {

    public String numero;
    public String motDePasse;
    public boolean avote = false;
    public Map<Integer, Integer> votes = new HashMap<Integer, Integer>();

    @Override
    public String toString() {
        System.out.println("");
        return "asd";
    }

    public User(String numero, String motDePasse) {
        this.numero=numero;
        this.motDePasse = motDePasse;
    }

}
