public class UsersArrayList implements UsersList {
    private User[] users;
    private int usersSize;
    private int usersNumber;

    public UsersArrayList() {
        usersSize = 10;
        usersNumber = 0;
        users = new User[usersSize];
    }

    private static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException() {
            super("User not found.");
        }
    }

    public void addUser(User user) {
        users[usersNumber] = user;
        if ((usersNumber + 1) == usersSize) {
            usersSize += (usersSize / 2);
            User[] newUsers = new User[usersSize];
            for (int i = 0; i < users.length; ++i) {
                newUsers[i] = users[i];
            }
            users = newUsers;
        }
        ++usersNumber;
    }

    public User retrieveUserById(int id) throws UserNotFoundException {
        for (int i = 0; i < users.length; ++i) {
            if (users[i] != null && users[i].getId() == id)
                return users[i];
        }
        throw new UserNotFoundException();
    }

    public User retrieveUserByIndex(int index) {
        return users[index];
    }

    public User[] retrieveUsers() {
        return users;
    }
}
