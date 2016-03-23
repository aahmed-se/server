import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class GameTest {

    static Database bdd = new Database();

    public static void main(String[] args) throws UnknownHostException {

        ArrayList<Summoner> observers = new ArrayList<Summoner>();
        ArrayList<Summoner> participants = new ArrayList<Summoner>();

        Summoner s = new Summoner(1,1,"a", 1,1,1);
        Summoner s2 = new Summoner(2,2,"a", 2,2,2);
        Summoner s3 = new Summoner(3,3,"a", 3,3,3);
        Summoner s4 = new Summoner(4,4,"a", 4,4,4);

        observers.add(s);
        participants.add(s2);
        participants.add(s3);
        participants.add(s4);

        ArrayList<BannedChampion> bannedChampions = new ArrayList<BannedChampion>();

        BannedChampion bc = new BannedChampion(1,1,1);
        BannedChampion bc2 = new BannedChampion(2,2,2);
        BannedChampion bc3 = new BannedChampion(3,3,3);

        bannedChampions.add(bc);
        bannedChampions.add(bc2);
        bannedChampions.add(bc3);

        Game g = new Game(1,5,"mode", 1, bannedChampions, "type", 1, observers, 1, 1, participants, "platformId");

        com.mongodb.DB database = bdd.initBDD();

        BasicDBObject doc1 = new BasicDBObject();
        doc1.put("user",s);
        doc1.put("user",s2);
        doc1.put("user",s3);
        doc1.put("user",s4);
        DBCollection items = bdd.initBDD().getCollection("user");
        items.insert(doc1);

        //bdd.initBDD().save(g);
    }

}