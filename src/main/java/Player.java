import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

@Entity("players")
public class Player {

    private int _idLOL;
    private String _login;
    private int _profileIcone;
    private int _level;
    private Date _createdAt;
    private Date _updatedAt;

    public Player(String _login) {
        this._login = _login;
    }

}
