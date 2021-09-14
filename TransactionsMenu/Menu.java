import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private TransactionService transactionService;
    private boolean isDevMode;
    private boolean isWorking;

    public Menu(boolean isDevMode) {
        transactionService = new TransactionService();
        this.isDevMode = isDevMode;
        this.isWorking = true;
        mainLoop();
    }

    private void mainLoop() {
        Scanner in = new Scanner(System.in);
        while (isWorking) {
            show();
            if (!in.hasNextInt()) {
                System.out.println("Enter a valid number please.\n");
                in.nextLine();
                continue;
            }
            int chosenItem = in.nextInt();
            if (chosenItem < 1 || (chosenItem > 5 && !isDevMode) || (chosenItem > 7 && isDevMode)) {
                System.out.println("Enter a valid number please.\n");
                continue;
            }
            in.nextLine();
            handleChosenItem(chosenItem);
            System.out.println("---------------------------------------------------------");
        }
    }

    private void handleChosenItem(int chosenItem) {
        try {
            switch (chosenItem) {
                case 1:
                    addUser();
                    break;
                case 2:
                    viewUserBalance();
                    break;
                case 3:
                    performTransfer();
                    break;
                case 4:
                    viewUserTransfers();
                    break;
                case 5:
                    if (!isDevMode) {
                        isWorking = false;
                    } else {
                        removeTransfer();
                    }
                    break;
                case 6:
                    checkValidity();
                    break;
                case 7:
                    isWorking = false;
                    break;
            }
        } catch (RuntimeException e) {
            if (e.getMessage() == null)
                System.out.println("Error! Enter a valid data please.");
            else
                System.out.println(e.getMessage());
        }
    }

    private void addUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a user name and a balance");
        String name = in.next();
        int balance = in.nextInt();
        User user = new User(name, balance);
        transactionService.addUser(user);
        System.out.println("User with id = " + user.getId() + " is added");
    }

    private void viewUserBalance() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a user ID");
        int id = in.nextInt();
        User user = transactionService.getUser(id);
        System.out.println(user.getName() + " - " + user.getBalance());
    }

    private void performTransfer() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        int senderId = in.nextInt();
        int recipientId = in.nextInt();
        if (senderId == recipientId) {
            System.out.println("Sender can't be equal to recipient");
            return;
        }
        int transferAmount = in.nextInt();
        transactionService.performTransaction(recipientId, senderId, transferAmount);
        System.out.println("The transfer is completed");
    }

    private void viewUserTransfers() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a user ID");
        int id = in.nextInt();
        Transaction[] transactions = transactionService.getUserTransfers(id);
        for (int i = 0; i < transactions.length; ++i) {
            System.out.println("To " + transactions[i].getRecipient().getName()
                    + "(id = " + transactions[i].getRecipient().getId() + ") "
                    + transactions[i].getTransferAmount()
                    + " with id = "+ transactions[i].getId());
        }
    }

    private void removeTransfer() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a user ID and a transfer ID");
        String[] input = in.nextLine().split(" ");
        int userId = Integer.parseInt(input[0]);
        UUID transferId = UUID.fromString(input[1]);
        Transaction[] userTransfers = transactionService.getUserTransfers(userId);
        Transaction transaction = null;
        for (int i = 0; i < userTransfers.length; ++i) {
            if (userTransfers[i].getId().equals(transferId)) {
                transaction = userTransfers[i];
            }
        }
        transactionService.removeTransaction(transferId, userId);
        System.out.println("Transfer To " + transaction.getRecipient().getName()
                + "(id = " + transaction.getRecipient().getName() + ") "
                + transaction.getTransferAmount() + " removed");
    }

    private void checkValidity() {
        System.out.println("Check results:");
        Transaction[] transactions = transactionService.getUnpairedTransfers();
        for (int i = 0; i < transactions.length; ++i) {
            System.out.println(transactions[i].getRecipient().getName() + "(id = "
                    + transactions[i].getRecipient().getId() + ") has an unacknowledged transfer id = "
                    + transactions[i].getId() + " from "
                    + transactions[i].getSender().getName() + "(id = "
                    + transactions[i].getSender().getId() + ") for " + transactions[i].getTransferAmount());
        }
    }

    private void show() {
        if (isDevMode) {
            System.out.println("1. Add user" +
                    "\n2. View user balances" +
                    "\n3. Perform a transfer" +
                    "\n4. View all transactions for a specific user" +
                    "\n5. DEV - remove a transfer by ID" +
                    "\n6. DEV - check transfer validity" +
                    "\n7. Finish execution");
        } else {
            System.out.println("1. Add user" +
                    "\n2. View user balances" +
                    "\n3. Perform a transfer" +
                    "\n4. View all transactions for a specific user" +
                    "\n5. Finish execution");
        }
    }
}
