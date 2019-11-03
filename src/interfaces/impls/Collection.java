package interfaces.impls;

import java.util.NoSuchElementException;

public class Collection<Obj> {

    private int count = 0;

    private Node<Obj> first;
    private Node<Obj> last;

    public Collection() {

    }

    private void linkFirst(Obj e) {
        final Node<Obj> f = first;
        final Node<Obj> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        count++;
    }

    private void linkLast(Obj e) {
        final Node<Obj> l = last;
        final Node<Obj> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        count++;
    }

    private void linkBefore(Obj e, Node<Obj> succ) {
        final Node<Obj> pred = succ.prev;
        final Node<Obj> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        count++;
    }

    private Obj unlinkFirst(Node<Obj> f) {
        final Obj element = f.item;
        final Node<Obj> next = f.next;
        f.item = null;
        f.next = null;
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        count--;
        return element;
    }

    private Obj unlinkLast(Node<Obj> l) {
        final Obj element = l.item;
        final Node<Obj> prev = l.prev;
        l.item = null;
        l.prev = null;
        last = prev;

        if (prev == null)
            first = null;
        else
            prev.next = null;
        count--;
        return element;
    }

    private Obj unlink(Node<Obj> x) {
        final Obj element = x.item;
        final Node<Obj> next = x.next;
        final Node<Obj> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        count--;
        return element;
    }

    public Obj getFirst() {
        final Node<Obj> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return f.item;
    }

    public Obj getLast() {
        final Node<Obj> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return l.item;
    }

    public Obj removeFirst() {
        final Node<Obj> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return unlinkFirst(f);
    }

    public Obj removeLast() {
        final Node<Obj> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return unlinkLast(l);
    }

    public void addFirst(Obj e) {
        linkFirst(e);
    }

    public void addLast(Obj e) {
        linkLast(e);
    }


    public int size() {
        return count;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public boolean add(Obj e) {
        linkLast(e);
        return true;
    }

    public boolean delete(int index) {
        checkElementIndex(index);
        unlink(node(index));
        return true;
    }

    public boolean delete(Object o) {
        if (o == null) {
            for (Node<Obj> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<Obj> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addAll(Collection<? extends Obj> c) {
        return addAll(count, c);
    }

    public boolean addAll(int index, Collection<? extends Obj> c) {
        checkPositionIndex(index);

        Object[] array = new Object[c.count];
        int arrLength = array.length;
        if (arrLength == 0)
            return false;

        for (int i = 0; i < c.count; i++) {
            array[i] = c.indexOf(i);
        }

        Node<Obj> pred, succ;
        if (index == count) {
            succ = null;
            pred = last;
        } else {
            succ = node(index);
            pred = succ.prev;
        }

        for (Object o : array) {
            @SuppressWarnings("unchecked") Obj obj = (Obj) o;
            Node<Obj> newNode = new Node<>(pred, obj, null);
            if (pred == null)
                first = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }

        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        count += arrLength;
        return true;
    }

    public boolean clear() {
        for (Node<Obj> x = first; x != null; ) {
            Node<Obj> next = x.next;
            x.item = null;
            x.prev = null;
            x.next = null;
            x = next;
        }

        first = last = null;
        count = 0;
        return true;
    }

    // Positional Access Operations

    public Obj get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    public Obj set(int index, Obj element) {
        checkElementIndex(index);
        Node<Obj> x = node(index);
        Obj oldVal = x.item;
        x.item = element;
        return oldVal;
    }

    public void add(int index, Obj element) {
        checkPositionIndex(index);

        if (index == count)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + count;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < count;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= count;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private Node<Obj> node(int index) {
        if (index < (count >> 1)) {
            Node<Obj> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<Obj> x = last;
            for (int i = count - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }


    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<Obj> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<Obj> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    public boolean compare(Collection<? extends Obj> coll) {
        if (this.count != coll.count)
            return false;
        for (int i = 0; i < coll.count; i++) {
            if (!this.contains(coll.get(i)))
                return false;
        }
        return true;
    }

    private static class Node<Obj> {
        Obj item;
        Node<Obj> next;
        Node<Obj> prev;

        Node(Node<Obj> prev, Obj element, Node<Obj> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

    }

    public boolean trim() {
        return false;
    }
}
