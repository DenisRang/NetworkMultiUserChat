package runnable;

import plugins.Factorial;
import plugins.PluginEngine;
import server.ClientsManager;

import java.io.*;
import java.net.Socket;

public class ClientReaderService implements Runnable {
    private final String COMMAND_FACTORIAL = "факториал";
    private final String COMMAND_POWER = "степень";
    private final String COMMAND_REPEAT = "повтори";
    private final String COMMAND_COMMANDS = "команды";
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
                    int indexSpace = msg.indexOf(' ');
                    String command = msg.substring(1, indexSpace);
                    String arguments = msg.substring(indexSpace + 1);
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
