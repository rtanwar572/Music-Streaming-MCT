package com.Rohit.Controller;

import com.Rohit.Model.Music;
import com.Rohit.Model.PlayList;
import com.Rohit.Model.RecentView;
import com.Rohit.Model.User;
import com.Rohit.Model.dto.AuthenticationInputDto;
import com.Rohit.Model.dto.SignInInputDto;
import com.Rohit.Service.PlayListService;
import com.Rohit.Service.RecentViewService;
import com.Rohit.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RecentViewService recentViewService;

    @PostMapping("/signUp")
    public String signUpUser(@RequestBody User user){
        return userService.signUpUser(user);
    }
    @PostMapping("/signIn")
    public String signInUser(@RequestBody SignInInputDto signInInputDto){
        return userService.signInUser(signInInputDto);
    }
    @DeleteMapping("/signOut")
    public String singOutUser(@RequestBody AuthenticationInputDto authenticationInputDto){
        return userService.signOutUser(authenticationInputDto);
    }
    //create user playlist
    @PostMapping("/playlist/email/{userMail}/tokVal/{tokenVal}")
    public String makeMusicPlayList(@RequestBody PlayList playList, @PathVariable String userMail, @PathVariable String tokenVal){
        return userService.makePlayList(playList,userMail,tokenVal);
    }
    //updating list name
    @PutMapping("/playlist/listName/{listName}/email/{userMail}/tokVal/{tokenVal}")
    public String updatePlayList(@RequestParam Integer playListId,@PathVariable String listName, @PathVariable String userMail, @PathVariable String tokenVal){
        return userService.updatePlayList(playListId,listName,userMail,tokenVal);
    }
    @DeleteMapping("/playlist/listid/{listId}/email/{userMail}/tokVal/{tokenVal}")
    public String delPlayList(@RequestParam Integer playListId, @PathVariable String userMail, @PathVariable String tokenVal){
        return userService.delPlayList(playListId,userMail,tokenVal);
    }
    @PostMapping("/music/{songId}/playlist/listid/{listId}/email/{userMail}/tokVal/{tokenVal}")
    public String addSongToList(@RequestBody AuthenticationInputDto inputDto,@RequestParam Integer songId ,@RequestParam Integer playListId){
        return userService.addSongToPlayList(songId,playListId,inputDto.getEmail(),inputDto.getTokenVal());
    }
    @DeleteMapping("/music/{songId}/playlist/listid/{listId}/email/{userMail}/tokVal/{tokenVal}")
    public String DelSongFromList(@RequestBody AuthenticationInputDto inputDto,@RequestParam Integer songId ,@RequestParam Integer playListId){
        return userService.delSongFromPlayList(songId,playListId,inputDto.getEmail(),inputDto.getTokenVal());
    }
    //getting all playlist of a particular user ..
    @GetMapping("/myplaylist/email/{userMail}/tokVal/{tokenVal}")
    public List<PlayList> getMyPlayList(@PathVariable String userMail, @PathVariable String tokenVal){
        return userService.getMyPlayList(userMail,tokenVal);
    }
    //getting all songs of a particular play-list and user and top10 songs
    @GetMapping("/mytop10songs/listid/{listId}/email/{userMail}/tokVal/{tokenVal}")
    public List<Music> getMySongs(@RequestParam Integer listId,@PathVariable String userMail, @PathVariable String tokenVal) {
        return userService.getMySongs(listId, userMail, tokenVal);
    }

    //getting songs by artist/author
    @GetMapping("/song/author/{artistNam}/email/{userMail}/tokVal/{tokenVal}")
    public List<Music> getSong(@RequestParam String artistNam,@PathVariable String userMail, @PathVariable String tokenVal){
        return userService.getSong(artistNam,userMail,tokenVal);
    }
    //recent view based
    @PostMapping("/recentlyplay/email/{userMail}/tokVal/{tokenVal}")
    public String playSong(@RequestBody Music song,@PathVariable String userMail,@PathVariable String tokenVal){
        return userService.playSongForUser(song,userMail,tokenVal);
    }
    //get top played songs only
    @GetMapping("/recentPlayed/Songs")
    public List<RecentView> getRecentViews(
            @RequestBody AuthenticationInputDto inputDto,
            @RequestParam int limit) {
//        User existUser=userService.getUserByid(userId);
        return recentViewService.getRecentViewsForUser(inputDto, limit);

    }

}
