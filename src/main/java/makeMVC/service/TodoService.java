package makeMVC.service;

import makeMVC.models.ModelFactory;
import makeMVC.models.Todo;

import java.util.ArrayList;
import java.util.HashMap;


public class TodoService {
    public static Todo add(HashMap<String, String> form) {
        String content = form.get("content");
        Todo m = new Todo();
        m.content = content;
        m.completed = false;

        ArrayList<Todo> all = load();
        if (all.size() == 0) {
            m.id = 1;
        } else {
            m.id = all.get(all.size() - 1).id + 1;
        }
        
        all.add(m);
        save(all);

        return m;
    }
    

    public static void save(ArrayList<Todo> list) {
        String className = Todo.class.getSimpleName();
        ModelFactory.save(className, list, (model) -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.id.toString());
            lines.add(model.content);
            lines.add(model.completed.toString());
            return lines;
        } );

    }


    public static ArrayList<Todo> load() {
        String className = Todo.class.getSimpleName();
        ArrayList<Todo> all = ModelFactory.load(className, 3, (modelData) -> {
            Integer id = Integer.valueOf(modelData.get(0));
            String content = modelData.get(1);
            Boolean completed = Boolean.valueOf(modelData.get(2)) ;

            Todo m = new Todo();
            m.id = id;
            m.content = content;
            m.completed = completed;

            return m;
        });
        return all;
    }
    
    public static void delete(Integer todoId) {
        ArrayList<Todo> all = load();

        for (int i = 0; i < all.size(); i++) {
            Todo m = all.get(i);
            if (m.id.equals(todoId)) {
                all.remove(m);
            }
        }
        
        save(all);

    }
    
    
    public static Todo findById(Integer todoId) {
        ArrayList<Todo> all = load();

        for (int i = 0; i < all.size(); i++) {
            Todo m = all.get(i);
            if (m.id.equals(todoId)) {
                return m;
            }
        }
        
        return null;
    }

    public static Todo findById2(Integer todoId) {
        ArrayList<Todo> all = load();
        
       Todo r =  ModelFactory.findBy(all, (target) -> {
           if (target.id.equals(todoId)) {
               return true;
           } else {
               return false;
           }
       });
        return r;
    }
    
    public static void updateContent(Integer todoId, String content) {
        ArrayList<Todo> all = load();

        for (int i = 0; i < all.size(); i++) {
            Todo m = all.get(i);
            if (m.id.equals(todoId)) { 
                m.content = content;
            }
        }
        
        save(all);
    }
    
    public static String todoListHtml() {
        ArrayList<Todo> all = load();
        StringBuilder allHtml = new StringBuilder();

        for (Todo m:all) {
            String s = String.format(
                    " <h3>\n" +
                    "   %s: %s\n" +
                    "   <a href=\"/todo/delete?id=%s\">删除</a>\n" +
                    "   <a href=\"/todo/edit?id=%s\">编辑</a>\n" +
                    " </h3>",
                    m.id,
                    m.content,
                    m.id,
                    m.id
            );
            allHtml.append(s);
        }
        
        return allHtml.toString();
    }
    
}
