package com.lucas.DesafioBackend.util;

public class MyQueue<T> implements Queue<T> {

    private final T[] queue;
    private int front = -1;
    private int rear = -1;
    private final int capacity;
    private int count;

    public MyQueue(int size)
    {
        queue = (T[]) new Object[size];
        capacity = size;
        count = 0;
    }

    public T dequeue()
    {
        if (isEmpty())
        {
            return null;
        }

        front = (front + 1) % capacity;
        count--;

        return queue[front];
    }

    public void enqueue(T item)
    {
        if (isFull())
        {
            return;
        }

        rear = (rear + 1) % capacity;
        queue[rear] = item;
        count++;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public boolean isFull() {
        return (size() == capacity);
    }
}
