package com.dateandtimequeue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * this class stores the date and time in a queue
*/
public class Queue<T> implements Iterable<Node<T>> {
    Node<T> front;
    Node<T> rear;

    public Queue() {
        front = null;
        rear = front;
    }

    public void enqueue(T data) {
        Node<T> newNode = new Node<T>(data);

        if (rear == null) {
            rear = newNode;
            front = rear;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }

    }

    public T dequeue()  {
        T dequed = null;

        if (front == null) {
            System.out.println("Queue is empty");
        }
        else if (front == rear) {
            dequed = front.getData();
            front = null;
            rear = front;
        }
        else {
            dequed = front.getData();
            front = front.getNext();
        }

        return dequed;
    }

    @Override
    public Iterator<Node<T>> iterator() {
        return new QueueIterator<T>(front);
    }

}