package ru.track.list;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List {

    private Node endptr; // pointer to the last element
    private Node begptr; // pointer to the first element

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    @Override
    void add(int item) {
        if (size == 0) {
            Node node = new Node(null, null, item);
            begptr = node;
            endptr = node;
        }
        else {
            Node node = new Node(endptr, null, item);
            endptr.next = node;
            endptr = node;
        }
        size++;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >=size)
            throw new NoSuchElementException();

        Node tmp = begptr;
        for (int i=0; i < idx; i++)
            tmp = tmp.next;

        if (tmp.prev != null)
            tmp.prev.next = tmp.next;
        if (tmp.next != null)
            tmp.next.prev = tmp.prev;
        int ret = tmp.val;
        if (idx == 0)
            begptr = tmp.next;
        if (idx == size-1)
            endptr = tmp.prev;
        tmp = null;
        size--;

        return ret;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx < 0 || idx >=size)
            throw new NoSuchElementException();

        Node tmp = begptr;
        for (int i=0; i < idx; i++)
            tmp = tmp.next;

        return tmp.val;
    }

    @Override
    int size() {
        return size;
    }
}
