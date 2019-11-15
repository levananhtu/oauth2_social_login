package lvat.oauth2sociallogin.controller;

import lvat.oauth2sociallogin.exception.ResourceNotFoundException;
import lvat.oauth2sociallogin.model.User;
import lvat.oauth2sociallogin.repository.UserRepository;
import lvat.oauth2sociallogin.security.CurrentUser;
import lvat.oauth2sociallogin.security.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(name = "/user/me")
    @PreAuthorize(value = "hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository
                .findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
