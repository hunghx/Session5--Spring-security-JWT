package ra.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SignInRequest {
    private  String username;
    private  String password;
}
