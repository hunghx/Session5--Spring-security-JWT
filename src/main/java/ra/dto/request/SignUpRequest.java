package ra.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Builder
public class SignUpRequest {
    private String username;
    private  String password;
    private String email;

    private List<String> roles;
}
