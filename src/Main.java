public class Main {

    public static void main(String[] args) {

        Library library = new Library();
        printBookArray(library.getLatestBooks());
        System.out.println(library.bookCount + "<< COUNT");

    }

    private static void printBookArray(Book[] books) {
        System.out.println("\n\n PRINTING \n");
        for (int i = 0; i < books.length; i++) {
            books[i].toString();
        }
    }

}