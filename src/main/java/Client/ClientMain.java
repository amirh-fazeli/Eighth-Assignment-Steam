package Client;

import Shared.Request;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
                    JSONObject jsonRequest = new JSONObject(request);
                    writer.println(request);
                    if (jsonRequest.getString("type").equals("download")) {
                        recieveFile(socket,jsonRequest.getString("id"));
                    }
                }
            }

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    public static void recieveFile(Socket socket,String id) {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            Long fileSize = inputStream.readLong();

            File folder = new File("D:\\Eighth-Assignment-Steam\\src\\main\\java\\Client\\Downloads\\");
            File[] listOfFiles = folder.listFiles();
            ArrayList<String> fileNames = new ArrayList<>();

            for (File file:listOfFiles){
                if (file.getName().endsWith(".png")) {
                    fileNames.add(file.getName().substring(0,file.getName().length() - 4));
                }
            }

            int i = 1;
            String plainId = id;
            while (fileNames.contains(id)){
                id = plainId + " (" + i + ")";
                i++;
            }

            String filePath = "D:\\Eighth-Assignment-Steam\\src\\main\\java\\Client\\Downloads\\" + id + ".png";
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytesRead = 0;
            while (totalBytesRead < fileSize &&
                    (bytesRead = inputStream.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalBytesRead))) != -1) {
                bos.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }
            System.out.println("download complete");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
