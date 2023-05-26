package Shared;

import Client.Menus;
import Client.User;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Request {
    public static String createRequest(JSONObject response,Scanner scan){
        String type = response.getString("type");

        if (type.equals("lobby menu")){
            return createLobbyMenuRequests(scan);
        }

        else if (type.equals("user menu")){
            return createUserMenuRequests(scan,new User(response));
        }

        else if (type.equals("sign up")){
            if (response.get("status").equals("true")){
                return showUserMenuRequest(new User(response));
            }

            else if (response.get("status").equals("false")){
                System.out.println("a user with this username already exists\ndo you want to try again? y/n");
                if (scan.nextLine().equals("y")){
                    return createSignUpRequest(scan);
                }

                else{
                    return showLobbyMenuRequest();
                }
            }
        }

        else if (type.equals("log in")){
            if (response.getString("status").equals("true")){
                return showUserMenuRequest(new User(response));
            }

            else {
                System.out.println(response.getString("reason"));
                System.out.println("do you want to try again? y/n");

                if (scan.nextLine().equals("y")){
                    return createLogInRequest(scan);
                }

                else{
                    return showLobbyMenuRequest();
                }
            }
        }

        else if (type.equals("view game list")){
            showGameList(response.getJSONObject("games"));
        }

        return null;
    }

    private static List<String> showGameList(JSONObject games) {
        List<String> ids =List.copyOf(games.keySet());

        for(int i=0;i<ids.size();i++){
            JSONObject game = games.getJSONObject(ids.get(i));
            System.out.println(i+1 + ". " + game.getString("title") + ", " + game.getString("genre") +
                    ", " + game.getString("reviews"));
        }

        return ids;
    }

    private static String createUserMenuRequests(Scanner scan, User user) {
        System.out.println(user.getUsername());
        System.out.println(Menus.userMenu());
        System.out.println("insert a number");
        int ch = Integer.parseInt(scan.nextLine());

        switch (ch){
            case 1:
                return viewGameListRequest(user);
            case 3:
                return showLobbyMenuRequest();
        }

        return null;
    }

    private static String viewGameListRequest(User user) {
        JSONObject json = new JSONObject();

        json.put("type", "view games");
        json.put("user",user.userTOJson());

        return json.toString();
    }


    public static String showLobbyMenuRequest(){
        JSONObject json = new JSONObject();

        json.put("type","lobby menu");

        return json.toString();
    }

    public static String showUserMenuRequest(User user){
        JSONObject json = new JSONObject();

        json.put("type","user menu");
        json.put("username", user.getUsername());

        return json.toString();
    }

    public static String createLobbyMenuRequests(Scanner scan){
        System.out.println(Menus.lobbyMenu());
        System.out.println("insert a number");
        int ch = Integer.parseInt(scan.nextLine());

        switch (ch){
            case 1:
                return createSignUpRequest(scan);

            case 2:
                return createLogInRequest(scan);
        }
        return null;
    }

    private static String createLogInRequest(Scanner scan) {
        JSONObject json = new JSONObject();
        json.put("type","log in");

        System.out.println("insert your username");
        String username = scan.nextLine();
        json.put("username",username);

        System.out.println("insert your password");
        String password = scan.nextLine();
        json.put("password",password);

        return json.toString();
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
