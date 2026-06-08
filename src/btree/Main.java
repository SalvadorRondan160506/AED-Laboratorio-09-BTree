package btree;

public class Main {

    public static void main(String[] args) {

        BTree<Integer> tree = new BTree<>(4);

        int[] datos = {
                50, 20, 70, 10, 30,
                60, 80, 25, 27, 26,
                65, 75, 85, 5
        };

        System.out.println("=== INSERCION ===");

        for (int x : datos) {
            System.out.println("Insertando: " + x);
            tree.insert(x);
        }

        System.out.println("\n=== ARBOL RESULTANTE ===");
        System.out.println(tree);

        //busqueda

        tree.search(50);
        tree.search(85);
        tree.search(100);

        System.out.println("\n=== BUSQUEDA POR RANGO ===");

        tree.searchRange(20, 70);

        System.out.println("\n\n=== ELIMINACIONES ===");

        int[] eliminar = {
                25, 10, 50, 70, 27, 5, 75
        };

        for (int x : eliminar) {

            System.out.println("\nEliminando: " + x);

            tree.remove(x);

            System.out.println(tree);
        }

        System.out.println("\n=== DATOS FINALES ===");

        System.out.println(
                "Altura del arbol: "
                        + tree.height());

        System.out.println(
                "Cantidad de claves: "
                        + tree.size());
    }
}
