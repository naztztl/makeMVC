package makeMVC.routes;

import makeMVC.Request;
import makeMVC.Utility;
import makeMVC.models.User;
import makeMVC.service.SessionService;
import makeMVC.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

public class RouteUser {

    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/login", RouteUser::login);
        map.put("/register", RouteUser::register);

        return map;
    }

    public static byte[] login(Request request) {
        HashMap<String, String> header = new HashMap<>();


        HashMap<String, String> data = null;
        if (request.method.equals("POST")) {
            data = request.form;
        } else if (request.method.equals("GET")) {
            data = request.query;
        } else {
            String m = String.format("unknown method (%s)", request.method);
            throw new RuntimeException(m);
        }

        String loginResult = "";
        if (data != null) {
            if (UserService.validLogin(data)) {
                User user = UserService.findByUsername(data.get("username"));
                String sessionId = UUID.randomUUID().toString();
                SessionService.add(user.id, sessionId);
                header.put("Set-Cookie", "session_id=" + sessionId);
                loginResult = "登录成功";

            } else {
                loginResult = "登录失败";
            }
        }

        String body = Route.html("login.html");
        body = body.replace("{loginResult}", loginResult);

        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] register(Request request) {
        String registerResult = "";
        if (request.method.equals("POST")) {
            HashMap<String, String> data = request.form;
            Utility.log("注册 data %s", data);
            UserService.add(data);
            registerResult = "注册成功";
        }

        String body = Route.html("register.html");
        body = body.replace("{registerResult}", registerResult);


        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");
        String response = Route.responseWithHeader(200, header, body);

        return response.getBytes(StandardCharsets.UTF_8);
    }

}
