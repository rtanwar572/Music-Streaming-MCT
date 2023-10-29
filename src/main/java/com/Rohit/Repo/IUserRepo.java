package com.Rohit.Repo;

import com.Rohit.Model.Role;
import com.Rohit.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepo extends JpaRepository<User,Integer> {

    User findFirstByUserEmail(String email);

    List<User> findByUserRole(Role role);
}
