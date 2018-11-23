package server;

import consumers.RetransmitterService;
import producers.ClientReaderService;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 4004;

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws InterruptedException {
        RetransmitterService retransmitterService = new RetransmitterService();
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            while (true) {
                System.out.println("Server runs!");
                Socket clientSocket = serverSocket.accept();
                retransmitterService.addClient(clientSocket);
                new ClientReaderService(retransmitterService, clientSocket).run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
