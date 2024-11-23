package controller;

import dto.AuthenticationDTO;
import dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;
import service.TokenService;



@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        Authentication authenticate = authenticationManager.authenticate(credentials);

        String token = tokenService.generateToken((User) authenticate.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){

        if(userRepository.findByLogin(registerDTO.login()) != null){
            return ResponseEntity.badRequest().build();
        }
        User user =  new User();
        user.setLogin(registerDTO.login());
        user.setPassword(new BCryptPasswordEncoder().encode(registerDTO.password()));
        user.setRole(registerDTO.role());

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

}
