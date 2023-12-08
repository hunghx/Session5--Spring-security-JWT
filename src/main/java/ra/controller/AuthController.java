package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.dto.request.SignInRequest;
import ra.dto.request.SignUpRequest;
import ra.dto.response.UserResponse;
import ra.exception.UserException;
import ra.security.jwt.JwtProvider;
import ra.security.user_principal.UserPrincipal;
import ra.service.IUserService;
import ra.service.impl.MailService;
import ra.service.impl.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/public")
public class AuthController {
    @Autowired
    private MailService mailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IUserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private JwtProvider jwtProvider;
    @GetMapping("/avg")
    public ResponseEntity<Double> getAvgProductPrice(){
        return new ResponseEntity<>(productService.getAvgProductPrice(), HttpStatus.OK);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> doSignUp(@RequestBody SignUpRequest signUpRequest) throws UserException {
        if (userService.existsByUsername(signUpRequest.getUsername())){
            throw  new UserException("Username is exists");
        }
        return  new ResponseEntity<>(userService.signup(signUpRequest),HttpStatus.CREATED);
    }
    @PostMapping("/sign-in")
    public ResponseEntity<?> doSignIn(@RequestBody SignInRequest signInRequest) throws UserException {
        Authentication authentication= null;
        try {
            authentication= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword()));
        }catch (AuthenticationException e){
            throw  new UserException("Tên đăng nhâp và mật khẩu không đúng");
        }
        // lấy ra userprincipal sau khi xác thực
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // tạo token
        String token = jwtProvider.generateToken(userPrincipal);
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        UserResponse userResponse =UserResponse.builder()
                .email(userPrincipal.getEmail())
                .username(userPrincipal.getUsername())
                .roles(roles)
                .status(userPrincipal.isStatus())
                .token(token)
                .build();
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }
    @PostMapping("/send-mail")
    public ResponseEntity<?> sendMail(@RequestParam String subject,@RequestParam String html,@RequestParam String to,@RequestParam List<MultipartFile> list){
        mailService.sendMail(to,subject,html,list);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
