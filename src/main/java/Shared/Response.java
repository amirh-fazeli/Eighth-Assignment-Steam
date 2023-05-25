package Shared;

import Server.InsertData;
import Server.ServerMain;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Response {
    public static String responseCreator(JSONObject request,Statement statement) throws SQLException {
        String type = request.getString("type");

        if (type.equals("lobby menu")){
            return lobbyMenuResponse();
        }

        else if (type.equals("user menu")){
            return userMenuResponse();
        }

        else if(type.equals("sign up")){
            return signInResponseCreator(doesUserExist(request,statement),statement,request);
        }

        return null;
    }

    public static String lobbyMenuResponse(){
        JSONObject json = new JSONObject();

        json.put("type","lobby menu");

        return json.toString();
    }

    public static String userMenuResponse(){
        JSONObject json = new JSONObject();

        json.put("type","user menu");

        return json.toString();
    }

    public static String signInResponseCreator(String status, Statement statement,JSONObject json) throws SQLException {
        if (status.equals("true")){
            InsertData.insertUser(json,statement);
        }

        json.put("type","sign in");
        json.put("status",status);

        return json.toString();
    }

    public static String doesUserExist(JSONObject json,Statement statement) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM users WHERE username = '" +
                json.getString("username") + "'");
        result.next();

        if (result.getInt("count") == 0){
            return "true";
        }
        return "false";
    }
}
