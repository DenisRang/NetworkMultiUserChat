package sdk;

import server.ServerTest;

import java.io.*;
import java.net.Socket;

import static server.ServerTest.DEFAULT_HOST;
import static server.ServerTest.DEFAULT_PORT;

public class ChatServerApiClient implements ChatServerApi, Closeable {
    private Socket socket;
    private static BufferedReader in;
    private static BufferedWriter out;

    public ChatServerApiClient() throws IOException {
        new ChatServerApiClient(DEFAULT_HOST, DEFAULT_PORT);
    }

    public ChatServerApiClient(String host, int port) throws IOException {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            System.out.println(e.getMessage());//todo logging
            throw e;
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(message + "\n"); // отправляем сообщение на сервер
            out.flush();
            String serverWord = in.readLine(); // ждём, что скажет сервер
            System.out.println(serverWord); // получив - выводим на экран
        } catch (IOException e) {
            e.printStackTrace();
        } finally { // в любом случае необходимо закрыть сокет и потоки
            System.out.println("\nClient closed...");
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
