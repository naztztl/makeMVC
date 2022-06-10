package makeMVC.service;

import makeMVC.models.ModelFactory;
import makeMVC.models.Session;

import java.util.ArrayList;


public class SessionService {
    public static Session add(Integer userId, String sessionId) {

        Session m = new Session();
        m.sessionId = sessionId;
        m.userId = userId;

        ArrayList<Session> all = load();
        all.add(m);
        save(all);

        return m;
    }
    
    public static void save(ArrayList<Session> list) {
        String className = Session.class.getSimpleName();
        ModelFactory.save(className, list, (model) -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.sessionId);
            lines.add(model.userId.toString());
            return lines;
        } );

    }

    public static ArrayList<Session> load() {
        String className = Session.class.getSimpleName();
        ArrayList<Session> all = ModelFactory.load(className, 2, (modelData) -> {
            String sessionId = modelData.get(0);
            Integer userId = Integer.valueOf(modelData.get(1));

            Session m = new Session();
            m.sessionId = sessionId;
            m.userId = userId;

            return m;
        });
        return all;
    }
    
    public static Session findBySessionId(String sessionId) {
        ArrayList<Session> all = load();
        for (Session m:all) {
            if (m.sessionId.equals(sessionId)) {
                return m;
            }
        }

        return null;
    }

}
