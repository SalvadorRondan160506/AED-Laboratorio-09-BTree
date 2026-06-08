package biblioteca;

import btree.BTree;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Biblioteca {
    private BTree<Libro> libros;

    public void cargarDesdeArchivo(String archivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            int orden = Integer.parseInt(br.readLine().trim());
            libros = new BTree<>(orden);

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", 4);
                if (partes.length != 4) continue;

                Libro libro = new Libro(
                        partes[0].trim(),
                        partes[1].trim(),
                        partes[2].trim(),
                        Integer.parseInt(partes[3].trim())
                );

                if (!libros.contains(libro)) {
                    libros.insert(libro);
                } else {
                    System.out.println("ISBN duplicado: " + partes[0].trim());
                }
            }
        }
    }
    public void agregarLibro(Libro libro) {
        if (!libros.search(libro)) libros.insert(libro);
    }

    public boolean buscarPorISBN(String isbn) {
        return libros.search(new Libro(isbn, "", "", 0));
    }

    public void eliminarPorISBN(String isbn) {

        Libro libro =
                new Libro(isbn,"","",0);

        if(libros.search(libro)) {

            libros.remove(libro);

            System.out.println(
                    "\nLibro eliminado: "
                            + isbn
            );

        } else {

            System.out.println(
                    "\nISBN no encontrado: "
                            + isbn
            );
        }
    }

    public void mostrarLibrosOrdenados() {

        System.out.println("\n=== LIBROS ORDENADOS ===");

        for(Libro libro : libros.inOrder()) {
            System.out.println(libro);
        }
    }

    public void mostrarLibros() {
        System.out.println(libros);
    }

    public int altura() {
        return libros.height();
    }

    public int totalLibros() {
        return libros.size();
    }
}