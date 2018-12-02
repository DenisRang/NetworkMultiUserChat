package plugins;

import server.ClientsManager;

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
    private static String pluginPath;
    // private static PluginLoader loader;
    private static String[] pluginNames;

    public PluginEngine(String pluginPath) {
        this.pluginPath = pluginPath;
        init();
    }

    public static void executeForClient(ClientsManager clientsManager, BufferedWriter writer, String commandName, String args) {
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
                    Method getCommandNameMethod = c.getDeclaredMethod("getCommandName");
                    Method executeMethod = c.getDeclaredMethod("execute", new Class[]{String.class});
                    if ("Plugin".equals(className)) {
                        continue;
                    }
                    Object object = c.newInstance();
                    String commandNameTemp = (String) getCommandNameMethod.invoke(object, null);
                    if (commandNameTemp.equals(commandName)) {
                        new Thread(() -> {
                            try {
                                clientsManager.sendMsgToClient(writer, String.valueOf(executeMethod.invoke(object, args)));
                            } catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            } catch (InvocationTargetException e1) {
                                e1.printStackTrace();
                            }
                        }).start();
                        return;
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        //  loader = new PluginLoader(pluginPath, ClassLoader.getSystemClassLoader());
        //Получаем список доступных модулей.
        File dir = new File(pluginPath);
        pluginNames = dir.list();
    }
}