package collections;

import java.util.List;

public abstract class Pilha<T> implements List<T> {
    private final int sizeMax;
    private final Object[] array;
    private int top;

    public Pilha(int size){
        this.top = -1;
        this.sizeMax = size;
        this.array = new Object[size];
    }

    public void push(T valor) throws Exception {
        if (isFull()){
            throw new Exception("Pilha Cheia");
        }
        top++;
        array[top] = valor;
    }

    @SuppressWarnings("unchecked")
    public T pop() throws Exception {
        if (isEmpty()){
            throw new Exception("Pilha vázia");
        }

        T valor = (T)array[top];
        top--;
        return valor;
    }

    @SuppressWarnings("unchecked")
    public T peek() throws Exception {
        if (isEmpty()){
            throw new Exception("Pilha Vazia");
        }
        return (T)array[top];
    }

    public boolean isFull(){
        return top == sizeMax - 1;
    }
    public boolean isEmpty(){
        return top == -1;
    }


}
