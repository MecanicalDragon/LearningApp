package oldLessons.stack;

import java.util.ArrayList;

public class Stack5<T>{
    // If in "Project Structure" connected simple library - logging is on.
    // If in "Project Structure" connected nop library - logging is off.
    // api library must be connected anyway.
    private ArrayList<T> list;

    public Stack5(){
        list = new ArrayList<>();
    }

    public void put(T t){
        list.add(t);
        System.out.println("List size now is " + list.size());
    }

    public T take(){
        T t = list.get(list.size()-1);
        list.remove(list.size()-1);
        return t;
    }

    public void lay (T t){
        list.add(0, t);
    }

    public T pull(){
        T t = list.get(0);
        list.remove(0);
        return t;
    }

    public int size(){
        return list.size();
    }
}
