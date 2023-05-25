package Server;

import Shared.Response;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class ServerMain {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException, SQLException {
        int portNumber = 1234;
        ServerMain server = new ServerMain(portNumber);
        server.start();
    }

    public ServerMain(int portNumber) throws IOException, SQLException {
        this.serverSocket = new ServerSocket(portNumber);
    }

    public void start() throws SQLException {
        System.out.println("Server started.");
        Connection connection = connectSQL();

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getRemoteSocketAddress());
                ClientHandler handler = new ClientHandler(socket,connection);
                clients.add(handler);
                handler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection connectSQL() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/steam";
        String user = "postgres";
        String pass = "12345";
        Connection connection = DriverManager.getConnection(url, user, pass);
        System.out.println("Connected to the PostgreSQL database!");
        return connection;
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private Connection connection;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket, Connection connection) throws IOException {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.connection = connection;
        }

        public void run() {
            Statement statement = null;
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            broadcast(Response.lobbyMenuResponse(),this);
            String request;
            try {
                while (!(request = in.readLine()).equals("null")) {
                    System.out.println(request);
                    String response = Response.responseCreator(new JSONObject(request),statement);
                    System.out.println(response);
                    broadcast(response,this);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            finally {
                try {
                    socket.close();
                    clients.remove(this);
                    statement.close();
                    connection.close();
                    System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcast(String message,ClientHandler client) {
                client.out.println(message);
        }
    }
}
