package makeMVC.models;

public class Todo {
    public Integer id;
    public String content;
    public Boolean completed;

    public Todo() {

    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", completed=" + completed +
                '}';
    }
}
