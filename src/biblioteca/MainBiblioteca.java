package biblioteca;

public class MainBiblioteca {

    public static void main(String[] args) {

        try {

            Biblioteca biblioteca =
                    new Biblioteca();

            biblioteca.cargarDesdeArchivo(
                    "biblioteca.txt");

            System.out.println(
                    "\n=== LIBROS CARGADOS ===");

            biblioteca.mostrarLibrosOrdenados();

            System.out.println(
                    "\n=== ESTRUCTURA DEL ARBOL ===");

            biblioteca.mostrarLibros();

            System.out.println(
                    "\n=== BUSQUEDA DE LIBRO ===");

            biblioteca.buscarPorISBN(
                    "9780132350884");

            System.out.println(
                    "\n=== ALTURA DEL ARBOL ===");

            System.out.println(
                    biblioteca.altura());

            System.out.println(
                    "\n=== TOTAL DE LIBROS ===");

            System.out.println(
                    biblioteca.totalLibros());

            System.out.println(
                    "\n=== ELIMINAR LIBRO ===");

            biblioteca.eliminarPorISBN(
                    "9780201633610");

            System.out.println(
                    "\n=== ARBOL DESPUES DE ELIMINAR ===");

            biblioteca.mostrarLibros();

            biblioteca.mostrarLibrosOrdenados();

        } catch (Exception e) {

            System.out.println(
                    "Error: "
                            + e.getMessage());
        }
    }
}