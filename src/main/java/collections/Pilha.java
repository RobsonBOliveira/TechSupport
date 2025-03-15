package collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Pilha<T> implements List<T> {
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

    @Override
    public int size() {
        return 0;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return List.of();
    }


}
