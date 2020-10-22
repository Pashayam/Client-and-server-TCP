import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    private ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Address:" + serverSocket.getLocalSocketAddress());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start() {
        try {
            while (true) {
                new clientThread(serverSocket.accept());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    class clientThread extends Thread {
        private Socket clientSocket;
        private BufferedReader readSocket;
        private BufferedWriter writeSocket;
        private String message;
        private int number;

        public clientThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                number = new Random().nextInt(200);
                readSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writeSocket = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                start();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        public void run() {
            try {
                try {
                    while (true) {
                        message = readSocket.readLine();
                        //System.out.println("Пришло сообщение от клиента:" + message);
                        try {
                            int clientNumber = Integer.parseInt(message);
                            if (clientNumber<number)message="server: more\n";
                            else if (clientNumber == number) message="server: correct\n";
                            else message="server: less\n";

                        }catch (Exception e){
                            message="server: error";
                        }
                        writeSocket.write(message);
                        writeSocket.flush();
                    }
                } finally {
                    clientSocket.close();
                    readSocket.close();
                    writeSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}