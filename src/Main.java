public class Main {

    public static void main(String[] args) {

        Library library = new Library();
        printBookArray(library.getLatestBooks());
        System.out.println(library.bookCount + "<< COUNT");

//        Review review = new Review("bad book", 2);
//        System.out.println(review);
//        Book tempBook = new Book("2005018", "Clara Callan" , "Richard Bruce Wright", "2001" , "HarperFlamingo Canada" , Genre.Mystery);
//        System.out.println(tempBook.toString());
//        tempBook.addReview(review);
//        tempBook.addReview("good book", 4);
//        System.out.println(tempBook.getReviewRating());

    }

    private static void printBookArray(Book[] books) {
        System.out.println("\n\n PRINTING \n");
        for (int i = 0; i < books.length; i++) {
            books[i].toString();
        }
    }

}