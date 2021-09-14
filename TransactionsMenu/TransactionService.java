import java.util.UUID;

public class TransactionService {
    private UsersList userList;

    public TransactionService() {
        userList = new UsersArrayList();
    }

    public void addUser(User user) {
        userList.addUser(user);
    }

    public int getUserBalance(int id) {
        return userList.retrieveUserById(id).getBalance();
    }

    public void performTransaction(int recipientId, int senderId, int transferAmount) {
        User recipient = userList.retrieveUserById(recipientId);
        User sender = userList.retrieveUserById(senderId);

        Transaction debit = new Transaction(recipient, sender, Transaction.Category.DEBIT, transferAmount);
        Transaction credit = debit.duplicateWithOtherCategory();

        recipient.getTransactions().add(debit);
        recipient.debitTransaction(transferAmount);
        sender.getTransactions().add(credit);
        sender.creditTransaction(transferAmount);
    }

    public Transaction[] getUserTransfers(int id) {
        return userList.retrieveUserById(id).getTransactions().toArray();
    }

    public Transaction[] getUnpairedTransfers() {
        TransactionsLinkedList result = new TransactionsLinkedList();
        User[] users = userList.retrieveUsers();
        for (int i = 0; i < users.length; ++i) {
            if (users[i] == null) {
                break;
            }
            Transaction[] transactions = users[i].getTransactions().toArray();
            for (int j = 0; j < transactions.length; ++j) {
                User other = transactions[j].getRecipient();
                if (other == users[i])
                    other = transactions[j].getSender();
                Transaction[] otherTransactions = other.getTransactions().toArray();
                if (otherTransactions.length == 0) {
                    for (int k = 0; k < transactions.length; ++k) {
                        result.add(transactions[k]);
                    }
                }
                for (int k = 0; k < otherTransactions.length; ++k) {
                    if (transactions[j].getId().equals(otherTransactions[k].getId())) {
                        break;
                    }
                    if (k == otherTransactions.length - 1) {
                        result.add(transactions[j]);
                    }
                }
            }
        }
        return result.toArray();
    }

    public User getUser(int id) {
        return userList.retrieveUserById(id);
    }

    public void removeTransaction(UUID transactionId, int userId) {
        userList.retrieveUserById(userId).getTransactions().remove(transactionId);
    }
}
