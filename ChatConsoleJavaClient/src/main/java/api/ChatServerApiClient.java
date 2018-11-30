package api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.Socket;

import static server.ServerMain.DEFAULT_HOST;
import static server.ServerMain.DEFAULT_PORT;

public class ChatServerApiClient implements ChatServerApi, Closeable {
    private static final Log logger = LogFactory.getLog(ChatServerApiClient.class);

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public ChatServerApiClient() throws IOException {
        try {
            socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }

    }

    @Override
    public void sendMsg(String message) throws IOException {
        try {
            writer.write(message + "\n");
            writer.flush();
            logger.info(String.format("Successful send the message: %s", message));
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error(String.format("Failed to send the message: %s", message));
            throw e;
        }
    }

    @Override
    public String getMsg() throws IOException {
        try {
            return reader.readLine();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void close() throws IOException {
        try {
            socket.close();
            writer.close();
            reader.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }
}
