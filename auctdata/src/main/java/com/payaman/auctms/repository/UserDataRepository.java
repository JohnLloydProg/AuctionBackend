package com.payaman.auctms.repository;

import com.payaman.auctms.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, String> {
    // This allows you to perform database operations like .save()
}