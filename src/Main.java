package btree;

public class Main {

    public static void main(String[] args) {

        BTree<Integer> tree =
                new BTree<>(4);

        int[] datos = {
                50,20,70,10,30,
                60,80,25,27,26,
                65,75,85,5
        };

        for (int x : datos) {
            tree.insert(x);
        }

        System.out.println(
                "ARBOL B");

        System.out.println(tree);

        System.out.println(
                "\nBUSQUEDAS");

        tree.search(50);
        tree.search(85);
        tree.search(100);

        System.out.println(
                "\nRANGO 20-70");

        tree.searchRange(
                20,
                70);
    }
}