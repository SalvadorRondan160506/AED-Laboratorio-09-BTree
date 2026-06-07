package btree;

import java.util.ArrayList;

public class BNode<E extends Comparable<E>> {

    protected ArrayList<E> keys;
    protected ArrayList<BNode<E>> childs;
    protected int count;
    protected int idNode;

    private static int nextId = 1;

    public BNode(int n) {
        keys = new ArrayList<>(n);
        childs = new ArrayList<>(n);

        count = 0;
        idNode = nextId++;

        for (int i = 0; i < n; i++) {
            keys.add(null);
            childs.add(null);
        }
    }

    public boolean nodeFull(int maxKeys) {
        return count == maxKeys;
    }

    public boolean nodeEmpty() {
        return count == 0;
    }

    public boolean searchNode(E key, int[] pos) {

        pos[0] = 0;

        while (pos[0] < count &&
                key.compareTo(keys.get(pos[0])) > 0) {
            pos[0]++;
        }

        if (pos[0] < count &&
                key.compareTo(keys.get(pos[0])) == 0) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Nodo ");
        sb.append(idNode);
        sb.append(": (");

        for (int i = 0; i < count; i++) {

            sb.append(keys.get(i));

            if (i < count - 1) {
                sb.append(", ");
            }
        }

        sb.append(")");

        return sb.toString();
    }
}