package btree;

import java.util.ArrayList;
import java.util.List;

public class BNode<E extends Comparable<E>> {
    private static int nextId = 1;

    protected ArrayList<E> keys;
    protected ArrayList<BNode<E>> childs;
    protected int count;
    protected int idNode;

    public BNode(int n) {
        this.keys = new ArrayList<E>(n);
        this.childs = new ArrayList<BNode<E>>(n);
        this.count = 0;
        this.idNode = nextId++;
        for (int i = 0; i < n; i++) {
            this.keys.add(null);
            this.childs.add(null);
        }
    }

    public boolean nodeFull(int maxKeys) {
        return this.count == maxKeys;
    }

    public boolean nodeEmpty() {
        return this.count == 0;
    }

    public boolean searchNode(E cl, int[] pos) {
        int i = 0;
        while (i < this.count && cl.compareTo(this.keys.get(i)) > 0) {
            i++;
        }
        pos[0] = i;
        return i < this.count && cl.compareTo(this.keys.get(i)) == 0;
    }

    public boolean isLeaf() {
        return this.childs.get(0) == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node ").append(idNode).append(": ");
        for (int i = 0; i < count; i++) {
            sb.append(keys.get(i));
            if (i + 1 < count) sb.append(", ");
        }
        return sb.toString();
    }
}