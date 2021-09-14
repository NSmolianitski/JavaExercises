public interface UsersList {
    void addUser(User user);
    User retrieveUserById(int id);
    User retrieveUserByIndex(int index);
    User[] retrieveUsers();
}
