package ir.selab.tdd.repository;

import ir.selab.tdd.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserRepository {
    private final Map<String, User> usersByUserName;
    private final Map<String, User> usersByEmail;

    public UserRepository(List<User> users) {
        this.usersByUserName = users.stream().collect(Collectors
                .toMap(User::getUsername, u -> u, (u1, u2) -> {
                    throw new IllegalArgumentException("Two users can not have the same username");
        }));
        this.usersByEmail = users.stream()
                .filter(user -> Objects.nonNull(user.getEmail())).collect(Collectors
                        .toMap(User::getEmail, u -> u, (u1, u2) -> {
                            throw new IllegalArgumentException("Two users can not have the same email");
                        }));
    }

    public User getUserByUsername(String username) {
        return usersByUserName.get(username);
    }

    public User getUserByEmail(String email) {
        return usersByEmail.get(email);
    }

    public boolean addUser(User user) {
        if (usersByUserName.containsKey(user.getUsername())) return false;
        if (usersByEmail.containsKey(user.getEmail())) return false;

        usersByUserName.put(user.getUsername(), user);
        if (Objects.nonNull(user.getEmail()))
            usersByEmail.put(user.getEmail(), user);
        return true;
    }

    public boolean removeUser(String username) {
        return Objects.nonNull(usersByUserName.remove(username));
    }

    public int getUserCount() {
        return usersByUserName.size();
    }

    public List<User> getAllUsers() {
        return usersByUserName.values().stream().toList();
    }
}
