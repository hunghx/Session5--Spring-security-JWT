package ra.security.user_principal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.entity.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserPrincipal implements UserDetails {

    private  Long id;
    private String username;
    @JsonIgnore
    private  String password;
    private String email;
    private boolean status;

    private Collection<? extends GrantedAuthority> authorities;


    // phương thức chuyển đổi từ User thành user principle
    public  static UserPrincipal build(User user){
        List<GrantedAuthority> authorityList = user.getRoles().stream()
                .map(role->new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());

        return UserPrincipal.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .authorities(authorityList)
                .status(user.isStatus())
                .password(user.getPassword()).build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
