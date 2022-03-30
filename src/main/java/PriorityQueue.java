public interface PriorityQueue<E> extends Iterable<E> {
    public void insert(E item);
    public E serve();
    public boolean isEmpty();
}
