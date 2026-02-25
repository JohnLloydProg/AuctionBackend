package com.payaman.userms.service;
import com.payaman.userms.model.User;
public interface UserService {
    User[] getAll() throws Exception;
    User get(Integer id) throws Exception;
    User create(User user) throws Exception;
    User update(User user) throws Exception;
    void delete(Integer id) throws Exception;
}
