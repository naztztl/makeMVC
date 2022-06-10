package makeMVC.models;

import java.util.List;

public interface Deserializer <T> {
    // 传入一个 ArrayList, 里面存了一个对象的所有属性, 返回一个新建的对象
    T deserialize(List<String> modelData);
}
