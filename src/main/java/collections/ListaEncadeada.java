package collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListaEncadeada<E> implements List<E> {

    class node { //Classe ajudante
        private E data;
        private node next;
        private node prev;

        public node(E data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

        //Váriaveis.
    private node head;
    private node tail;
    private int size;

    public void listaEncadeada(){ //Construtor da classe
        head = null;
        tail = null;
        size = 0;
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

    public node searchNodeIndex(int index){
        node p = head;
        int temp = 0;
        while (p != null){
            if(temp == index)
            {
                return p;
            }
            temp++;
            p = p.next;
        }
        return null;
    }

    public E search (E criterio){//
        node p = searchNode(criterio);

        if(p == null) {
            return null;
        }
        else {
            return p.data;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if(head == null || tail == null){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean contains(Object o) { //Retorna true se elemento existe na lista.
        node p = head;

        while (p != null){
            if(p.data.equals(o)){
                return true;
            }
            p = p.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() { //Desnecessário qualquer coisa adiciono depois
        return new Iterator() {
            private node current = head; // Começa pelo head

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new IllegalStateException("Sem mais elementos.");
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public Object[] toArray() {//Desnecessário qualquer coisa adiciono depois
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {//Desnecessário qualquer coisa adiciono depois
        return null;
    }

    @Override
    public boolean add(E e) { //Pela definição no List, addLast.
        node newNode = new node(e);
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
        return true;
    }

    @Override
    public boolean remove(Object o) { //Remove um elemento de acordo com o criterio, mas não retorna ele
        E valor = null;

        if(head == null){
            System.out.println("Lista vazia!");
            return false;
        }

        node previous = null;

        node removed = searchNode((E) o);

        if(removed != null){
            previous = removed.prev;
        }

        if(previous == null){
            if(!head.data.equals(o)){
                System.out.println("Critério inexistente");
                return false;
            }
            else{
                removeFirst();
                return true;
            }
        }
        else{
            if(removed == tail){
                removeLast();
                return true;
            }
            else{
                node front = removed.next;

                previous.next = front;
                front.prev = previous;

                removed.next = null;
                removed.prev = null;

                size--;
                return true;
            }
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) { //Desnecessário qualquer coisa adiciono depois
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }

        for (E element : c) {
            this.add(element);
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) { throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) { //Desnecessário qualquer coisa adiciono depois
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {//Desnecessário qualquer coisa adiciono depois
        return false;
    }

    @Override
    public void clear() { //Limpa a lista inteira
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public E get(int index) { //Retorna o data de um node no index X
        node temp = searchNodeIndex(index);
        return temp.data;
    }

    @Override
    public E set(int index, E element) { //Insere elemento no lugar de um elemento de número X, retorna elemento apagado
        node p = searchNodeIndex(index);
        if(p == null){
            System.out.println("Critério inválido");
            return null;
        }
        else{
            E dado = p.data;
            p.data = element;
            return dado;
        }
    }

    @Override
    public void add(int index, E element) { //Adiciona após x elementos.
        if(index == 0){
            addFirst(element);
        }
        else {
            node p = searchNodeIndex(index);

            if (p == null) {
                System.out.println("Criterio inválido");
            } else {
                node New = new node(element);

                if (p.next == null) {
                    p.next = New;
                }

                New.next = null;
                New.prev = p;
                p.next = New;

                node front = New.next;

                size++;
            }
        }
    }

    @Override
    public E remove(int index) {//Remove de acordo com o index, mas retorna um valor.
        E valor = null;

        if(head == null){
            System.out.println("Lista vazia!");
            return null;
        }

        node previous = null;

        node removed = searchNodeIndex(index);

        if(removed != null){
            previous = removed.prev;
        }

        if(previous == null){
            if(searchNodeIndex(index) == null){
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

    @Override
    public int indexOf(Object o) { //Desnecessário qualquer coisa adiciono depois
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) { //Desnecessário qualquer coisa adiciono depois
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() { //Desnecessário qualquer coisa adiciono depois
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) { //Desnecessário qualquer coisa adiciono depois
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) { //Desnecessário qualquer coisa adiciono depois
        return List.of();
    }



        public void show(){
            node p = head;

            if( p == null){
                System.out.println("Lista vazia!");
            }
            while(p != null){
                System.out.println( "Dado: " + p.data);
                p = p.next;
            }
        }


        @Override
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

        @Override
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

        @Override
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

        @Override
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


        public E removeAt(E criterio){//Remove de acordo com critério, e retorna um valor.
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

        public boolean addAt(E dado, E criterio){//Adiciona após certo elemento
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
    }
