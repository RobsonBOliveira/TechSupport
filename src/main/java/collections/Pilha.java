package collections;

import org.hibernate.internal.util.collections.Stack;


import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class Pilha<T> implements Stack<T> {
    private int sizeMax;
    private Object[] array;
    private int top;

    public Pilha(int size){
        this.top = -1;
        this.sizeMax = size;
        this.array = new Object[size];
    }
    @Override
    public void push(T valor) {
        if (isFull()){
            System.out.println("Pilha Cheia");
        }
        top++;
        array[top] = valor;
    }
    @Override
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()){
            System.out.println("Pilha vázia");
        }

        T valor = (T)array[top];
        top--;
        return valor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getCurrent() {
        if (isEmpty()){
            System.out.println("Pilha Vazia");
        }
        return (T)array[top];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T peek(int i) {
        if (isEmpty()){
            System.out.println("Pilha Vazia");
            return null;
        }
        return (T)array[i];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getRoot() {
        if(isEmpty()){
            System.out.println("Pilha Vazia");
            return null;
        }
        return (T)array[0];
    }

    @Override
    public int depth() {
        if(top == -1){
            return 0;
        }
        else{
            return top + 1;
        }
    }


    public boolean isFull(){
        return top == sizeMax - 1;
    }

    @Override
    public boolean isEmpty(){
        return top == -1;
    }

    @Override
    public void clear() {
        this.top = -1;
        this.array = new Object[this.sizeMax];
    }

    @Override
    public void visitRootFirst(Consumer<T> consumer) { //Desnecessário

    }

    @Override
    public <X> X findCurrentFirst(Function<T, X> function) { //Desnecessário
        return null;
    }

    @Override
    public <X, Y> X findCurrentFirstWithParameter(Y y, BiFunction<T, Y, X> biFunction) { //Desnecessário
        return null;
    }
}