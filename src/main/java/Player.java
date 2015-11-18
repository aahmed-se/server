import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

@Entity("players")
public class Player {

    private String _login;
    private String _mdp;
    private Name _nom;

    public Player() {

    }

    public Player(String _login, String _mdp, Name _nom) {
        this._login = _login;
        this._mdp = _mdp;
        this._nom = _nom;
    }

    public String get_mdp() {
        return this._mdp;
    }

    public void set_mdp(String _mdp) {
        this._mdp = _mdp;
    }

    public String get_nom() {
        return this._nom.get_nom() + " " + this._nom.get_prenom();
    }

    public String toString() {
        return this._nom.get_nom() + " " + this._nom.get_prenom() + " - Login : " + this._login;
    }

}

@Embedded
public class Name {
    private String _nom;
    private String _prenom;

    public Name(String _nom, String _prenom) {
        this._nom = _nom;
        this._prenom= _prenom;
    }

    public String get_nom() {
        return _nom;
    }

    public String get_prenom() {
        return _prenom;
    }

}
