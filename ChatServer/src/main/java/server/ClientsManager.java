package server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import runnable.SingleClientWriter;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientsManager {

    private static final Log logger = LogFactory.getLog(ClientsManager.class);
    private List<Socket> sockets = new ArrayList<>();
    private List<BufferedReader> readers = new ArrayList<>();
    private List<BufferedWriter> writers = new ArrayList<>();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
    private Date currentTime;
    private String formattedTime;
    ExecutorService clientWriterService = Executors.newCachedThreadPool();

    public synchronized void addClient(Socket clientSocket) throws IOException {
        sockets.add(clientSocket);
        readers.add(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
        writers.add(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
    }

    public synchronized void removeClient(Socket clientSocket) {
        int index = sockets.indexOf(clientSocket);
        try {
            sockets.get(index).close();
        } catch (IOException e) {
            logger.error(e);
        }
        sockets.remove(index);
        readers.remove(index);
        writers.remove(index);
        logger.info(String.format("Client %d disconnected", index));
    }

    public synchronized void retransmitToAllClients(BufferedWriter senderWriter, String msg) {
        int indexSender = writers.indexOf(senderWriter);
        for (BufferedWriter writer : writers) {
            currentTime = new Date();
            formattedTime = dateFormatter.format(currentTime);
            String msgWithSender = String.format("(%s) Client %d:  %s", formattedTime, indexSender, msg);
            sendMsgToClient(writer, msgWithSender);
        }
    }

    public void sendMsgToClient(BufferedWriter writer, String msg) {
        clientWriterService.submit(new SingleClientWriter(writer, msg));
    }

    public BufferedReader getClientReader(Socket clientSocket) {
        int indexSocket = sockets.indexOf(clientSocket);
        return readers.get(indexSocket);
    }

    public BufferedWriter getClientWriter(Socket clientSocket) {
        int indexSocket = sockets.indexOf(clientSocket);
        return writers.get(indexSocket);
    }
}
