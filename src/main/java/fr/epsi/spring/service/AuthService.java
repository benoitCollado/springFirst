package fr.epsi.spring.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import fr.epsi.spring.repository.UserRepository;
import fr.epsi.spring.model.User;

@Service
public class AuthService {
  private final UserRepository userRepository;
  
  public AuthService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  public User register(String username, String password, String role){
    String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
    User user = new User();
    user.setUsername(username);
    user.setPassword(hashed);
    user.setRole(role);
    return this.userRepository.save(user);
  }

  public boolean checkPassword(String RawPassword, String hashed){
    return BCrypt.checkpw(RawPassword, hashed);
  }
}
