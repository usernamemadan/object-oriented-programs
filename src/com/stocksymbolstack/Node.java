package stocksymbolstack;


public class Node<T> {
    public T data;
    public Node<T> next;

    public Node(T data) {
        this.data = data;
        next = null;
    }

    public T getData(){
        return data;
    }

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public Node<T> getNext() {
		 return next;
	}
}
