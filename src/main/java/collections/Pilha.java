package collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Pilha<T> implements List<T> {
    private int sizeMax;
    private Object[] array;
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
    public int size() { //Retorna a quantidade de itens dentro da pilha
        if(top == -1){
            return 0;
        }
        else{
            return top + 1;
        }
    }

    @Override
    public boolean isEmpty(){
        return top == -1;
    }

    @Override
    public boolean contains(Object o) { //Retorna true se X elemento existe na pilha.
        for(int i = 0; i < top; i++){
            if(array[i].equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {//Desnecessário, qualquer coisa adiciono depois
        return null;
    }

    @Override
    public Object[] toArray() {//Desnecessário, qualquer coisa adiciono depois
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {//Desnecessário, qualquer coisa adiciono depois
        return null;
    }

    @Override
    public boolean add(T t) { //Retorna true ao adicionar um elemento na pilha (push)
        try {
            this.push(t);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean remove(Object o) { //Não faz sentido de acordo com definnição de pilha
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {//Desnecessário, qualquer coisa adiciono depois
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {//Desnecessário, qualquer coisa adiciono depois
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {//Desnecessário, qualquer coisa adiciono depois
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {//Desnecessário, qualquer coisa adiciono depois
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {//Desnecessário, qualquer coisa adiciono depois
        return false;
    }

    @Override
    public void clear() { //Limpa a pilha.
        this.top = -1;
        this.array = new Object[sizeMax];
    }

    @Override
    public T get(int index) {//Não faz sentido de acordo com definnição de pilha
        return null;
    }

    @Override
    public T set(int index, T element) {//Não faz sentido de acordo com definnição de pilha
        return null;
    }

    @Override
    public void add(int index, T element) {//Não faz sentido de acordo com definnição de pilha

    }

    @Override
    public T remove(int index) { //Não faz sentido de acordo com definnição de pilha
        return null;
    }

    @Override
    public int indexOf(Object o) {//Desnecessário qualquer coisa adiciono depois
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {//Desnecessário qualquer coisa adiciono depois
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {//Desnecessário qualquer coisa adiciono depois
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {//Desnecessário qualquer coisa adiciono depois
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) { //Desnecessário qualquer coisa adiciono depois
        return List.of();
    }


}
