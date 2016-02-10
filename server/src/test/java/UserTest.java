import java.net.UnknownHostException;

/**
 * Created by CLOE on 27/01/2016.
 */
public class UserTest {

    public static void main(String[] args) throws Exception {

        User user1 = new User("cloeamoroso", "motdepasse");
        String mdpEncrypt = user1.encryptMdp(user1.getMdp());

        User user2 = new User("andredupond", "mdp");
        mdpEncrypt = user2.encryptMdp(user2.getMdp());

    }

}
