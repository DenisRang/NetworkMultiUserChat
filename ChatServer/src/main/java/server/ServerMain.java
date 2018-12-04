package server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import runnable.ClientReaderService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;

public class ServerMain {
    private static final Log logger = LogFactory.getLog(ServerMain.class);
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 4044;
    public static final String DEFAULT_PLUGIN_PATH = Paths.get("plugins").toAbsolutePath().toString();

    public static void main(String[] args) {
        PluginEngine pluginEngine = new PluginEngine(DEFAULT_PLUGIN_PATH);
        ClientsManager clientsManager = new ClientsManager();
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                logger.info("New client connected");
                new Thread(new ClientReaderService(clientsManager, clientSocket)).start();
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
