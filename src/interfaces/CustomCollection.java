package interfaces;

import interfaces.impls.Collection;

public interface CustomCollection<Obj> {

    boolean add(Obj e);

    boolean addAll(Collection<? extends Obj> c);

    boolean delete(int index);

    boolean delete(Obj obj);

    Obj get(int index);

    boolean contains(Obj str);

    boolean clear();

    int size();

    boolean trim();

    boolean compare(Collection<? extends Obj> c);

}
