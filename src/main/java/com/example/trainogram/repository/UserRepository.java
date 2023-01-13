package com.example.trainogram.repository;

import com.example.trainogram.model.Role;
import com.example.trainogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    void deleteUserByUsername(String username);

    @Query("select u.email from User u where u.role <> 'DELETED' ")
    List<String> getEmails();

    @Query("select u from User u where u.role <> 'DELETED' ")
    List<User> findAllActiveUsers();

//    @Query()
//    void deleteUser(User byId);

    @Modifying
    @Query(value = "insert into users_deleted (avatar, password, role, username, email) values (:id,:avatar, :password, :role, :username, :email)",
    nativeQuery = true)
    void insertUser(@Param("avatar") String avatar, @Param("password") String password,
                    @Param("role") Role role,@Param("username") String username ,@Param("email") String email);


//    String[] getAllByEmail();

}