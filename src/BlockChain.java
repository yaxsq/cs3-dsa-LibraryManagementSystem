import java.util.ArrayList;

class BlockChain {
    private static BlockChain chain = null;
    private ArrayList<Block> blockChain = new ArrayList<Block>();
    private Library library;


    private BlockChain() {
        library = Library.getInstance();
    }

    public static BlockChain getChain() {
        if (chain == null)
            chain = new BlockChain();

        return chain;
    }

    public void addBorrowTransaction(Customer customer, String title) {
        // Need to implement a GetCustomer function in library which checks if the customer really does exist
       /* Customer searchCustomer = library.getCustomer(book.getCustomerCode());
        if (searchCustomer == null) {
            System.out.println("Customer not found");
            return;
        }*/
        Book searchBook = library.getBook(title);
        if (searchBook == null) {
            System.out.println("Book not found");
            return;
        }
        if (blockChain.size() == 0) {
            //Stores the value of the first transaction in the arrayList, the first transaction has previous blockHash 0
            blockChain.add(new Block(customer.getName() + " has borrowed the book " + searchBook.getTitle() + " by " + searchBook.getAuthor() + " on time " + System.currentTimeMillis(), 0));
            //This adds the borrowedBook in the customer's ArrayList
            customer.addBorrowedBook(searchBook);
            return;
        }
        blockChain.add(new Block(customer.getName() + " has borrowed the book " + searchBook.getTitle() + " by " + searchBook.getAuthor() + " on time " + System.currentTimeMillis(), blockChain.size() - 1));

        searchBook.borrowBook(customer);
        searchBook.printTransactions();
    }

    public void addReturnTransaction(Customer customer, String title) {
        // Need to implement a GetCustomer function in library which checks if the customer really does exist
       /* Customer searchCustomer = library.getCustomer(book.getCustomerCode());
        if (searchCustomer == null) {
            System.out.println("Customer not found");
            return;
        }*/
        Book searchBook = library.getBook(title);
        if (!customer.getBorrowedBooks().contains(searchBook)) {
            System.out.println("You don't have that book");
            return;
        }
        blockChain.add(new Block(customer.getName() + " has returned the book " + searchBook.getTitle() + " by " + searchBook.getAuthor() + " on time " + System.currentTimeMillis(), blockChain.size() - 1));

        searchBook.returnBook(customer);
        ArrayList<Book> borrowedBooks = customer.getBorrowedBooks();
        for (int i = 0; i < borrowedBooks.size(); i++) {
            if (borrowedBooks.get(i) == searchBook) {
                borrowedBooks.remove(i);
            }
        }
    }
}
