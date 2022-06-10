package makeMVC.models;

public class Session {
    public String sessionId;
    public Integer userId;

    public Session() {

    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId='" + sessionId + '\'' +
                ", userId=" + userId +
                '}';
    }
}
