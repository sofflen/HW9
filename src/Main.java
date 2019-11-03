import interfaces.impls.Collection;

public class Main {

    public static void main(String[] args) {

        Collection<String> collection = new Collection<>();

        collection.addFirst("Z");
        collection.add(1, "A");
        collection.addLast("G");
        System.out.println(collection.getFirst());
        System.out.println(collection.getLast());
        System.out.println(collection.removeFirst());
        System.out.println(collection.removeLast());
        System.out.println(collection.add("B"));
        collection.add("C");
        collection.add("D");
        collection.add("E");
        collection.add("F");
        System.out.println(collection.delete(5));
        System.out.println(collection.delete("E"));
        System.out.println(collection.delete("F"));
        System.out.println(collection.contains("B"));
        System.out.println(collection.size());
    }
}
