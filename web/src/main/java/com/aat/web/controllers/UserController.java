package com.aat.web.controllers;

import com.aat.web.models.UserModel;
import com.aat.web.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ArrayList<UserModel> getProducts(){
        return this.userService.getProducts();
    }

    @PostMapping
    public UserModel saveProducts(@RequestBody UserModel user){
        return this.userService.saveProducts(user);
    }

    @GetMapping(path = "/{id}")
    public Optional<UserModel> getProductsById(@PathVariable Long id){
        return this.userService.getById(id);

    }

    @PutMapping(path = "/{id}")
    public UserModel updateProductsById(@RequestBody UserModel request, @PathVariable("id") Long id){
        return this.userService.updateById(request, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.userService.deleteProduct(id);

        if(ok){
            return "User " + id + " deleted";
        }else{
            return "Error, User not deleted" + id;
        }
    }
}
