package spring.project.service;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.project.entity.User;
import spring.project.repository.UserRepository;

import java.util.List;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean doesUserExist(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        String role = user.getRole();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                List.of(new SimpleGrantedAuthority(role))
        );
    }
    public void updateAddUser(User user) { userRepository.save(user); }
    public User getUserById(Long id) { return userRepository.findById(id).orElse(null); }
    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    public List<User> getRegisteredUser() {
        return userRepository.getUser();
    }
}
