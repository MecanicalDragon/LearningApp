package oldLessons.stack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericStack<E> implements Stack<E> {

    private List<E> list;
    private int size;
    private int maxSize;

    public GenericStack(int size){
        this.maxSize = size;
        list = new ArrayList<>();
    }

    public GenericStack(){
        this(15);
    }

    @Override
    public void push(E e) throws StackException {
        if (!this.isFull()){
            list.add(e);
            size++;
        }
        else throw new StackException(String.format("StackOverFlow! Max size = %s", maxSize));
    }

    @Override
    public E pop() throws StackException {
        if (size!=0){
            size--;
            E e = list.get(size);
            list.remove(size);
            return e;
        }
        else throw new StackException("Stack is Empty");
    }

    @Override
    public E peek() throws StackException {
        if (size!=0){
            return list.get(size-1);
        }
        else throw new StackException("Stack is Empty");
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == maxSize;
    }

    @Override
    public void pushAll(Collection<? extends E> src) throws StackException {
        for (E e : src) {
            push(e);
        }
    }

    @Override
    public void popAll(Collection dst) throws StackException {
        for (E e : list){
            dst.add(pop());
        }

    }
}
