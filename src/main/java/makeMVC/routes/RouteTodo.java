package makeMVC.routes;

import makeMVC.MakeTemplate;
import makeMVC.Request;
import makeMVC.Utility;
import makeMVC.models.Todo;
import makeMVC.models.User;
import makeMVC.models.UserRole;
import makeMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Function;

public class RouteTodo {

    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/todo", RouteTodo::index);
        map.put("/todojs", RouteTodo::indexWithJS);
        map.put("/todo/add", RouteTodo::add);
        map.put("/todo/delete", RouteTodo::delete);
        map.put("/todo/edit", RouteTodo::edit);
        map.put("/todo/update", RouteTodo::update);

        return map;
    }
    public static byte[] index(Request request) {
        User current = Route.currentUser(request);
        // 登录验证, 如果是游客身份, 就重定向到登录页面
        if (current.role.equals(UserRole.guest)) {
            return redirect("/login");
        }

        String body = Route.html("todo/index.html");

        body = body.replace("{todos}", TodoService.todoListHtml());

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");
        String response = Route.responseWithHeader(200, header, body);

        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] indexWithJS(Request request) {
        HashMap<String, Object> d = new HashMap<>();
        String body = MakeTemplate.render(d, "todo.ftl");

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");
        String response = Route.responseWithHeader(200, header, body);

        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] redirect(String url) {
        String header = String.format(
                "%s\r\n%s\r\n\r\n",
                "HTTP/1.1 302 move",
                "Location: " + url
        );
        return header.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] add(Request request) {
        HashMap<String, String> form = request.form;
        Utility.log("add form %s", form);
        TodoService.add(form);

        return redirect("/todo");
    }


    public static byte[] delete(Request request) {
        HashMap<String, String> query = request.query;
        Integer todoId = Integer.valueOf(query.get("id"));
        TodoService.delete(todoId);

        return redirect("/todo");
    }

    public static byte[] edit(Request request) {
        Utility.log("query %s", request.query);
        HashMap<String, String> query = request.query;
        Integer todoId = Integer.valueOf(query.get("id"));
        Todo todo = TodoService.findById(todoId);

        String body = Route.html("todo/edit.html");

        body = body.replace("{todo_id}", todo.id.toString());
        body = body.replace("{todo_content}", todo.content);

        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");
        String response = Route.responseWithHeader(200, header, body);

        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] update(Request request) {
        HashMap<String, String> form = request.form;
        Integer todoId = Integer.valueOf(form.get("id"));
        String todoContent = form.get("content");

        TodoService.updateContent(todoId, todoContent);

        return redirect("/todo");
    }
}
