package server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginEngine {
    private static final Log logger = LogFactory.getLog(PluginEngine.class);
    private static final String COMMAND_COMMANDS = "commands";
    private static final String INVALID_COMMAND_MESSAGE = "Не правильно введена команда!";
    private static String pluginPath;
    private static String[] pluginNames;

    public PluginEngine(String pluginPath) {
        this.pluginPath = pluginPath;
        init();
    }

    public static void executeForClient(ClientsManager clientsManager, BufferedWriter writer, String commandName, String args) {
        StringBuilder commands = new StringBuilder();
        for (String pluginName : pluginNames) {
            try {
                JarFile jarFile = new JarFile(pluginPath + '\\' + pluginName);
                Enumeration<JarEntry> e = jarFile.entries();
                URL[] urls = {new URL("jar:file:" + pluginPath + '\\' + pluginName + "!/")};
                URLClassLoader cl = URLClassLoader.newInstance(urls);

                while (e.hasMoreElements()) {
                    JarEntry je = e.nextElement();
                    if (je.isDirectory() || !je.getName().endsWith(".class")) {
                        continue;
                    }
                    // -6 because of .class
                    String className = je.getName().substring(0, je.getName().length() - 6);
                    className = className.replace('/', '.');
                    Class c = cl.loadClass(className);
                    if ("Plugin".equals(className)) {
                        continue;
                    }

                    Object object = c.newInstance();
                    Method getCommandNameMethod = c.getDeclaredMethod("getCommandName");
                    String commandNameFromClass = (String) getCommandNameMethod.invoke(object, null);
                    if (COMMAND_COMMANDS.equals(commandName)) {
                        commands.append(String.format(" - %s\n", commandNameFromClass));
                        continue;
                    }

                    Method executeMethod = c.getDeclaredMethod("execute", new Class[]{String.class});
                    if (commandNameFromClass.equals(commandName)) {
                        new Thread(() -> {
                            try {
                                clientsManager.sendMsgToClient(writer, String.valueOf(executeMethod.invoke(object, args)));
                            } catch (Exception e1) {
                                logger.error(e1);
                            }
                        }).start();
                        return;
                    }
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        new Thread(() -> {
            try {
                if (COMMAND_COMMANDS.equals(commandName)) {
                    clientsManager.sendMsgToClient(writer, commands.toString());
                } else {
                    clientsManager.sendMsgToClient(writer, INVALID_COMMAND_MESSAGE);
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }).start();
    }

    private void init() {
        File dir = new File(pluginPath);
        pluginNames = dir.list();
    }
}