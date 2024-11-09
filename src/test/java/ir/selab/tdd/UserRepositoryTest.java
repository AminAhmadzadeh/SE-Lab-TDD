package ir.selab.tdd;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private UserRepository repository;

    @BeforeEach
    public void setUp() {
        List<User> userList = Arrays.asList(
                new User("admin", "1234", "admin@sharif.edu"),
                new User("ali", "qwert"),
                new User("mohammad", "123asd"));
        repository = new UserRepository(userList);
    }

    @Test
    public void getContainingUser__ShouldReturn() {
        User ali = repository.getUserByUsername("ali");
        assertNotNull(ali);
        assertEquals("ali", ali.getUsername());
        assertEquals("qwert", ali.getPassword());
    }

    @Test
    public void getNotContainingUser__ShouldReturnNull() {
        User user = repository.getUserByUsername("reza");
        assertNull(user);
    }

    @Test
    public void getContainingUserByEmail__ShouldReturn() {
        User ali = repository.getUserByEmail("admin@sharif.edu");
        assertNotNull(ali);
        assertEquals("admin", ali.getUsername());
        assertEquals("1234", ali.getPassword());
    }

    @Test
    public void getNotContainingUserByEmail__ShouldReturnNull() {
        User user = repository.getUserByUsername("reza@sharif.edu");
        assertNull(user);
    }

    @Test
    public void createRepositoryWithDuplicateUsers__ShouldThrowException() {
        User user1 = new User("ali", "1234");
        User user2 = new User("ali", "4567");
        assertThrows(IllegalArgumentException.class, () -> {
            new UserRepository(List.of(user1, user2));
        });
    }

    @Test
    public void createRepositoryWithDuplicateEmails__ShouldThrowException() {
        User user1 = new User("ali", "1234", "mail@sharif.edu");
        User user2 = new User("amin", "1234", "mail@sharif.edu");
        assertThrows(IllegalArgumentException.class, () -> {
            new UserRepository(List.of(user1, user2));
        });
    }

    @Test
    public void removeContainingUser__ShouldReturnTrue() {
        User user = new User("akbar", "1234", "akbar@sharif.edu");
        repository.addUser(user);
        boolean b = repository.removeUser("ali");
        assertTrue(b);
    }

    @Test
    public void removeNotContainingUser__ShouldReturnFalse() {
        boolean b = repository.removeUser("asqar");
        assertFalse(b);
    }

    @Test
    public void addNewUser__ShouldIncreaseUserCount() {
        int oldUserCount = repository.getUserCount();
        User newUser = new User("reza", "123abc");
        repository.addUser(newUser);
        assertEquals(oldUserCount + 1, repository.getUserCount());
    }

    @Test
    public void removeUser__ShouldDecreaseUserCount() {
        int oldUserCount = repository.getUserCount();
        repository.removeUser("mohammad");
        assertEquals(oldUserCount - 1, repository.getUserCount());
    }


    @Test
    public void getAllUsers__ShouldNotReturnNullAndEmpty() {
        List<User> allUsers = repository.getAllUsers();
        assertNotNull(allUsers);
        assertFalse(allUsers.isEmpty());
    }

    @Test
    public void changeUserEmailWithInvalidUsername__ShouldReturnFalse() {
        boolean b = repository.changeUserEmail("babak", "babak@sharif.edu");
        assertFalse(b);
    }
}
