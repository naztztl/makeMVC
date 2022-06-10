package makeMVC.service;

import makeMVC.models.ModelFactory;
import makeMVC.models.User;
import makeMVC.models.UserRole;

import java.util.ArrayList;
import java.util.HashMap;


public class UserService {
    public static User add(HashMap<String, String> form) {
        String username = form.get("username");
        String password = form.get("password");
        User m = new User();
        m.username = username;
        m.password = password;
        m.role = UserRole.normal;

        ArrayList<User> all = load();
        if (all.size() == 0) {
            m.id = 1;
        } else {
            m.id = all.get(all.size() - 1).id + 1;
        }
        
        all.add(m);
        save(all);

        return m;
    }
    

    public static void save(ArrayList<User> list) {
        String className = User.class.getSimpleName();
        ModelFactory.save(className, list, (model) -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.id.toString());
            lines.add(model.username);
            lines.add(model.password);
            lines.add(model.role.toString());
            return lines;
        } );

    }


    public static ArrayList<User> load() {
        String className = User.class.getSimpleName();
        ArrayList<User> all = ModelFactory.load(className, 4, (modelData) -> {
            Integer id = Integer.valueOf(modelData.get(0));
            String username = modelData.get(1);
            String password = modelData.get(2);;
            UserRole role = UserRole.valueOf(modelData.get(3));

            User m = new User();
            m.id = id;
            m.username = username;
            m.password = password;
            m.role = role;

            return m;
        });
        return all;
    }
    
    public static boolean validLogin(HashMap<String, String> form) {
        String username =  form.get("username");
        String password = form.get("password");    
        
        ArrayList<User> ms = load();

        for (User user:ms) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }



    public static User findByUsername(String username) {
        ArrayList<User> all = load();
        for (User u:all) {
            if (u.username.equals(username)) {
                return u;
            }
        }

        return null;
    }


    public static User findById(Integer userId) {
        ArrayList<User> all = load();
        for (User u:all) {
            if (u.id.equals(userId)) {
                return u;
            }
        }

        return null;
    }
    public static User guest() {
        User u = new User();
        u.id = -1;
        u.username = "游客";
        u.password = "";
        u.role = UserRole.guest;
        return u;
    }
    
}
