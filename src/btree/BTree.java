package btree;

import java.util.ArrayList;
import java.util.List;

public class BTree<E extends Comparable<E>> {
    private BNode<E> root;
    private int orden;
    private boolean up;
    private BNode<E> nDes;

    public BTree(int orden) {
        this.orden = orden;
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void insert(E cl) {
        up = false;
        E mediana = push(this.root, cl);
        if (up) {
            BNode<E> pnew = new BNode<E>(this.orden);
            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }

    private E push(BNode<E> current, E cl) {
        int[] pos = new int[1];
        if (current == null) {
            up = true;
            nDes = null;
            return cl;
        }

        if (current.searchNode(cl, pos)) {
            System.out.println("Item duplicado");
            up = false;
            return null;
        }

        E mediana = push(current.childs.get(pos[0]), cl);
        if (up) {
            if (current.nodeFull(this.orden - 1)) {
                mediana = dividedNode(current, mediana, pos[0]);
            } else {
                putNode(current, mediana, nDes, pos[0]);
                up = false;
            }
        }
        return mediana;
    }

    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        for (int i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }
        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);
        current.count++;
    }

    private E dividedNode(BNode<E> current, E cl, int k) {
        BNode<E> rd = nDes;
        int posMdna = (k <= this.orden / 2) ? this.orden / 2 : this.orden / 2 + 1;
        nDes = new BNode<E>(this.orden);

        for (int i = posMdna; i < this.orden - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
            nDes.childs.set(i - posMdna + 1, current.childs.get(i + 1));
        }

        nDes.count = (this.orden - 1) - posMdna;
        current.count = posMdna;

        if (k <= this.orden / 2) {
            putNode(current, cl, rd, k);
        } else {
            putNode(nDes, cl, rd, k - posMdna);
        }

        E median = current.keys.get(current.count - 1);
        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;
        up = true;
        return median;
    }

    public boolean search(E cl) {
        System.out.println(
                "\n=== BUSQUEDA DE "
                        + cl
                        + " ===");

        boolean found = search(root, cl);

        if (!found) {

            System.out.println(
                    "Clave "
                            + cl
                            + " no encontrada");
        }

        return found;
    }

    private boolean search(BNode<E> current, E cl) {

        if(current == null) {
            System.out.println("Clave no encontrada");
            return false;
        }
        System.out.println(
                "Visitando nodo "
                        + current.idNode
                        + " -> "
                        + current
        );

        int[] pos = new int[1];
        if(current.searchNode(cl, pos)) {

            System.out.println(
                    "Clave encontrada en el nodo "
                            + current.idNode
                            + " en la posicion "
                            + pos[0]
            );

            return true;
        }
        return search(
                current.childs.get(pos[0]),
                cl
        );
    }

    public void searchRange(E min, E max) {
        if (min.compareTo(max) > 0) {
            System.out.println("Rango invalido");
            return;
        }
        List<E> result = new ArrayList<>();
        searchRange(this.root, min, max, result);
        for (E key : result) {
            System.out.print(key + " ");
        }
        System.out.println();
    }

    private void searchRange(BNode<E> current, E min, E max, List<E> result) {
        if (current == null) return;

        int i = 0;
        while (i < current.count) {
            if (current.keys.get(i).compareTo(min) >= 0) {
                searchRange(current.childs.get(i), min, max, result);
            }

            if (current.keys.get(i).compareTo(min) >= 0 && current.keys.get(i).compareTo(max) <= 0) {
                result.add(current.keys.get(i));
            }

            if (current.keys.get(i).compareTo(max) > 0) {
                return;
            }
            i++;
        }
        searchRange(current.childs.get(i), min, max, result);
    }

    public void remove(E cl) {
        if (root == null) return;
        remove(root, cl);
        if (root.count == 0) {
            root = root.childs.get(0);
        }
    }

    private void remove(BNode<E> current, E cl) {
        int[] pos = new int[1];
        boolean found = current.searchNode(cl, pos);

        if (found) {
            if (current.isLeaf()) {
                removeKey(current, pos[0]);
            } else {
                E successor = getMin(current.childs.get(pos[0] + 1));
                current.keys.set(pos[0], successor);
                remove(current.childs.get(pos[0] + 1), successor);
                fixUnderflow(current, pos[0] + 1);
            }
        } else {
            if (current.isLeaf()) return;
            remove(current.childs.get(pos[0]), cl);
            fixUnderflow(current, pos[0]);
        }
    }

    private void removeKey(BNode<E> node, int index) {
        for (int i = index; i < node.count - 1; i++) {
            node.keys.set(i, node.keys.get(i + 1));
        }
        node.keys.set(node.count - 1, null);
        node.count--;
    }

    private E getMin(BNode<E> node) {
        while (!node.isLeaf()) node = node.childs.get(0);
        return node.keys.get(0);
    }

    private void fixUnderflow(BNode<E> parent, int index) {
        int minKeys = (int) Math.ceil(this.orden / 2.0) - 1;
        BNode<E> child = parent.childs.get(index);
        if (child == null || child.count >= minKeys) return;

        if (index > 0 && parent.childs.get(index - 1).count > minKeys) {
            borrowFromLeft(parent, index);
        } else if (index < parent.count && parent.childs.get(index + 1).count > minKeys) {
            borrowFromRight(parent, index);
        } else if (index > 0) {
            merge(parent, index - 1);
        } else {
            merge(parent, index);
        }
    }

    private void borrowFromLeft(BNode<E> parent, int index) {
        BNode<E> child = parent.childs.get(index);
        BNode<E> left = parent.childs.get(index - 1);

        for (int i = child.count - 1; i >= 0; i--) child.keys.set(i + 1, child.keys.get(i));
        for (int i = child.count; i >= 0; i--) child.childs.set(i + 1, child.childs.get(i));

        child.keys.set(0, parent.keys.get(index - 1));
        child.childs.set(0, left.childs.get(left.count));
        child.count++;

        parent.keys.set(index - 1, left.keys.get(left.count - 1));
        left.keys.set(left.count - 1, null);
        left.childs.set(left.count, null);
        left.count--;
    }

    private void borrowFromRight(BNode<E> parent, int index) {
        BNode<E> child = parent.childs.get(index);
        BNode<E> right = parent.childs.get(index + 1);

        child.keys.set(child.count, parent.keys.get(index));
        child.childs.set(child.count + 1, right.childs.get(0));
        child.count++;

        parent.keys.set(index, right.keys.get(0));
        for (int i = 0; i < right.count - 1; i++) right.keys.set(i, right.keys.get(i + 1));
        for (int i = 0; i < right.count; i++) right.childs.set(i, right.childs.get(i + 1));
        right.keys.set(right.count - 1, null);
        right.childs.set(right.count, null);
        right.count--;
    }

    private void merge(BNode<E> parent, int index) {
        BNode<E> left = parent.childs.get(index);
        BNode<E> right = parent.childs.get(index + 1);

        left.keys.set(left.count, parent.keys.get(index));
        left.count++;

        for (int i = 0; i < right.count; i++) {
            left.keys.set(left.count + i, right.keys.get(i));
        }
        for (int i = 0; i <= right.count; i++) {
            left.childs.set(left.count + i, right.childs.get(i));
        }
        left.count += right.count;

        for (int i = index; i < parent.count - 1; i++) {
            parent.keys.set(i, parent.keys.get(i + 1));
            parent.childs.set(i + 1, parent.childs.get(i + 2));
        }
        parent.keys.set(parent.count - 1, null);
        parent.childs.set(parent.count, null);
        parent.count--;
    }

    public int height() {
        return height(root);
    }

    private int height(BNode<E> node) {
        if (node == null) return 0;
        if (node.isLeaf()) return 1;
        return 1 + height(node.childs.get(0));
    }

    public int size() {
        return size(root);
    }

    private int size(BNode<E> node) {
        if (node == null) return 0;
        int total = node.count;
        for (int i = 0; i <= node.count; i++) {
            total += size(node.childs.get(i));
        }
        return total;
    }

    public List<E> inOrder() {
        List<E> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(BNode<E> node, List<E> result) {
        if (node == null) return;
        for (int i = 0; i < node.count; i++) {
            inOrder(node.childs.get(i), result);
            result.add(node.keys.get(i));
        }
        inOrder(node.childs.get(node.count), result);
    }

    @Override
    public String toString() {
        if (isEmpty()) return "BTree is empty...";
        return writeTree(this.root, 0);
    }

    private String writeTree(BNode<E> current, int level) {
        if (current == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(level)).append(current).append("\n");
        for (int i = 0; i <= current.count; i++) {
            sb.append(writeTree(current.childs.get(i), level + 1));
        }
        return sb.toString();
    }

    public boolean contains(E key) {
        return contains(root, key);
    }

    private boolean contains(
            BNode<E> current,
            E key) {

        if(current == null)
            return false;

        int[] pos = new int[1];

        boolean found =
                current.searchNode(key, pos);

        if(found)
            return true;

        return contains(
                current.childs.get(pos[0]),
                key);
    }
}