import java.util.Iterator;

public class NodePriorityQueue<E extends Comparable> implements PriorityQueue<E> {

    int count;
    Node<E> top;
    Node<E> iterator;

    public Node<E> getTop() {
        return top;
    }
    public int getCount() {
        return count;
    }

    public void insert(E item) {
        Node<E> i = getTop();
        Node<E> node =  new Node<E>(item, null);
        if (i == null){
            top = node;
            count++;
        }
        else{
            while(i.item.compareTo(node.item) > 0){
                i = i.getNext();
            }
            Node<E> iNext = i.getNext();
            node.setNext(iNext);
            i.setNext(node);
            count++;
        }
    }

    @Override
    public E serve() {
        Node<E> oldTop = top;
        top = top.getNext();
        count--;
        return oldTop.item;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    private class PriorityQueueIterator implements Iterator<E>{
        int index = 0;

        @Override
        public boolean hasNext() {
            return index < count;
        }

        @Override
        public E next() {
            if (hasNext()){
                iterator = iterator.next;
                index++;
                return (E) iterator;
            }
            return null;
        }
    }
    @Override
    public Iterator<E> iterator() {
        return new PriorityQueueIterator();
    }
}
