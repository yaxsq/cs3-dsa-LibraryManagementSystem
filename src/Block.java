import java.util.Arrays;

public class Block {
    private String transactions;
    private int blockHash;
    private int previousBlockHash;

    public Block(String transactions, int previousHashCode) {
        this.transactions = transactions;
        this.previousBlockHash = previousHashCode;
        this.blockHash = Arrays.hashCode(new int[]{transactions.hashCode(), this.previousBlockHash});
    }

    public String getTransactions() {
        return transactions;
    }

    public int getBlockHash() {
        return blockHash;
    }

    public int getPreviousBlockHash() {
        return previousBlockHash;
    }

}
