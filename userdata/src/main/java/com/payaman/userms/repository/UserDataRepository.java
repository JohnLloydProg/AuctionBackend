package com.payaman.userms.repository;
import com.payaman.userms.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
}
