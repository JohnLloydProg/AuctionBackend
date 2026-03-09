package com.payaman.userms.transform;
import com.payaman.userms.entity.UserData;
import com.payaman.userms.model.User;
public interface TransformUserService {
    UserData transform(User user);
    User transform(UserData userData);
}
