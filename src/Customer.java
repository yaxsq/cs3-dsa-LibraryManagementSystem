import java.util.ArrayList;

public class Customer {
    private String name;
    private int ID;
    private ArrayList<Book> borrowedBooks = new ArrayList<Book>();
    // Why are we creating another Library in Customers?
 //   private Library library = new Library();

    public Customer(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }
    

    public void addBook(Book book) {
        borrowedBooks.add(book);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }


}
