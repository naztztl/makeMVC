package makeMVC.routes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import makeMVC.Request;
import makeMVC.Utility;
import makeMVC.models.Todo;
import makeMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RouteAjaxTodo {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/ajax/todo", RouteAjaxTodo::index);
        map.put("/ajax/todo/add", RouteAjaxTodo::add);
        map.put("/ajax/todo/all", RouteAjaxTodo::all);

        return map;
    }


    public static byte[] index(Request request) {
        String body = Route.html("ajax_todo.ftl");

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");
        String response = Route.responseWithHeader(200, header, body);

        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] add(Request request) {
        String jsonString = request.body;
        Utility.log("json: %s", jsonString);
        JSONObject jsonForm = JSON.parseObject(jsonString);
        String content = jsonForm.getString("content");
        Utility.log("content: %s", content);

        HashMap<String, String> form = new HashMap<>();
        form.put("content", content);
        Todo todo = TodoService.add(form);


        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/json");
        String body = JSON.toJSONString(todo);
        String response = Route.responseWithHeader(200, header, body);

        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] all(Request request) {
        ArrayList<Todo> arrayList = TodoService.load();


        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/json");
        String body = JSON.toJSONString(arrayList);
        String response = Route.responseWithHeader(200, header, body);

        return response.getBytes(StandardCharsets.UTF_8);
    }
}
