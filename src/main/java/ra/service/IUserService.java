package ra.service;

import ra.dto.request.SignUpRequest;
import ra.entity.User;

public interface IUserService extends IGenericService<User,Long> {
    User findUserByUserName(String username);
    boolean existsByUsername(String username);
    User signup(SignUpRequest signUpRequest);
}
