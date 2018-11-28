package server;

import runnable.ClientReaderService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 4044;

    public static void main(String[] args) {
        ClientsManager clientsManager = new ClientsManager();
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                new Thread(new ClientReaderService(clientsManager, clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
