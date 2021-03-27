package turntabl.io.client_connectivity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @ResponseBody
    public Map<String, Object> login(@RequestBody User user) throws ResponseStatusException {
        HashMap<String , Object> userDetails = new HashMap<>();
        if(userService.findIfUserExists(user.getEmail())){
            User db_user = userService.findUserByMail(user.getEmail());
            userDetails.put("email", db_user.getEmail());
            userDetails.put("firstname", db_user.getFirst_name());
            userDetails.put("date_created", db_user.getDate_created());
            userDetails.put("message", "login successful");
            userDetails.put("id",db_user.getId());
            userDetails.put("portfolios", db_user.getPortfolio());
            userDetails.put("code", HttpStatus.OK.value());
            return userDetails;
        } else {
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User credentials invalid");
        }
    }


//    @RequestMapping("/user")
//    public Principal user(HttpServletRequest request) {
//        String authToken = request.getHeader("Authorization")
//                .substring("Basic".length()).trim();
//        return () ->  new String(Base64.getDecoder()
//                .decode(authToken)).split(":")[0];
//    }
}
