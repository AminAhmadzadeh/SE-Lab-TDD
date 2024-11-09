package ir.selab.tdd;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import ir.selab.tdd.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    public void setUp() {
        UserRepository userRepository = new UserRepository(List.of());
        userService = new UserService(userRepository);
        userService.registerUser("admin", "1234");
        userService.registerUser("ali", "qwert", "ali@sharif.edu");
        userService.registerUser("amin", "qwert", "amin@sharif.edu");
    }

    @Test
    public void createNewValidUser__ShouldSuccess() {
        String username = "reza";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertTrue(b);
    }

    @Test
    public void createNewDuplicateUser__ShouldFail() {
        String username = "ali";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertFalse(b);
    }

    @Test
    public void createNewValidUserWithEmail__ShouldSuccess() {
        String username = "reza";
        String password = "123abc";
        String email = "reza@sharif.edu";
        boolean b = userService.registerUser(username, password, email);
        assertTrue(b);
    }

    @Test
    public void createNewDuplicateUserWithEmail__ShouldFail() {
        String username = "mohammad";
        String password = "qwert";
        String email = "amin@sharif.edu";
        boolean b = userService.registerUser(username, password, email);
        assertFalse(b);
    }

    @Test
    public void loginWithValidUsernameAndPassword__ShouldSuccess() {
        boolean login = userService.loginWithUsername("admin", "1234");
        assertTrue(login);
    }

    @Test
    public void loginWithValidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("admin", "abcd");
        assertFalse(login);
    }

    @Test
    public void loginWithInvalidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("ahmad", "abcd");
        assertFalse(login);
    }

    @Test
    public void loginWithValidEmailAndPassword__ShouldSuccess() {
        boolean login = userService.loginWithEmail("amin@sharif.edu", "qwert");
        assertTrue(login);
    }

    @Test
    public void loginWithValidEmailAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithEmail("amin@sharif.edu", "abcd");
        assertFalse(login);
    }
    
    @Test
    public void loginWithInvalidEmailAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithEmail("ahmad@sharif.edu", "abcd");
        assertFalse(login);
    }

    @Test
    public void changeUserEmailWithValidUsernameAndValidEmail_ShouldSuccess() {
        boolean changed = userService.changeUserEmail("amin", "amin@gmail.com");
        assertTrue(changed);
        boolean login = userService.loginWithEmail("amin@gmail.com", "qwert");
        assertTrue(login);
    }

    @Test
    public void changeUserEmailWithInvalidUsername_ShouldFail() {
        boolean changed = userService.changeUserEmail("akbar", "akabr@gmail.com");
        assertFalse(changed);
    }

    @Test
    public void changeUserEmailWithDuplicateEmail_ShouldFail() {
        boolean changed = userService.changeUserEmail("amin", "ali@sharif.edu");
        assertFalse(changed);
    }

    @Test
    public void removeValidUser__ShouldSuccess() {
        String username = "mohammad";
        String password = "123abc";
        boolean registered = userService.registerUser(username, password);
        assertTrue(registered);
        boolean removed = userService.removeUser(username);
        assertTrue(removed);
    }

    @Test
    public void getAllUsers__ShouldNotReturnNullAndEmpty() {
        List<User> allUsers = userService.getAllUsers();
        assertNotNull(allUsers);
        assertFalse(allUsers.isEmpty());
    }
}
