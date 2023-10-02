package ra.security.jwt;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import ra.security.user_principal.UserDetailService;
import ra.security.user_principal.UserPrincipal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final Logger logger  = LoggerFactory.getLogger(JwtProvider.class);
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // lấy ra token
            String token =getTokenFromRequest(request);
            if(token!=null && jwtProvider.validateToken(token)){
                // đã được xác thực
                String username = jwtProvider.getUsernameFromToken(token);

                UserDetails userPrincipal = userDetailService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal,null,userPrincipal.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }catch (Exception e){
            logger.error("Un Authentication ->>>",e.getMessage());
        }

        // gửi request tiêps tục đến controller
        filterChain.doFilter(request,response);
    }
    // lấy token từ request

    public String getTokenFromRequest(HttpServletRequest httpServletRequest){
        // lấy ra header
        String header = httpServletRequest.getHeader("Authorization");
        if (header!=null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
