package ra.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ra.entity.Role;


import java.util.List;

@Setter
@Getter
@Builder
public class UserResponse {
    private String token;
    private final String type ="Bearer ";

    private String username;

    private String email;
    private boolean status;

    private List<String> roles;
}
