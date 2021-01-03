import sun.misc.Launcher;
import sun.misc.URLClassPath;

import java.net.URL;
import java.util.HashMap;

public class ClassLoaderTest {
    public static void main(String[] args) {
        System.out.println("**********启动类加载器*********的地址");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL element:urLs) {
            System.out.println(element);
        }
//
        System.out.println("**********扩展类加载器************");
        String property = System.getProperty("java.ext.dirs");
        for (String path: property.split(";")){
            System.out.println(path);
        }

    }
}
