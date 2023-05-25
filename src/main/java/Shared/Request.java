package Shared;

import Server.Menus;
import org.json.JSONObject;

import java.util.Scanner;

public class Request {
    public static String createRequest(JSONObject response,Scanner scan){
        String type = response.getString("type");

        if (type.equals("lobby menu")){
            System.out.println(Menus.lobbyMenu());
            return createLobbyMenuRequests(scan);
        }

        else if (type.equals("sign up")){
            if (response.get("status").equals("true")){
                return showUserMenuRequest();
            }

            else if (response.get("status").equals("false")){
                System.out.println("a user with this username already exists, do you want to try again? y/n");
                if (scan.nextLine().equals("y")){
                    return createSignUpRequest(scan);
                }

                else{
                    return showLobbyMenuRequest();
                }
            }
        }

        return null;
    }

    public static String showLobbyMenuRequest(){
        JSONObject json = new JSONObject();

        json.put("type","lobby menu");

        return json.toString();
    }

    public static String showUserMenuRequest(){
        JSONObject json = new JSONObject();

        json.put("type","user menu");

        return json.toString();
    }

    public static String createLobbyMenuRequests(Scanner scan){
        System.out.println("insert a number");
        int ch = Integer.parseInt(scan.nextLine());

        switch (ch){
            case 1:
                return createSignUpRequest(scan);
        }
        return null;
    }

    public static String createSignUpRequest(Scanner scan){
        JSONObject json = new JSONObject();
        json.put("type","sign up");

        System.out.println("insert your username");
        String username = scan.nextLine();
        json.put("username",username);

        System.out.println("insert your password");
        String password = scan.nextLine();
        json.put("password",password);

        return json.toString();
    }
}
