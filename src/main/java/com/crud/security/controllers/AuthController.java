// AuthController.java
package com.crud.security.controllers;

import com.crud.entities.Ranking;
import com.crud.security.dtos.AuthRequestDTO;
import com.crud.security.dtos.AuthResponseDTO;
import com.crud.security.services.CustomUserDetailsService;
import com.crud.security.util.JwtUtil;
import com.crud.services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Collections; // Add this import for Collections.singletonMap
import java.util.HashSet; // Needed for new Set<Role>()
import java.util.Set;
import java.util.stream.Collectors;

// ADD IMPORTS FOR USER AND ROLE ENTITIES, AND SERVICES
import com.crud.security.entities.User; // Import your User entity
import com.crud.security.entities.Role; // You'll need a Role entity or enum
import com.crud.security.services.UserService; // Import your UserService
import com.crud.security.repositories.RoleRepository; // Assuming you'll have a RoleRepository
import org.springframework.security.crypto.password.PasswordEncoder; // Needed for password encoding

//@CrossOrigin(origins = "${ip.frontend}")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
//@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api") // Keep this as /api for now, or change to /auth if you prefer
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    // ADD NEW DEPENDENCIES FOR REGISTRATION
    private final UserService userService; // Inject UserService
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder
    private final RoleRepository roleRepository; // You'll need a RoleRepository to get default role
    @Autowired
    private RankingService rankingService; // Injected RankingService

    // Update constructor to include new dependencies
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            CustomUserDetailsService userDetailsService,
            UserService userService,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository // Add RoleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository; // Initialize RoleRepository
    }

    @PostMapping("/authenticate") // Existing login endpoint
    public ResponseEntity<AuthResponseDTO> createAuthenticationToken(@RequestBody AuthRequestDTO authRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = jwtUtil.generateToken(userDetails);

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        User authenticatedUser = userService.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado después de la autenticación."));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setRoles(roles);
        authResponseDTO.setJwt(token);
        authResponseDTO.setUserId(authenticatedUser.getId());       // <-- ¡Añade esta línea!
        authResponseDTO.setUsername(authenticatedUser.getUsername());

        System.out.println("Roles enviados en AuthResponseDTO: " + authResponseDTO.getRoles()); // <--- AÑADE ESTA LÍNEA

        return ResponseEntity.ok().headers(responseHeaders).body(authResponseDTO);
    }

    // *** NEW REGISTRATION ENDPOINT ***
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) { // Changed return type to ResponseEntity<?>
        // 1. Check if username already exists
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            // Return a JSON error message
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Error: Username is already taken!"));
        }

        // 2. Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3. Assign default role (e.g., 'USER')
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);

        // 4. Save the user
        userService.save(user);

        // 5. Initialize ranking for the new user with 0 points
        Ranking newRanking = new Ranking();
        newRanking.setUser(user);
        newRanking.setPuntos(0);
        rankingService.gravarRanking(newRanking);

        // Return a JSON success message
        return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully!"));
    }
}