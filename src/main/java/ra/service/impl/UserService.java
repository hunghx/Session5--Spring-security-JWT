package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.dto.request.SignUpRequest;
import ra.entity.Role;
import ra.entity.RoleName;
import ra.entity.User;
import ra.repository.IRoleRepository;
import ra.repository.IUserRepository;
import ra.service.IRoleService;
import ra.service.IUserService;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class UserService implements IUserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
   @Autowired
    private IUserRepository userRepository;
   @Autowired
   private IRoleService roleService;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        // chưa triển khai
        return userRepository.save(user);
    }

    @Override
    public User signup(SignUpRequest signUpRequest) {
        // lấy ra danh sách quyền
        Set<Role> roles = new HashSet<>();
        if(signUpRequest.getRoles()== null|| signUpRequest.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        }else {
            signUpRequest.getRoles().stream().forEach(
                    (role)->{
                        switch (role){
                            case "admin":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                            case "pm":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_PM));
                            case "user":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                        }
                    }

            );
        }
        User user = User.builder().email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .username(signUpRequest.getUsername())
                .status(true)
                .roles(roles)
                .build();
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
