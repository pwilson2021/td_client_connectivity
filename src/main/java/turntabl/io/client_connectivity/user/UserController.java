package turntabl.io.client_connectivity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.*;
import turntabl.io.client_connectivity.reporting.ReportingModel;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;
    private RedisTemplate template;
    private ChannelTopic topic;

    private ReportingModel report;

    @Autowired
    public UserController(UserService userService, RedisTemplate redisTemplate) { this.userService = userService; this.template = redisTemplate; }

    @GetMapping
    public List<User> getUser() { return userService.getUsers(); }

    @PostMapping
    public String registerNewUser(@RequestBody User user) {
        userService.addNewUser(user);
        report.setTitle("client connectivity: User");
        report.setMsg("New user registered");
        template.convertAndSend(topic.getTopic(), report);
        userService.addNewUser(user);
        return "User registration succesful";
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        report.setTitle("client connectivity: User");
        report.setMsg("User deleted: "+userId);
        template.convertAndSend(topic.getTopic(), report);
    }

    public void updateUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String email
    ) { userService.updateUser(userId, firstname, lastname, email);}
}
