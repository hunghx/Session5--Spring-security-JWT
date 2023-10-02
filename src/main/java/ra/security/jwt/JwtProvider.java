package ra.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ra.security.user_principal.UserPrincipal;

import java.util.Date;

@Component
public class JwtProvider {
    private final Logger logger  = LoggerFactory.getLogger(JwtProvider.class);
   // có những loại log sau

    // chuỗi khóa bí mật (secret - key )
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expired}")
    private long EXPIRED;

    // chưas3 phưuong thức làm việc với token
    // genarate token
    public String generateToken(UserPrincipal userPrincipal){
        return Jwts.builder().setSubject(userPrincipal.getUsername()) // mã hóa username
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+EXPIRED*1000))// mili giây
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }

    // xác thực token
    public boolean validateToken(String token){
       try {
           Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
           return true;
       }catch (ExpiredJwtException e){
           logger.error("Message ->>> Expired token error",e.getMessage());
       }catch (UnsupportedJwtException e){
           logger.error("Message ->>> Unsupport jwt token error",e.getMessage());
       }catch (MalformedJwtException e){
           logger.error("Message ->>> Invalid format token error",e.getMessage());
       }catch (SignatureException e){
           logger.error("Message ->>> Signature is not match",e.getMessage());
       }catch (IllegalArgumentException e){
           logger.error("Message ->>> Claims is empty error",e.getMessage());
       }
       return false;
    }

    // get username from token token
    public  String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token)
                .getBody().getSubject();
    }
}
