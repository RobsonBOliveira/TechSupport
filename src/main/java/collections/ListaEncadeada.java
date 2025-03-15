package collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListaEncadeada<E> implements List<E> {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
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
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
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
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
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
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return List.of();
    }

    class node{
        private E data;
        private node next;
        private node prev;

        public node (E data){
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        private node head;
        private node tail;
        private int size;

        public void listaEncadeada(){
            head = null;
            tail = null;
            size = 0;
        }

        public void show(){
            node p = head;

            if( p == null){
                System.out.println("Lista vazia!");
            }
            while(p != null){
                System.out.println(p.data);
                p = p.next;
            }
        }

        public node searchNode(E criterio){
            node p = head;

            while (p != null){
                if(p.data.equals(criterio)){
                    return p;
                }
                p = p.next;
            }
            return null;
        }

        public E search (E criterio){
            node p = searchNode(criterio);

            if(p == null) {
                return null;
            }
            else {
                return p.data;
            }
        }

        public void addFirst(E data){
            node newNode = new node(data);

            if(head == null){
                head = newNode;
                tail = newNode;
            }
            else{
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            size++;
        }

        public void addLast(E data){
            node newNode = new node(data);
            if(head == null){
                head = newNode;
                tail = newNode;
            }
            else{
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            size++;
        }

        public E removeFirst(){
            node p = head;
            E valor = null;

            if(head == null){
                System.out.println("Lista vazia!");
                return null;
            }
            else{
                valor = p.data;
                if(head == tail){
                    System.out.println("Último elemento será removido.");
                    head = tail = null;
                }
                else {
                    head = head.next;
                    head.prev = null;
                }
                p.next = null;
                size--;
            }
            return valor;
        }

        public E removeLast(){
            E valor = null;

            if(head == null){
                System.out.println("Lista vazia!");
                return null;
            }
            else{
                valor = tail.data;
                if(head == tail){
                    System.out.println("Último elemento será removido. ");
                    tail = head = null;
                }
                else{
                    node p = tail.prev;
                    tail.prev = null;
                    tail = p;
                    tail.next = null;
                }
                size--;
            }
            return valor;
        }

        public E removeAt(E criterio){
            E valor = null;

            if(head == null){
                System.out.println("Lista vazia!");
                return null;
            }

            node previous = null;

            node removed = searchNode(criterio);

            if(removed != null){
                previous = removed.prev;
            }

            if(previous == null){
                if(!head.data.equals(criterio)){
                    System.out.println("Critério inexistente");
                    return null;
                }
                else{
                    return removeFirst();
                }
            }
            else{
                if(removed == tail){
                    return removeLast();
                }
                else{
                    node front = removed.next;

                    previous.next = front;
                    front.prev = previous;

                    removed.next = null;
                    removed.prev = null;

                    size--;

                    valor = removed.data;
                    return valor;
                }
            }
        }

        public boolean addAt(E dado, E criterio){
            node p = searchNode(criterio);

            if(p == null){
                System.out.println("Criterio inválido");
                return false;
            }
            else{
                node New = new node(dado);

                if(p.next == null){
                    p.next = New;
                }

                New.next = p.next;
                New.prev = p;
                p.next = New;

                node front = New.next;
                if(front == null){
                    front.prev = New;
                }

                size++;
                return true;
            }
        }

        public E peekFirst(){
            if(head == null){
                System.out.println("Lista vazia!");
                return null;
            }
            else{
                return head.data;
            }
        }

        public E peekLast(){
            if(head == null){
                System.out.println("Lista vazia!");
                return null;
            }
            else{
                return tail.data;
            }
        }

        public int getSize(){
            return size;
        }
    }
}
