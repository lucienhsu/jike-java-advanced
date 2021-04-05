import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoadClassFromEncryptClassFile extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        LoadClassFromEncryptClassFile lf = new LoadClassFromEncryptClassFile();
        Class<?> helloClazz = lf.findClass("Hello");
        Method hello = helloClazz.getMethod("hello");
        hello.invoke(helloClazz.getDeclaredConstructor().newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 1.load xclass file
        File file = new File("src/" + name + ".xlass");

        try {
            FileInputStream fi = new FileInputStream(file);
            byte[] bs = fi.readAllBytes();
            // 2.decrypt
            for (int i = 0; i < bs.length; i++) {
                bs[i] = (byte) (255 - bs[i]);
            }
            // 3.return Class
            return defineClass(name, bs, 0, bs.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }
}
