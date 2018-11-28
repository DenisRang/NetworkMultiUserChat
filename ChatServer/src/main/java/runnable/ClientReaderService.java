package runnable;

import server.ClientsManager;

import java.io.*;
import java.net.Socket;

public class ClientReaderService implements Runnable {
    private final String COMMAND_FACTORIAL = "факториал";
    private final String COMMAND_POWER = "степень";
    private final String COMMAND_REPEAT = "повтори";
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
                    int argument = Integer.valueOf(msg.substring(indexSpace + 1));
                    String command = msg.substring(1, indexSpace);
                    switch (command) {
                        case COMMAND_FACTORIAL:
                            new Thread(new Factorial(clientsManager, writer, argument)).start();
                            break;
                        case COMMAND_POWER:
                            break;
                        case COMMAND_REPEAT:
                            break;
                    }
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
