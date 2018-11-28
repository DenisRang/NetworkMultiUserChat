package api;

import java.io.*;
import java.net.Socket;

import static server.ServerMain.DEFAULT_HOST;
import static server.ServerMain.DEFAULT_PORT;

public class ChatServerApiClient implements ChatServerApi, Closeable {
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public ChatServerApiClient() throws IOException {
        socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void sendMsg(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getMsg() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        writer.close();
        reader.close();
    }
}
