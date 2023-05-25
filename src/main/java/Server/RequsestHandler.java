package Server;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

public class RequsestHandler {
    public static void requestLobby(JSONObject json,Statement statement) throws SQLException, IOException {
        String type = json.getString("request");

        if (type.equals("download")) {
            downloadRequestHandler(json);
        }

        else if (type.equals("game list")){
            gameListRequestHandler(statement);
        }

        else if (type.equals("view details")){
            detailsRequestHandler(json,statement);
        }
    }

    public static void downloadRequestHandler(JSONObject json) throws IOException{
        String id = json.getString("id");
        FileChannel src = new FileInputStream(
                "D:\\Eighth-Assignment-Steam\\src\\main\\java\\Server\\Resources\\" + id + ".png").getChannel();

        FileChannel dest = new FileOutputStream(
                "D:\\Eighth-Assignment-Steam\\src\\main\\java\\Client\\Downloads\\" + id + ".png").getChannel();

        try {
            dest.transferFrom(src, 0, src.size());
        }

        finally {
            src.close();
            dest.close();
        }
    }

    public static void gameListRequestHandler(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM games");
        resultSet.next();

        while (!resultSet.isAfterLast()){
            System.out.println("name: " + resultSet.getString("name") + ", genre: "
                    + resultSet.getString("genre") + ", ratings: " + resultSet.getString("ratings"));

            resultSet.next();
        }
    }

    public static void detailsRequestHandler(JSONObject json,Statement statement) throws SQLException {
        String id = json.getString("id");
        ResultSet result = statement.executeQuery("SELECT FROM games WHERE id = " + id);

        System.out.println("id: " + result.getString("id"));
        System.out.println("name: " + result.getString("name"));
        System.out.println("developer: " + result.getString("developer"));
        System.out.println("genre: " + result.getString("genre"));
        System.out.println("price: " + result.getString("price"));
        System.out.println("release year: " + result.getString("release_year"));
        System.out.println("controller support: " + result.getString("controller_support"));
        System.out.println("reviews: " + result.getString("reviews"));
        System.out.println("size: " + result.getString("size"));
    }
}
