package com.exam.services.implemetation;

import com.exam.models.User;
import com.exam.models.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override

    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
         User local=this.userRepository.findByUserName(user.getUserName());
         if(local!=null){
             System.out.println("User Already  Exist");
             throw  new Exception("User Already present");
         }else
         {

             for(UserRole ur:userRoles){
                 roleRepository.save(ur.getRole());

             }

             user.getUserRoles().addAll(userRoles);

             local=this.userRepository.save(user);

         }
        return local;
    }
   //getting user by username
    @Override
    public User getUser(String username) throws Exception {
        return this.userRepository.findByUserName(username);
    }

    @Override
    public User deleteUserById(Long id) {
            this.userRepository.deleteById(id);
       return null;
    }
}
