package com.Rohit.Service;

import com.Rohit.Model.AuthToken;
import com.Rohit.Repo.ITokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UAuthToken {
    @Autowired
    ITokenRepo iTokenRepo;

    public boolean Authenticate(String email, String tokenVal) {
        AuthToken token =iTokenRepo.findFirstByTokValue(tokenVal);
        if(token==null){
            return false;
        }
        String existMail=token.getUser().getUserEmail();
        return existMail.equals(email);
    }

    public String createToken(AuthToken token) {
        iTokenRepo.save(token);
        return token.getRoleType()+" Your Otp/Token Sent Successfully ";
    }

    public String removeToken(String tokVal) {
        AuthToken token=iTokenRepo.findFirstByTokValue(tokVal);
        iTokenRepo.delete(token);
        return token.getRoleType()+" SignOut Successfully!! ";
    }
}