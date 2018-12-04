package runnable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import server.PluginEngine;

import java.io.BufferedWriter;
import java.io.IOException;

public class SingleClientWriter implements Runnable {
    private static final Log logger = LogFactory.getLog(SingleClientWriter.class);

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
                logger.error(e);
            }
        }
    }
}
