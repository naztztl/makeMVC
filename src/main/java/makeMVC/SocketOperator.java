package makeMVC;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketOperator {
    private static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static String socketReadAll(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        InputStreamReader reader = new InputStreamReader(input);
        int bufferSize = 1024;
        // 初始化指定长度的数组
        char[] data = new char[bufferSize];
        StringBuilder sb = new StringBuilder();
        while (true) {
            // size 是读取到的字节数
            int size = reader.read(data, 0, data.length);

            // 判断是否读到数据
            if (size > 0) {
                sb.append(data, 0, size);
            }
            // 把字符数组的数据追加到 sb 中
//            log("size and data: " + size + " || " + data.length);
            if (!reader.ready()) {
                break;
            }
        }
        return sb.toString();
    }

    public static void socketSendAll(Socket socket, byte[] r) throws IOException {
        OutputStream output = socket.getOutputStream();
        output.write(r);
    }
}
