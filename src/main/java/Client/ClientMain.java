package Client;

import Shared.Request;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {
            String hostname = "127.0.0.1";
            int port = 1234;

            try {
                Socket socket = new Socket(hostname, port);

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                Scanner scan = new Scanner(System.in);
                String response;

                while ((response = reader.readLine()) != null) {
                    if (!response.equals("null")) {
                        String request = Request.createRequest(new JSONObject(response), scan);
                        writer.println(request);
                    }
                }

            } catch (UnknownHostException ex) {
                System. out.println("Server not found: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("I/O error: " + ex.getMessage());
            }
        }
    }
