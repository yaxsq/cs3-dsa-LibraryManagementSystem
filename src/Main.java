public class Main {

    public static void main(String[] args) {

        // FYI: we have 250 books in books.txt but only ~221 dont have a ", " so we're actually working with 221 books right now
        Library library = new Library();
//        printBookArray(library.getLatestBooks());
//        printBookArray(library.getSortedByGenreBooks(Genre.Mystery));



    }

    private static void printBookArray(Book[] books) {
        System.out.println("\n\n PRINTING \n");
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i].toString());
        }
    }

}