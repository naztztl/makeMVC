package makeMVC.models;

import java.util.ArrayList;

public interface Serializer<T> {
    // 把一个自定义类型对象的所有属性, 都放入一个 String 类型的 ArrayList, 然后返回
    ArrayList<String> serialize(T model);
}
