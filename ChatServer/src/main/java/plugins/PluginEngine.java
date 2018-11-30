package plugins;

import server.ClientsManager;

import java.io.BufferedWriter;
import java.io.File;

public class PluginEngine {
    private static String pluginPath;
    private static PluginLoader loader;
    private static String[] pluginNames;

    public PluginEngine(String pluginPath) {
        this.pluginPath = pluginPath;
        init();
    }

    public static void executeForClient(ClientsManager clientsManager, BufferedWriter writer, String commandName, Object... args) {
        for (String pluginName : pluginNames) {
            try {
                Class clazz = loader.loadClass(pluginName);
                Plugin plugin = (Plugin) clazz.newInstance();
                if (plugin.getCommandName().equals(commandName)) {
                    new Thread(() -> clientsManager.sendMsgToClient(writer, String.valueOf(plugin.execute(args)))).start();
                    return;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        loader = new PluginLoader(pluginPath, ClassLoader.getSystemClassLoader());
        //Получаем список доступных модулей.
        File dir = new File(pluginPath);
        pluginNames = dir.list();
    }
}