public class Node<T> {
    private Node<T> next;
    private Node<T> down;
    private Node<T> previous;
    private T payload;

    public Node() {
        this.next = null;
        this.down = null;
        this.previous = null;
        this.payload = null;
    }

    public Node(Node<T> next, Node<T> down, Node<T> previous, T payload) {
        this.next = next;
        this.down = down;
        this.previous = previous;
        this.payload = payload;
    }

    // Getters and setters for next, down, previous, and payload
    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next){
        this.next = next;
    }

    public Node<T> getDown() {
        return down;
    }

    public void setDown(Node<T> down){
        this.down = down;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> previous){
        this.previous = previous;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload){
        this.payload = payload;
    }

}
