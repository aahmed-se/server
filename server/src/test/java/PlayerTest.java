import java.net.UnknownHostException;

public class PlayerTest {

    static DB bdd = new DB();

    public static void main(String[] args) throws UnknownHostException {
        Player p = new Player("abcdef");
        bdd.initBDD().save(p);
    }

}
