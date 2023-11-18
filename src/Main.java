public class Main {

    public static void main(String[] args) {

        // FYI: we have 250 books in books.txt but only ~221 dont have a ", " so we're actually working with 221 books right now
        Library library = new Library();
        //   printBookArray(library.getLatestBooks());

        // @ Qamar = this retrieves a 10 book array from mostPopular array and prints
        printBookArray(library.getMostPopularity());

        // @ Qamar = this retrieves a 10 book array from leastPopular array and prints
        printBookArray(library.getLeastPopularity());

//        printBookArray(library.getSortedByGenreBooks(Genre.Mystery));y

        System.out.println();

    }

    private static void printBookArray(Book[] books) {
        System.out.println("\n\n PRINTING \n");
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

}