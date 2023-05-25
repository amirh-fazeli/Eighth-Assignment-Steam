package Server;

import org.json.JSONObject;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertData {
    public static void insertUser(JSONObject json, Statement statement) throws SQLException {
        System.out.println(json);
        statement.executeUpdate("INSERT INTO users VALUES (null, '" + json.getString("username") + "', '" +
                json.getString("password") + "', null)");
    }
}
