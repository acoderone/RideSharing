package com.Driver.DriverService.service;

import com.Driver.DriverService.dto.*;
import com.Driver.DriverService.model.Rider;
import com.Driver.DriverService.repository.RiderRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RiderService {

    @Autowired
    private RiderLocationService riderLocationService;
    @Autowired
    private RiderRepository riderRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKeyString;

    public UserResponseDTO register(UserRequestDTO dto){
        Optional<Rider>find_Rider=riderRepository.findByEmail(dto.getEmail());
        if(find_Rider.isPresent()){
            throw new RuntimeException("User is already present");
        }
        Rider rider=new Rider();
        rider.setEmail(dto.getEmail());
        rider.setLicense(dto.getLicense());
        rider.setPassword(passwordEncoder.encode(dto.getPassword()));
        rider.setName(dto.getName());
        riderRepository.save(rider);
        UserResponseDTO userDto=new UserResponseDTO();
        userDto.setName(rider.getName());
        userDto.setStatus(rider.getStatus());
        return userDto;
    }

    public AuthResponseDTO login(AuthRequestDTO dto){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword()));
        }
        catch(BadCredentialsException e){
            throw new RuntimeException("Invalid username or password");
        }

        UserDetails userDetails=userDetailsService.loadUserByUsername(dto.getEmail());
        Optional<Rider> user=riderRepository.findByEmail(dto.getEmail());
        final String token=jwtService.generateToken(user.get());
        String role= Jwts.parserBuilder()
                .setSigningKey(jwtService.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
        System.out.println(role);
        riderLocationService.addDriverTemplate(String.valueOf(user.get().getId()),dto.getLongitude(),dto.getLatitude(),"Available");
        return new AuthResponseDTO(token);
    }

}
