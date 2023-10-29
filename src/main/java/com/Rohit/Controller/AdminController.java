package com.Rohit.Controller;

import com.Rohit.Model.Music;
import com.Rohit.Model.User;
import com.Rohit.Model.dto.AuthenticationInputDto;
import com.Rohit.Model.dto.SignInInputDto;
import com.Rohit.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {
    @Autowired
    UserService userService;

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
    //adsMusic by admin
    @PostMapping("/music/email/{adminMail}/tokenVal/{adminToken}")
    public String addMusic(@PathVariable String adminMail,@PathVariable String adminToken,@RequestBody Music music){
        return userService.addMusic(adminMail,adminToken,music);
    }
    //both user and admin can fetch all songs..
    @GetMapping("/allSongs/email/{adminMail}/tokenVal/{adminToken}")
    public List<Music> getMusics(@PathVariable String adminMail, @PathVariable String adminToken){
        return userService.getMusics(adminMail,adminToken);
    }
    @PutMapping("/music/email/{adminMail}/tokenVal/{adminToken}")
    public String updateMusic(@PathVariable String adminMail,@PathVariable String adminToken,@RequestBody Music music){
        return userService.updateMusic(adminMail,adminToken,music);
    }
    @DeleteMapping("/music/email/{adminMail}/tokenVal/{adminToken}")
    public String removeMusic(@PathVariable String adminMail,@PathVariable String adminToken,@RequestParam Integer musicId){
        return userService.removeMusic(adminMail,adminToken,musicId);
    }
    @GetMapping("/alluser/email/{adminMail}/tokenVal/{adminToken}")
    public List<User> getAllUsers(@PathVariable String adminMail,@PathVariable String adminToken){
        return userService.getAllUsers(adminMail,adminToken);
    }

}
