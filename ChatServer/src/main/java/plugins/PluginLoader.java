//package plugins;
//
//import java.io.*;
//import java.nio.file.Paths;
//import java.util.jar.JarFile;
//
//public class PluginLoader extends ClassLoader {
//    //Путь до директории с модулями.
//    private String pathToBin;
//
//    public PluginLoader(String pathtobin, ClassLoader parent) {
//        super(parent);
//        this.pathToBin = pathtobin;
//    }
//
//    @Override
//    public Class<?> findClass(String className) throws ClassNotFoundException {
//        try {
//            JarFile jarFile = new JarFile(new File(pathToBin + '\\' + className + ".jar"));jarFile.getEntry().
//            return jarFile.getEntry(className + ".class");
//        } catch (FileNotFoundException ex) {
//            return super.findClass(className);
//        } catch (IOException ex) {
//            return super.findClass(className);
//        }
//    }
//
//    private byte[] fetchClassFromFS(String path) throws IOException {
//        InputStream is = new FileInputStream(new File(path));
//        long length = new File(path).length();
//        if (length > Integer.MAX_VALUE) {
//            // File is too large
//        }
//        byte[] bytes = new byte[(int) length];
//        int offset = 0;
//        int numRead = 0;
//        while (offset < bytes.length
//                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
//            offset += numRead;
//        }
//        if (offset < bytes.length) {
//            throw new IOException("Could not completely read file " + path);
//        }
//        is.close();
//        return bytes;
//    }
//}