package runnable;

import server.ClientsManager;
import server.PluginEngine;

import java.io.*;
import java.net.Socket;

public class ClientReaderService implements Runnable {
    private ClientsManager clientsManager;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientReaderService(ClientsManager clientsManager, Socket socket) {
        this.clientsManager = clientsManager;
        this.socket = socket;
        try {
            clientsManager.addClient(socket);
            reader = clientsManager.getClientReader(socket);
            writer = clientsManager.getClientWriter(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String msg = reader.readLine();
                if (isCommand(msg)) {
                    int indexEndOfCommand = (msg.indexOf(' ') != -1) ? msg.indexOf(' ') : msg.length();
                    String command = msg.substring(1, indexEndOfCommand);
                    String arguments = (indexEndOfCommand != msg.length()) ? msg.substring(indexEndOfCommand + 1) : null;
                    PluginEngine.executeForClient(clientsManager, writer, command, arguments);
                } else {
                    new Thread(new AllClientWriter(clientsManager, writer, msg)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
                clientsManager.removeClient(socket);
                return;
            }
        }
    }

    private boolean isCommand(String msg) {
        return msg != null && !msg.isEmpty() && msg.charAt(0) == '/';
    }
}
