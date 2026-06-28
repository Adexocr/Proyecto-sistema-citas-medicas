package tecnicotec.salud_cr.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tecnicotec.salud_cr.data.User;
import tecnicotec.salud_cr.security.JwtUtil;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public record LoginRequest(String username, String password) {}

    public record AuthResponse(String token, String tokenType, String expiresAt, List<String> roles) {}
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req, HttpServletResponse response) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username, req.password)
        );

        var user = (User) auth.getPrincipal();
        List<String> roles = auth.getAuthorities().stream()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toList());

        String token = jwtUtil.generateToken(user.getUsername(), roles);
        ResponseCookie cookie = ResponseCookie.from("JWT", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(Duration.ofMinutes(60))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        var claims = jwtUtil.validateAndParse(token).getBody();
        String expIso = Instant.ofEpochMilli(claims.getExpiration().getTime())
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_DATE_TIME);

        return ResponseEntity.ok(new AuthResponse(token, "Bearer", expIso, roles));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("JWT", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.noContent().build();
    }
}