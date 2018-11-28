package consumers;

import java.io.BufferedWriter;

public class Factorial extends Thread {
    private BufferedWriter writer;
    private int value;

    public Factorial(BufferedWriter writer, int value) {
        this.writer = writer;
        this.value = value;
        start();
    }

    @Override
    public void run() {
        super.run();
        new SingleClientWriter(writer, String.valueOf(factorial(value)));
    }

    public int factorial(int value) {
        int result = 1;
        for (int i = 2; i <= value; i++) {
            result *= i;
        }
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
