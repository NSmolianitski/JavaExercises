import java.util.UUID;

public interface TransactionsList {
    void add(Transaction transaction);
    void remove(UUID id);
    Transaction[] toArray();
}
