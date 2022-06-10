package makeMVC;

import makeMVC.routes.Route;
import makeMVC.routes.RouteAjaxTodo;
import makeMVC.routes.RouteTodo;
import makeMVC.routes.RouteUser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

class MakeServlet implements Runnable {
    Socket connection;
    
    public MakeServlet(Socket connection) {
        this.connection = connection;
    }

    private static byte[] responseForPath(Request request) {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.putAll(Route.routeMap());
        map.putAll(RouteUser.routeMap());
        map.putAll(RouteTodo.routeMap());
        map.putAll(RouteAjaxTodo.routeMap());


        Function<Request, byte[]> function = map.getOrDefault(request.path, Route::route404);
        byte[] response =  function.apply(request);
        return response;
    }
    
    @Override
    public void run() {
        try  {
            Socket socket = this.connection;
            // 读取客户端请求数据
            String request = SocketOperator.socketReadAll(socket);
            byte[] response;
            if (request.length() > 0) {
                // 输出响应的数据
                // 解析 request 得到 path
                Request r = new Request(request);

                // 根据 path 来判断要返回什么数据
                response = responseForPath(r);
            } else {
                response = new byte[1];
            }
            SocketOperator.socketSendAll(socket, response);
        } catch (IOException ex) {
            System.out.println("exception: " + ex.getMessage());
        } finally {
            try {
                this.connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Server {
    static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        int port = 9000;
        runWithThread(port);
    }

    private static void runWithThread(Integer port) {
        Utility.log("服务器启动, 访问 http://localhost:%s", port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try  {
                    Socket socket = serverSocket.accept();

                    MakeServlet servlet = new MakeServlet(socket);
                    Thread r = new Thread(servlet);
                    pool.execute(r);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.out.println("exception: " + ex.getMessage());
        }
    }

}
