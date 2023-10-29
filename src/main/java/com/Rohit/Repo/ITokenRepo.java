package com.Rohit.Repo;

import com.Rohit.Model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepo extends JpaRepository<AuthToken,Integer> {
//    AuthToken findFirstByTokVal(String tokenVal);
    AuthToken findFirstByTokValue(String tokenVal);
}
