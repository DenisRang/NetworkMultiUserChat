package api;

import java.io.*;
import java.net.Socket;

import static server.ServerTest.DEFAULT_HOST;
import static server.ServerTest.DEFAULT_PORT;

public class ChatServerApiClient implements ChatServerApi, Closeable {
    private Socket socket;

    public ChatServerApiClient() throws IOException {
        new ChatServerApiClient(DEFAULT_HOST, DEFAULT_PORT);
    }

    public ChatServerApiClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        System.out.println("SD");
    }

    @Override
    public void sendMsg(String message) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            writer.write(message + "\n"); // отправляем сообщение на сервер
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getMsg() {
        String result = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            StringBuilder msg = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                msg.append(line);
                line = reader.readLine();
            }
            result = msg.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
