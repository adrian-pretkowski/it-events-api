package pl.patrykdepka.iteventsapi.security;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestBody LoginData loginData) {
        System.out.println("Test");
        return null;
    }
}
