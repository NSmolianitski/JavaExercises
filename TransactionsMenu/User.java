public class User {
    private int id;
    private String name;
    private int balance;
    private TransactionsLinkedList transactions;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public TransactionsLinkedList getTransactions() {
        return transactions;
    }

    public void debitTransaction(int transferAmount) {
        balance += transferAmount;
    }

    public void creditTransaction(int transferAmount) {
        if (transferAmount > balance)
            throw new IllegalTransactionException();
        balance -= transferAmount;
    }

    private static class IllegalTransactionException extends RuntimeException {
        public IllegalTransactionException() {
            super("Illegal transaction.");
        }
    }

    public User(String name, int balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        if (balance < 0)
            balance = 0;
        this.balance = balance;
        transactions = new TransactionsLinkedList();
    }
}
