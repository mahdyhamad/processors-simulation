public class Node<E>{
    E item;
    Node<E> next;

    Node(){
        item = null;
        next = null;
    }

    Node(E item, Node n){
        this.item = item;
        this.next = n;
    }

    public Node<E> getNext() {
        return next;
    }
    public void setNext(Node<E> next) {
        this.next = next;
    }
}
