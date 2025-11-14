package fr.epsi.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.epsi.spring.model.User;
import fr.epsi.spring.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private final UserRepository userRepository;

    public UserService( UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    
    @Transactional
    public Optional<User> findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User save(User user){
        if(user.getId() != null){
            Optional<User> existing = userRepository.findById(user.getId());
            User toUpdate = existing.get();
            toUpdate.setUsername(user.getUsername());
            toUpdate.setPassword(user.getPassword());
            toUpdate.setRole(user.getRole());
            return userRepository.save(toUpdate);
        }else{
            return userRepository.save(user);
        }
    }

    @Transactional
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }


}
