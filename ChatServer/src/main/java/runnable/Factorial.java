package runnable;

import server.ClientsManager;

import java.io.BufferedWriter;

public class Factorial implements Runnable {
    private ClientsManager clientsManager;
    private BufferedWriter writer;
    private int value;

    public Factorial(ClientsManager clientsManager, BufferedWriter writer, int value) {
        this.clientsManager = clientsManager;
        this.writer = writer;
        this.value = value;
    }

    @Override
    public void run() {
        clientsManager.sendMsgToClient(writer, String.valueOf(factorial(value)));
    }

    public int factorial(int value) {
        int result = 1;
        for (int i = 2; i <= value; i++) {
            result *= i;
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
