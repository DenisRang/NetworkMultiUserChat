package runnable;

import java.io.BufferedWriter;
import java.io.IOException;

public class SingleClientWriter implements Runnable {

    private BufferedWriter writer;
    private String msg;

    public SingleClientWriter(BufferedWriter writer, String msg) {
        this.writer = writer;
        this.msg = msg;
    }

    @Override
    public void run() {
        synchronized (writer) {
            try {
                writer.write(msg + '\n');
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
