package server;

public interface Plugin {

    public Object execute(Object... args);

    public String getCommandName();

}
