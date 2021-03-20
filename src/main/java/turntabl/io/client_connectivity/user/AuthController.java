package turntabl.io.client_connectivity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) { this.userService = userService; }

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        HashMap<String , Object> userDetails = new HashMap<>();
        if(userService.findIfUserExists(user.getEmail())){
            User db_user = userService.findUserByMail(user.getEmail());
            userDetails.put("email", db_user.getEmail());
            userDetails.put("firstname", db_user.getFirst_name());
            userDetails.put("date_created", db_user.getDate_created());
            userDetails.put("id",db_user.getId());
            return userDetails;
        } else {
            userDetails.put("message","User Credentials are invalid");
            return userDetails;
        }
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }
}
