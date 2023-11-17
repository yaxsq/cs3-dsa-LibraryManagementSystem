import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.TreeSet;

public class Library {

    private Hashtable<Integer, Book> books;
    private Book[] latestList;
    private ArrayList<Book>[] genreList;            // 0-9 index where each is a genre. Genre is an enum
    //I do not know how to implement Heaptrees xd
    private TreeSet<Book> popular; // use Lab heaptree or learn treeset.
    private ArrayList<Review> randomReviews;
    public int bookCount;

    /**
     * genre is an array of arraylists
     * each index corresponds to a specific genre
     * the index for every genre is the same as its enum value
     * <p>
     * latest is a simple array which contains books published in 2020 or 2021
     */
    public Library() {
        initializeLists();
        populateBooks();
        populateRandomReviews();
        bookCount = 0;
        //Popular needs to be reformatted into the custom heaptree. Therefore I am not including it in the initialization now. -Abdur Rehman.
    }

    private void initializeLists() {
        books = new Hashtable<>();
        genreList = new ArrayList[10];
        latestList = new Book[15];
        randomReviews = new ArrayList<>();

        for (int i = 0; i < genreList.length; i++) {
            genreList[i] = new ArrayList<>();
        }
    }

    /**
     * reads the text file
     * adds a new book to the lists
     */
    private void populateBooks() {
        try {
            File file = new File("src/data/books.txt");
            Scanner in = new Scanner(file);

            if (file.exists()) {
                while (in.hasNextLine()) {
                    String line = in.nextLine();

                    if (line.contains(", ")) {
                        continue;                   // goes to the next iteration if the line contains ", " to avoid conflicts in splitting
                    }

                    String[] attributes = line.split(",");
                    addBook(attributes);
                }

            } else {
                System.out.println("File does not exist.        Library/populateBooks()");
            }

            in.close();

        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found.        Library/populateBooks()");
        }
    }

    /**
     * reads reviews from a text file and fills an array
     * the array contains reviews which can be assigned to books
     */
    private void populateRandomReviews() {
        try {
            File file = new File("src/data/reviews.txt");
            Scanner in = new Scanner(file);

            if (file.exists()) {
                while (in.hasNextLine()) {
                    String[] reviewElements = in.nextLine().split(">");
                    randomReviews.add(new Review(reviewElements[0], Integer.parseInt(reviewElements[1])));
                }
            }

            in.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Library/generateReview()    Review file not found");
        }
    }

    /**
     * calls addBook()
     *
     * @param attributes array containing attributes of a book
     */
    private void addBook(String[] attributes) {
        if (attributes.length == 6) {
            addBook(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5]);
        } else {
            System.out.println("Invalid array        Library/addBook()");
        }
    }

    /**
     * add to the Big General Storage
     * checks the book's publishing date, genre and popularity and updates the pointers in those arrays appropriately
     */
    public void addBook(String isbn, String title, String author, String pubDate, String publisher, String genre) {
        Book book = new Book(isbn, title, author, pubDate, publisher, getGenre(genre));

        // adding to BGS
//        int key = 0;
//        for (int i = 0; i < title.length() - 1; i++) { // this method of producing key is used since it allows us to use the title as the hash value.
//            key += title.codePointAt(i);          // we can alternatively use the Books.length function to gain the number of keys inside the HashTable.
//        }
        books.putIfAbsent(calculateKey(title), book);

        // updating latest
        if (isLatest(pubDate)) {
            for (int i = 0; i < latestList.length; i++) {
                if (latestList[i] == null) {                    // fills a null node
                    latestList[i] = book;
                    break;
                }
            }
        }

        // updating genre
        switch (getGenre(genre)) {
            case ScienceFiction:
                this.genreList[0].add(book);
                break;
            case Fantasy:
                this.genreList[1].add(book);
                break;
            case Mystery:
                this.genreList[2].add(book);
                break;
            case Thriller:
                this.genreList[3].add(book);
                break;
            case HistoricalFiction:
                this.genreList[4].add(book);
                break;
            case Horror:
                this.genreList[5].add(book);
                break;
            case Biography:
                this.genreList[6].add(book);
                break;
            case Selfhelp:
                this.genreList[7].add(book);
                break;
            case Romance:
                this.genreList[8].add(book);
                break;
            case YoungAdult:
                this.genreList[9].add(book);
                break;
        }

//        System.out.println(bookCount++);
        System.out.println("Library/addBooks()  added >" + book.toString());
        System.out.println("Library/addBooks()  TITLE >" + getBook(book.getTitle()).getTitle());
        System.out.println("Library/addBooks()  TITLE GENRE >" + genreList[getGenre(genre).getValue()].get(genreList[getGenre(genre).getValue()].size() - 1));

        // For the addition by popularity the Heaptree and the review addition needs to be done first. This as lazy as I am, will leave to you guys.:D
    }

    public void deleteBook(String title) {
        books.remove(calculateKey(title));
        updateLatest();
    }

    private void rearrangeHeapTree() {
        //

    }

    /**
     * goes through the latestList to check if an entry is null
     * if a null entry is found, the node is updated to a new latest book
     */
    private void updateLatest() {
        for (int i = 0; i < latestList.length; i++) {
            if (latestList[i] == null) {
                while (true) {
                    int randomGenre = (int) (Math.random() * 10);
                    int genreLength = genreList[randomGenre].size();
                    Book randomBook = genreList[randomGenre].get((int) (Math.random() * genreLength));
                    if (isLatest(randomBook.getPubDate())) {
                        latestList[i] = randomBook;
                        return;
                    }
                }
            }
        }

    }

    /**
     * checks if the pubDate input is latest i.e. 2002 or newer
     *
     * @param pubDate
     * @return true if latest, false otherwise
     */
    private boolean isLatest(String pubDate) {
        if (Integer.parseInt(pubDate) > 2002) {
            return true;
        }
        return false;
    }

    /**
     * calculates the hash key of the input
     *
     * @param title
     * @return hash key
     */
    private int calculateKey(String title) {
        int key = 0;
        for (int i = 0; i < title.length(); i++) {
            key += title.codePointAt(i);
        }

        return key;
    }

    /** !!!! CHANGE BACK TO PRIVATE AFTER TESTING !!!!
     * @return a random review from the list of reviews
     */
    public Review getRandomReview() {
        return randomReviews.get((int)(Math.random()*randomReviews.size()));
    }

    public void getSortedByPopularBooks() {
        // return most popular using heaptree.
    }

    /**
     * returns an array of 10 books according to the genre input
     * <p>
     * the array is filled by going through the arrayList of the genre input
     * random books from the array are selected
     *
     * @param genre
     */
    public Book[] getSortedByGenreBooks(Genre genre) {
        int value = genre.getValue();
        Book[] sortedBooks = new Book[10];

        for (int i = 0; i < sortedBooks.length; i++) {
            sortedBooks[i] = genreList[value].get((int) (Math.random() * genreList[value].size()));
        }

        return sortedBooks;
    }

    private Genre getGenre(String genre) {
        if (genre.equalsIgnoreCase("Science Fiction")) {
            return Genre.ScienceFiction;
        } else if (genre.equalsIgnoreCase("Fantasy")) {
            return Genre.Fantasy;
        } else if (genre.equalsIgnoreCase("Mystery")) {
            return Genre.Mystery;
        } else if (genre.equalsIgnoreCase("Thriller")) {
            return Genre.Thriller;
        } else if (genre.equalsIgnoreCase("Historical Fiction")) {
            return Genre.HistoricalFiction;
        } else if (genre.equalsIgnoreCase("Horror")) {
            return Genre.Horror;
        } else if (genre.equalsIgnoreCase("Biography")) {
            return Genre.Biography;
        } else if (genre.equalsIgnoreCase("Self-help")) {
            return Genre.Selfhelp;
        } else if (genre.equalsIgnoreCase("Romance")) {
            return Genre.Romance;
        } else if (genre.equalsIgnoreCase("Young Adult")) {
            return Genre.YoungAdult;
        }
        return null;
    }

    /**
     * hashes the title and returns the book from the books hashTable
     *
     * @param title
     * @return
     */
    public Book getBook(String title) {
        return books.get(calculateKey(title));
    }

    public Book[] getLatestBooks() {
        return latestList;
    }

    public void printBooks() {
        books.toString();
    }

}
