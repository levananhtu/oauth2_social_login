package lvat.oauth2sociallogin.security;

import lvat.oauth2sociallogin.exception.ResourceNotFoundException;
import lvat.oauth2sociallogin.model.User;
import lvat.oauth2sociallogin.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + s));
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserPrincipal.create(user);
    }
}
