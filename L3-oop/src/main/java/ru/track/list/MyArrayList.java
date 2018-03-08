package ru.track.list;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {

    private int[] intArray;
    private int elemsAlloced;

    public MyArrayList() {
        elemsAlloced = 10;
        size = 0;
        intArray = new int[size];
    }

    public MyArrayList(int capacity) {
        elemsAlloced = capacity;
        size = 0;
        intArray = new int[size];
    }

    @Override
    void add(int item) {
        if (size == elemsAlloced)
        {
            int[] temp = new int[10+size];
            System.arraycopy(intArray, 0, temp, 0, size);
            intArray = new int[size+10];
            size += 10;
            System.arraycopy(temp, 0, intArray, 0, size);
            intArray[size] = item;
            size++;
        }
        else {
            intArray[size] = item;
            size++;
        }
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx >= size || idx < 0)
            throw new NoSuchElementException();

        int ret = intArray[idx];

        for (int i=idx; i < size-1; i++) {
            intArray[i] = intArray[i+1];
        }
        size--;

        return ret;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx >= elemsAlloced || idx < 0)
            throw new NoSuchElementException();

        return intArray[idx];
    }

    @Override
    int size() {
        return size;
    }
}
