package biblioteca;

public class Libro implements Comparable<Libro> {
    private String isbn;
    private String titulo;
    private String autor;
    private int anio;

    public Libro(String isbn, String titulo, String autor, int anio) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public int compareTo(Libro other) {
        return this.isbn.compareTo(other.isbn);
    }

    @Override
    public String toString() {
        return isbn + " - " + titulo + " - " + autor + " - " + anio;
    }
}
