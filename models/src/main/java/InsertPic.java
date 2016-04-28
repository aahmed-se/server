import models.Champion;
import mongo.Database;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by thomas on 27/04/16.
 */
public class InsertPic {
    public static byte[] LoadImage(String filePath) throws Exception {
        File file = new File(filePath);
        int size = (int)file.length();
        byte[] buffer = new byte[size];
        FileInputStream in = new FileInputStream(file);
        in.read(buffer);
        in.close();
        return buffer;
    }

    public static void main(String[] args) throws Exception {

        String path = "/home/thomas/Project/BigData-server/database/src/main/resources/loading/";


        List<Champion> list = Database.get().getDatastore().find(Champion.class).asList();
        list.forEach(champion -> {
            champion.getSkins().forEach(skin -> {
                byte[] imageBytes = new byte[0];
                try {
                    imageBytes = LoadImage(path + champion.getKey() + "_" + skin.getNum() + ".jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                skin.setLoadingArt(imageBytes);
            });
            Database.get().getDatastore().save(champion);
        });

    }
}
