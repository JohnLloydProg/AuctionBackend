package com.payaman.userms.controller;
import com.payaman.userms.kafka.UserProducer;
import com.payaman.userms.model.User;
import com.payaman.userms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    private UserProducer userProducer;

    public UserController(UserProducer userProducer) {
        this.userProducer = userProducer;
    }


    @GetMapping("/api/user")
    public ResponseEntity<?> listUser(){
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try{
            User[] user = userService.getAll();
            response = ResponseEntity.ok().headers(headers).body(user);
        } catch (Exception ex){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
    @PostMapping("api/user")
    public ResponseEntity<?> add(@RequestBody User user){
        logger.info("Input >> " + user.toString());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try{
            User newUser = userService.create(user);
            logger.info("created user >> " + newUser.toString());
            this.userProducer.sendMessage(newUser);
            response = ResponseEntity.ok(newUser);
        } catch (Exception ex){
            logger.error("Failed to retrieve user with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("api/login")
    public ResponseEntity<?> login(@RequestBody User user){
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try{
            User newUser = userService.login(user.getEmail(), user.getPasswordHash());
            response = ResponseEntity.ok(newUser);
        } catch (Exception ex){
            logger.error("Failed to retrieve user with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("api/user")
    public ResponseEntity<?> update(@RequestBody User user){
        logger.info("Update Input >> user.toString() ");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try{
            User newUser = userService.update(user);
            this.userProducer.sendMessage(newUser);
            response = ResponseEntity.ok(user);
        } catch (Exception ex){
            logger.error("Failed to retrieve user with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
    @GetMapping("api/user/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id){
        logger.info("Input user id >>" + Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try{
            User user = userService.get(id);
            if (user != null) {
                response = ResponseEntity.ok(user);
            }else {
                response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
            }
        } catch (Exception ex){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
    @DeleteMapping("api/user/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id){
        logger.info("Input >> " + Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try{
            userService.delete(id);
            response = ResponseEntity.ok(null);
        } catch (Exception ex){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
