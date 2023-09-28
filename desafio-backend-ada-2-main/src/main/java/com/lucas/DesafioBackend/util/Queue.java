package com.lucas.DesafioBackend.util;

public interface Queue<T> {

    void enqueue(T item);

    T dequeue();

    boolean isFull();

    boolean isEmpty();
}
