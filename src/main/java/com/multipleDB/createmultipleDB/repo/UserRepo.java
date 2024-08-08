package com.multipleDB.createmultipleDB.repo;

import com.multipleDB.createmultipleDB.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<Users, Long> {

    @Query(value = "select * from users where user_id=?1", nativeQuery = true)
    Users getUserById(long userId);
}
