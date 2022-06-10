package makeMVC;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Utility {
    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }


    public static InputStream fileStream(String path) throws FileNotFoundException {
        String resource = String.format("%s.class", Utility.class.getSimpleName());
        Utility.log("resource %s", resource);
        Utility.log("resource path %s", Utility.class.getResource(""));
        URL res = Utility.class.getResource(resource);
        if (res != null && res.toString().startsWith("jar:")) {

            path = String.format("/%s", path);
            InputStream is = Utility.class.getResourceAsStream(path);
            if (is == null) {
                throw new FileNotFoundException(String.format("在 jar 里面找不到 %s", path));
            } else {
                return is;
            }
        } else {
            path = String.format("src/main/resources/%s", path);
            return new FileInputStream(path);
        }
    }

    public static void save(String path, String data) {
        try (FileOutputStream out = new FileOutputStream(path)) {
            out.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            String s = String.format("Save file <%s>, error: <%s>", path, e);
            throw new RuntimeException(s);
        }
    }

    public static String load(String path) {
        try (FileInputStream is = new FileInputStream(path)) {
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return content;
        } catch (IOException e) {
            String s = String.format("load file <%s>, error: <%s>", path, e);
            throw new RuntimeException(s);
        }
    }

    public static void main(String[] args) {
        String filename = "a.txt";
        String content = "1111";
        save(filename, content);
        String r = load(filename);
        log("a.txt: <%s>", r);
    }
}
