package btree;

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
        return root == null;
    }

    public void insert(E cl) {

        up = false;

        E mediana;
        BNode<E> pnew;

        mediana = push(root, cl);

        if (up) {

            pnew = new BNode<>(orden);

            pnew.count = 1;

            pnew.keys.set(0, mediana);

            pnew.childs.set(0, root);
            pnew.childs.set(1, nDes);

            root = pnew;
        }
    }

    private E push(BNode<E> current, E cl) {

        int[] pos = new int[1];

        E mediana;

        if (current == null) {

            up = true;
            nDes = null;

            return cl;
        }

        boolean found =
                current.searchNode(cl, pos);

        if (found) {

            System.out.println("Clave duplicada");

            up = false;

            return null;
        }

        mediana =
                push(current.childs.get(pos[0]), cl);

        if (up) {

            if (current.nodeFull(orden - 1)) {

                mediana =
                        dividedNode(current,
                                mediana,
                                pos[0]);
            } else {

                up = false;

                putNode(
                        current,
                        mediana,
                        nDes,
                        pos[0]);
            }
        }

        return mediana;
    }

    private void putNode(
            BNode<E> current,
            E cl,
            BNode<E> rd,
            int k) {

        int i;

        for (i = current.count - 1;
             i >= k;
             i--) {

            current.keys.set(
                    i + 1,
                    current.keys.get(i));

            current.childs.set(
                    i + 2,
                    current.childs.get(i + 1));
        }

        current.keys.set(k, cl);

        current.childs.set(k + 1, rd);

        current.count++;
    }

    private E dividedNode(
            BNode<E> current,
            E cl,
            int k) {

        BNode<E> rd = nDes;

        int i;
        int posMdna;

        posMdna =
                (k <= orden / 2)
                        ? orden / 2
                        : orden / 2 + 1;

        nDes = new BNode<>(orden);

        for (i = posMdna;
             i < orden - 1;
             i++) {

            nDes.keys.set(
                    i - posMdna,
                    current.keys.get(i));

            nDes.childs.set(
                    i - posMdna + 1,
                    current.childs.get(i + 1));
        }

        nDes.count =
                (orden - 1) - posMdna;

        current.count = posMdna;

        if (k <= orden / 2) {

            putNode(current, cl, rd, k);

        } else {

            putNode(
                    nDes,
                    cl,
                    rd,
                    k - posMdna);
        }

        E median =
                current.keys.get(current.count - 1);

        nDes.childs.set(
                0,
                current.childs.get(current.count));

        current.count--;

        up = true;

        return median;
    }

    public boolean search(E key) {
        return search(root, key);
    }

    private boolean search(
            BNode<E> current,
            E key) {

        if (current == null) {
            return false;
        }

        int[] pos = new int[1];

        boolean found =
                current.searchNode(key, pos);

        if (found) {

            System.out.println(
                    key +
                            " encontrado en nodo "
                            + current.idNode +
                            " posicion "
                            + pos[0]);

            return true;
        }

        return search(
                current.childs.get(pos[0]),
                key);
    }

    public void searchRange(
            E min,
            E max) {

        System.out.print(
                "Rango: ");

        searchRange(
                root,
                min,
                max);

        System.out.println();
    }

    private void searchRange(
            BNode<E> current,
            E min,
            E max) {

        if (current == null) {
            return;
        }

        int i;

        for (i = 0;
             i < current.count;
             i++) {

            searchRange(
                    current.childs.get(i),
                    min,
                    max);

            E key =
                    current.keys.get(i);

            if (key.compareTo(min) >= 0 &&
                    key.compareTo(max) <= 0) {

                System.out.print(
                        key + " ");
            }
        }

        searchRange(
                current.childs.get(i),
                min,
                max);
    }

    @Override
    public String toString() {

        StringBuilder sb =
                new StringBuilder();

        writeTree(root, sb);

        return sb.toString();
    }

    private void writeTree(
            BNode<E> current,
            StringBuilder sb) {

        if (current == null) {
            return;
        }

        sb.append(current);
        sb.append("\n");

        for (int i = 0;
             i <= current.count;
             i++) {

            writeTree(
                    current.childs.get(i),
                    sb);
        }
    }
}