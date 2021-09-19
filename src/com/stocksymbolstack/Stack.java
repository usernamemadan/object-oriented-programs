package stocksymbolstack;

import java.util.EmptyStackException;
import java.util.Iterator;

/* this class stores the the company shares in a stack
*/
public class Stack<T> implements Iterable<Node<T>>{
    Node<T> top;

    public Stack() {
        top = null;
    }

    public void push(T data) {
        Node<T> newNode = new Node<T>(data);

        if (top == null) {
            top = newNode;
        } else {
            newNode.setNext(top);
            top = newNode;
        }
    }

    public T pop() {
        T popped = null;
        if (top == null) {
            throw new EmptyStackException();
        } else {
            popped = top.getData();
            top = top.getNext();
        }
        return popped;
    }

    public T peek() throws EmptyStackException {
        if (top == null) {
            throw new EmptyStackException();
        } else {
            return top.getData();
        }
    }

    @Override
    public Iterator<Node<T>> iterator() {
        return new StackIterator<T>(top);
    }
}