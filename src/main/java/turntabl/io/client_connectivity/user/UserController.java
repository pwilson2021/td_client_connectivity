package turntabl.io.client_connectivity.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired
    private RedisTemplate template;
    @Autowired
    private ChannelTopic topic;
    ObjectMapper mapper = new ObjectMapper();

    private ReportingModel report;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public List<User> getUser() { return userService.getUsers(); }

    @PostMapping
    public String registerNewUser(@RequestBody User user) throws JsonProcessingException {
        userService.addNewUser(user);
        String report = "new user registered"+user.toString();
        template.convertAndSend(topic.getTopic(), mapper.writeValueAsString(report));
        return "User registration successful";
    }

//    @DeleteMapping(path = "{userId}")
//    public void deleteUser(@PathVariable("userId") Integer userId) {
//        userService.deleteUser(userId);
//        report.setTitle("client connectivity: User");
//        report.setMsg("User deleted: "+userId);
//        template.convertAndSend(topic.getTopic(), report);
//    }

    @PutMapping(path={"userId"})
    public String updateUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String email
    ) {
        userService.updateUser(userId, firstname, lastname, email);
        String report = "order with id "+userId +" updated registered";
        template.convertAndSend(topic.getTopic(), report);
        return "User details updated";}
}
