package com.example.login_password.service;

import com.example.login_password.model.User;
import com.example.login_password.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository user_info_repo;

    @PersistenceContext
    private EntityManager entityManager;


    public boolean addUser(String phonenumber, String password){
        entityManager.clear();
        User user = user_info_repo.findByPhoneNumber(phonenumber);
        System.out.println(user);
        if(user == null){
            user = new User();
            user.setPhoneNumber(phonenumber);
            user.setPassword(password);
            user_info_repo.save(user);
            return true;
        }
        else{
            System.err.println("user already exists");
            return false;
        }
    }

    public boolean ResetPassword(String phonenumber, String password){
        User user = user_info_repo.findByPhoneNumber(phonenumber);
        if(user != null){
            user.setPassword(password);
            user_info_repo.save(user);
            return true;
        }
        else{
            System.err.println("user doesn't exist");
            return false;
        }
    }

    public boolean updateUser(User user_info) {
        User existingUser = user_info_repo.findByPhoneNumber(user_info.getPhoneNumber());

        if (existingUser != null) {
            if (user_info.getPassword() == null || user_info.getPhoneNumber() == null) {
                return false; // Return false if either password or phone number is null
            }

            existingUser.setUsername(user_info.getUsername());
            existingUser.setAge(user_info.getAge());
            existingUser.setGender(user_info.getGender());

            user_info_repo.save(existingUser);
            return true;
        }
        System.err.println("error in updating");
        return false;
    }

    public User viewUser(String phonenumber) {
        User user = user_info_repo.findByPhoneNumber(phonenumber);
        if (user != null) {
            return user;
        }
        System.err.println("error in finding user");
        return null;
    }

    public User login(String phoneNumber, String password) {
        User user = user_info_repo.findByPhoneNumber(phoneNumber);
        if (user != null && user.getPassword().equals(password)) {
            user.setLoggedin(true);
            user_info_repo.save(user);
            return user;
        }
        return null;
    }

    public boolean logout(String phoneNumber) {
        User user = user_info_repo.findByPhoneNumber(phoneNumber);
        if (user != null) {
            user.setLoggedin(false);
            user_info_repo.save(user);
            return true;
        }
        return false;
    }
}
