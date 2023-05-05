package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminService {
@PersistenceContext
   private EntityManager entityManager;

@Autowired
 RoleRepository roleRepository;
@Autowired
 UserRepository userRepository;

//    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    public void setUserRepository(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.userRepository = userRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null){
//            throw new UsernameNotFoundException("User not found");
//        }
//        UserDetails userDetails = org.springframework.security.core.userdetails.User
//                .builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(user.getRole())
//                .build();
//        return userDetails;
//    }
    public User getUserById(long id){
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElse(new User());
    }

    public User getUserByName(String name){
        return userRepository.findByUsername(name);
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }


@Transactional
    public void addUser(User user){

        user.setRole(user.getRole());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setUsername(user.getUsername());
userRepository.save(user);

    }
    @Transactional

    public void deleteUser (long userId){
        if(userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
        }

    }
    @Transactional
    public User updateUser(long id,User update){
User toBeUpdate =getUserById(id);
toBeUpdate.setPassword(update.getPassword());
toBeUpdate.setUsername(update.getUsername());
toBeUpdate.setEmail(update.getEmail());
toBeUpdate.setRole(update.getRole());
return entityManager.merge(toBeUpdate);
    }
}
