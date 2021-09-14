import java.util.UUID;

public class Transaction {

    public enum Category {
        DEBIT,
        CREDIT
    }

    private UUID id;
    private User recipient;
    private User sender;
    private Category transferCategory;
    private int transferAmount;
    private Transaction next;
    private Transaction prev;

    public Transaction(User recipient, User sender, Category transferCategory, int transferAmount) {
        this.id = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        if (transferCategory == Category.DEBIT && transferAmount < 0)
            transferAmount = 0;
        else if (transferCategory == Category.CREDIT && transferAmount > 0)
            transferAmount = 0;
        this.transferAmount = transferAmount;
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getTransferCategory() {
        return transferCategory;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public Transaction getNext() { return next; }

    public void setNext(Transaction transaction) { next = transaction; }

    public Transaction getPrev() { return prev; }

    public void setPrev(Transaction transaction) { prev = transaction; }

    public Transaction clone() {
        Transaction copy = new Transaction(this.recipient, this.sender, this.transferCategory, this.transferAmount);
        copy.id = this.id;
        return copy;
    }

    public Transaction duplicateWithOtherCategory() {
        Category category = Category.DEBIT;
        if (this.transferCategory == Category.DEBIT)
            category = Category.CREDIT;
        Transaction copy = new Transaction(this.recipient, this.sender, category, -this.transferAmount);
        copy.id = this.id;
        return copy;
    }
}
