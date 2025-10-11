package com.userService.UserService.service;

import com.userService.UserService.dto.AuthRequestDTO;
import com.userService.UserService.dto.AuthResponseDTO;
import com.userService.UserService.dto.UserRequestDTO;
import com.userService.UserService.dto.UserResponseDTO;
import com.userService.UserService.model.User;
import com.userService.UserService.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class UserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.secret}")
    private String secretKeyString;
    public UserResponseDTO registerUser(UserRequestDTO userDTO){

        Optional<User> find_User=userRepository.findByEmail(userDTO.getEmail());
        System.out.println(userDTO.getEmail());
        if(find_User.isPresent()){
            throw new RuntimeException("User is already Present");
        }
        User user=new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setNoOfRides(0L);
        userRepository.save(user);
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setNoOfTrips(user.getNoOfRides());
        return userResponseDTO;
    }


    public AuthResponseDTO login(AuthRequestDTO userDTO){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        }
        UserDetails userDetails=userDetailsService.loadUserByUsername(userDTO.getEmail());
        Optional<User>user=userRepository.findByEmail(userDetails.getUsername());
        final String token=jwtService.generateToken(user.get());
        System.out.println(user.get());
        String role = Jwts.parserBuilder()
                .setSigningKey(jwtService.getSecretKey())  // you may need a getter in JwtService
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);


        System.out.println("Logged-in user role: " + role);
        return new  AuthResponseDTO(token);
    }
}
