package fr.esgi.jee.api.users.domain;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.esgi.jee.api.authentication.domain.AuthenticationService;
import fr.esgi.jee.api.authentication.domain.login.Role;
import fr.esgi.jee.api.authentication.domain.login.RoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder bCryptEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuthenticationService authenticationService, PasswordEncoder bCryptEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationService = authenticationService;
        this.bCryptEncoder = bCryptEncoder;
    }

    @Override
    public User addUser(User user) {
        String role = user.getRoles().iterator().next().getRole();
        Role userRole = roleRepository.findByRole(role);
        return userRepository.save(
                User.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .password(bCryptEncoder.encode(user.getPassword()))
                        .phoneNumber(user.getPhoneNumber())
                        .createDate(new Date())
                        .closeDate(null)
                        .roles(new HashSet<>(Arrays.asList(userRole)))
                        .partners(new HashSet<>(new ArrayList<>()))
                        .build()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User addRole(User user, String newRole){
        User dbUser = findUserById(user.getId());
        var roles = new HashSet<>(Arrays.asList(roleRepository.findByRole(newRole)));
        dbUser.setRoles(roles);
            return userRepository.save(dbUser);
    }

    public User removeRole(User user){
        User dbUser = findUserById(user.getId());
        dbUser.setRoles(new HashSet<>(Arrays.asList()));
        return userRepository.save(dbUser);
    }

    public void delete(String id){
        User user = User.builder()
                        .id(id)
                        .build();
        userRepository.delete(user);
    }

    public User getUserFromToken(String jwtToken){
        String[] chunks = jwtToken.split(" ")[1].split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        JsonObject json = new Gson().fromJson(payload, JsonObject.class);

        return findUserById(json.get("id").getAsString());
    }
}
