package turntabl.io.client_connectivity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public List<User> getUser() { return userService.getUsers(); }

    @PostMapping
    public void registerNewUser(@RequestBody User user) { userService.addNewUser(user); }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) { userService.deleteUser(userId); }

    public void updateUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String email
    ) { userService.updateUser(userId, firstname, lastname, email);}
}
