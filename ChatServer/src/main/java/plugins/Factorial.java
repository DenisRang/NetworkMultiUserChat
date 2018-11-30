package plugins;

import server.ClientsManager;

import java.io.BufferedWriter;

public class Factorial implements Plugin {
    private final String COMMAND_NAME = "факториал";


    @Override
    public Object execute(Object... args) {
        int value = (int) args[0];
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

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
