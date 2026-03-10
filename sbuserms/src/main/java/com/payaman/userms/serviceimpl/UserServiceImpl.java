package com.payaman.userms.serviceimpl;
import com.payaman.userms.entity.UserData;
import com.payaman.userms.model.User;
import com.payaman.userms.repository.UserDataRepository;
import com.payaman.userms.service.UserService;
import com.payaman.userms.transform.TransformUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class UserServiceImpl implements UserService{
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserDataRepository userDataRepository;
    public UserData transform(User user){
        UserData userData = new UserData();
        userData.setUserId(user.getUserId());
        userData.setUsername(user.getUsername());
        userData.setEmail(user.getEmail());
        userData.setPasswordHash(user.getPasswordHash());
        userData.setRole(user.getRole());
        userData.setStatus(user.getStatus());
        return userData;
    }
    public User transform(UserData userData){
        User user = new User();
        user.setUserId(userData.getUserId());
        user.setUsername(userData.getUsername());
        user.setEmail(userData.getEmail());
        user.setPasswordHash(userData.getPasswordHash());
        user.setRole(userData.getRole());
        user.setStatus(userData.getStatus());
        return user;
    }

    @Override
    public User login(String email, String password) throws Exception {
        List<UserData> userDataList = new ArrayList<>();
        userDataRepository.findAll().forEach(userDataList::add);
        for (UserData userData : userDataList) {
            if (userData.getEmail().equals(email) && userData.getPasswordHash().equals(password)) {
                return transform(userData);
            }
        }
        return null;
    }

    @Override
    public User[] getAll(){
        List<UserData> usersData = new ArrayList<>();
        List<User> users = new ArrayList<>();
        userDataRepository.findAll().forEach(usersData::add);
        Iterator<UserData> it = usersData.iterator();
        while(it.hasNext()) {
            UserData userData = it.next();
            User user = this.transform(userData);
            users.add(user);
        }
        User[] array = new User[users.size()];
        for (int i = 0; i < users.size(); i++){
            array[i] = users.get(i);
        }
        return array;
    }
    @Override
    public User create(User user){
        logger.info(" add:input " + user.toString());
        UserData userData = this.transform(user);
        userData = userDataRepository.save(userData);
        logger.info( "add:input " + userData.toString());
        User newUser = this.transform(userData);
        return newUser;
    }
    @Override
    public User update(User user){
        User updatedUser = null;
        int id = user.getUserId();
        Optional<UserData> optional  = userDataRepository.findById(id);
        if (optional.isPresent()){
            UserData originalUserData = this.transform(user);
            originalUserData.setCreatedAt(optional.get().getCreatedAt());
            UserData userData = userDataRepository.save(originalUserData);
            updatedUser = this.transform(userData);
        } else{
            logger.error("User record with id: " + Integer.toString(id) + " do not exist ");
        }
        return updatedUser;
    }
    @Override
    public User get(Integer id){
        logger.info(" Input id >>" + Integer.toString(id));
        User user = null;
        Optional<UserData> optional = userDataRepository.findById(id);
        if(optional.isPresent()){
            logger.info(" Is present >> ");
            user = this.transform(optional.get());
        } else {
            logger.info(" Failed >> unable to locate id: " + Integer.toString(id));
        }
        return user;
    }
    @Override
    public void delete(Integer id){
        User user = null;
        logger.info(" Input >> " + Integer.toString(id));
        Optional<UserData> optional = userDataRepository.findById(id);
        if(optional.isPresent()){
            UserData userDatum = optional.get();
            userDataRepository.delete(optional.get());
            logger.info("Successfully deleted User record with id: " + Integer.toString(id));
        } else{
            logger.info("Unable to locate user with id: " + Integer.toString(id));
        }
    }
}
