import java.util.ArrayList;
import java.util.Objects;

class BlockChain {
    private static BlockChain chain = null;
    private ArrayList<Block> blockChain = new ArrayList<Block>();
    private Library library;


    private BlockChain() {
        library = Library.getInstance();
    }

    public ArrayList<Block> getBlockChain() {
        return blockChain;
    }

    public static BlockChain getChain() {
        if (chain == null)
            chain = new BlockChain();

        return chain;
    }

    public void addBorrowTransaction(Customer customer, Book book) {
        // Need to implement a GetCustomer function in library which checks if the customer really does exist
       /* Customer searchCustomer = library.getCustomer(book.getCustomerCode());
        if (searchCustomer == null) {
            System.out.println("Customer not found");
            return;
        }*/
        Book searchBook = book;
        if (searchBook == null) {
            System.out.println("Book not found");
            return;
        }
        if(searchBook.isBorrowed()){
            return;
        }
        if (blockChain.size() == 0) {
            //Stores the value of the first transaction in the arrayList, the first transaction has previous blockHash 0
            blockChain.add(new Block(customer.getName() + " has borrowed the book " + searchBook.getTitle() + " by " + searchBook.getAuthor() + " on time " + System.currentTimeMillis(), 0));
            //This adds the borrowedBook in the customer's ArrayList
            customer.addBorrowedBook(searchBook);
            book.setBorrowed(true);
            return;
        }

        blockChain.add(new Block(customer.getName() + " has borrowed the book " + searchBook.getTitle() + " by " + searchBook.getAuthor() + " on time " + System.currentTimeMillis(), blockChain.get(blockChain.size() - 1).getHash()));

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
        blockChain.add(new Block(customer.getName() + " has returned the book " + searchBook.getTitle() + " by " + searchBook.getAuthor() + " on time " + System.currentTimeMillis(), blockChain.get(blockChain.size() - 1).getHash()));

        searchBook.returnBook(customer);
        ArrayList<Book> borrowedBooks = customer.getBorrowedBooks();
        for (int i = 0; i < borrowedBooks.size(); i++) {
            if (borrowedBooks.get(i) == searchBook) {
                borrowedBooks.remove(i);
            }
        }
    }
    public String isChainValid() {
        for (int i = 1; i < blockChain.size(); i++) {
            Block currentBlock = blockChain.get(i);
            Block previousBlock = blockChain.get(i - 1);

            // Check if the stored hash matches the calculated hash
            if (currentBlock.getHash() != currentBlock.calculateHash()) {
                return "Block #" + i + " has been tampered with.";
            }

            // Check if the previous block's hash matches the stored previous hash
            if (currentBlock.getPreviousBlockHash() != previousBlock.getHash()) {
                return "Block #" + i + " has an invalid previous hash.";
            }
        }

        return "Blockchain is valid.";
    }


    public Library getLibrary() {
        return library;
    }
}
