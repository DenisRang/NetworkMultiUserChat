package server;

import consumers.ClientsManager;
import producers.ClientReaderService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8080;

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws InterruptedException {
        ClientsManager clientsManager = new ClientsManager();
        new ClientVerifier(clientsManager);
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                new ClientReaderService(clientsManager, clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
