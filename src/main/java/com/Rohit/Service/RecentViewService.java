package com.Rohit.Service;

import com.Rohit.Model.Music;
import com.Rohit.Model.RecentView;
import com.Rohit.Model.User;
import com.Rohit.Model.dto.AuthenticationInputDto;
import com.Rohit.Repo.IRecentViewRepo;
import com.Rohit.Repo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecentViewService {
    @Autowired
    IRecentViewRepo iRecentViewRepo;
    @Autowired
    UAuthToken authToken;
    @Autowired
    IUserRepo iUserRepo;
    public String playSong(Music existSong, User existUser) {
        RecentView recentView=new RecentView(null,existSong,existUser, LocalDateTime.now());
        RecentView existView=iRecentViewRepo.findBySong(existSong);
        if(existView==null){
            iRecentViewRepo.save(recentView);
        }
        return existSong.getSongTitle()+" Song Played !!";

    }

    public List<RecentView> getRecentViewsForUser(AuthenticationInputDto inputDto, int limit) {
        String email=inputDto.getEmail();
        if(authToken.Authenticate(email,inputDto.getTokenVal()) && !email.contains("@admin.com")){
            User existUser=iUserRepo.findFirstByUserEmail(email);
            return iRecentViewRepo.findByUserOrderByViewTimeDesc(existUser)
                    .stream().limit(limit).collect(Collectors.toList());
        }
        throw new RuntimeException("UnAuthorized access !!");

    }
}
