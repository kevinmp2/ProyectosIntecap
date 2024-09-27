package com.aat.web.services;

import com.aat.web.models.UserModel;
import com.aat.web.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    public ArrayList<UserModel> getProducts(){
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel saveProducts(UserModel user){
        return userRepository.save(user);
    }

    public Optional<UserModel> getById(Long id){
        return userRepository.findById(id);
    }

    public UserModel updateById(UserModel request, Long id){
        UserModel product = userRepository.findById(id).get();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrecio(request.getPrecio());

        return userRepository.save(product);
    }


    public Boolean deleteProduct(Long id){
        try {
            userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
