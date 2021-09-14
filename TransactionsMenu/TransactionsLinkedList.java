import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Transaction begin;
    private Transaction end;
    private int length;

    public TransactionsLinkedList() {
        length = 0;
    }

    private static class TransactionNotFoundException extends RuntimeException {
        public TransactionNotFoundException() {
            super("Transaction not found.");
        }
    }

    public void add(Transaction transaction) {
        transaction = transaction.clone();
        if (begin == null) {
            begin = transaction;
            end = transaction;
        } else {
            end.setNext(transaction);
            transaction.setPrev(end);
            end = transaction;
        }
        ++length;
    }

    public void remove(UUID id) {
        Transaction tmp = begin;
        while (tmp != null) {
            if (tmp.getId().equals(id)) {
                if (tmp.getPrev() != null) {
                    tmp.getPrev().setNext(tmp.getNext());
                } else {
                    begin = tmp.getNext();
                }
                if (tmp.getNext() != null) {
                    tmp.getNext().setPrev(tmp.getPrev());
                } else {
                    end = tmp.getPrev();
                }
                --length;
                return;
            }
            tmp = tmp.getNext();
        }
        throw new TransactionNotFoundException();
    }

    public Transaction[] toArray() {
        Transaction[] array = new Transaction[length];

        int i = 0;
        Transaction tmp = begin;
        while (tmp != null) {
            array[i] = tmp;
            tmp = tmp.getNext();
            ++i;
        }
        return array;
    }
}
