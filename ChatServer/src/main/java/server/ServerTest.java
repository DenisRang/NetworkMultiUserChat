package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 4004;

    private static ServerSocket serverSocket;
    private static Socket clientSocket; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) throws InterruptedException {
        try {
            try {
                serverSocket = new ServerSocket(DEFAULT_PORT);
                System.out.println("Server runs!");

                clientSocket = serverSocket.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String word = in.readLine();
                    System.out.println(word);
                    out.write("Hi from server! Approve your message: " + word + "\n");
                    out.flush(); // выталкиваем все из буфера

                } finally { // в любом случае сокет будет закрыт
                    System.out.println("dfjkhgkdf");
                    clientSocket.close();
                    // потоки тоже хорошо бы закрыть
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Server closed!");
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
