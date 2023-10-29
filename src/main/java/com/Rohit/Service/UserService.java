package com.Rohit.Service;

import com.Rohit.Model.*;
import com.Rohit.Model.dto.AuthenticationInputDto;
import com.Rohit.Model.dto.SignInInputDto;
import com.Rohit.Repo.IRecentViewRepo;
import com.Rohit.Repo.IUserRepo;
import com.Rohit.Service.MailUtility.EmailHandler;
import com.Rohit.Service.hashingUtility.PassEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    IUserRepo iUserRepo;
    @Autowired
    UAuthToken authToken;
    @Autowired
    MusicService musicService;
    @Autowired
    PlayListService playListService;

    @Autowired
    RecentViewService recentViewService;

    public String signUpUser(User user) {
        String email = user.getUserEmail();
        User existuser = iUserRepo.findFirstByUserEmail(email);
        if (existuser != null) {
            return existuser.getUserRole()+" AlReady Exist just Login";
        }
        if (email.contains("@admin.com")) {
            user.setUserRole(Role.ADMIN);
        } else {
            user.setUserRole(Role.USER);
        }
        String pass = user.getUserPass();
        try {
            String encryptedPass = PassEncryptor.encrypt(pass);
            user.setUserPass(encryptedPass);
            iUserRepo.save(user);
            return user.getUserRole() + " Registered !!";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String signInUser(SignInInputDto signInInputDto) {
        String email = signInInputDto.getEmail();
        User existUser = iUserRepo.findFirstByUserEmail(email);
        if (existUser == null) {
            return "Pls Register YourSelf First !!";
        }
        String pass = signInInputDto.getPass();
        try {
            String encryptedPass = PassEncryptor.encrypt(pass);
            if (existUser.getUserPass().equals(encryptedPass)) {
                AuthToken token = new AuthToken(existUser);
                if (EmailHandler.sendEmail(existUser.getUserEmail(),"Otp After Login",token.getTokValue())){
                    return authToken.createToken(token);
                }
                else {
                    return "Error While Sending Opt/Token";
                }

            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "Invalid Credentials !!";
    }

    public String signOutUser(AuthenticationInputDto authenticationInputDto) {
        String email= authenticationInputDto.getEmail();
        String tokVal=authenticationInputDto.getTokenVal();
        if(authToken.Authenticate(email,tokVal)){
            return authToken.removeToken(tokVal);
        }
        return "UnAuthorized Access ";
    }

    public String addMusic(String adminMail, String adminToken, Music music) {
        if (authToken.Authenticate(adminMail,adminToken) && adminMail.contains("@admin.com")){
            Integer musicId=music.getSongId();
            if(musicId!=null && musicService.isExist(musicId)){
                return "Music Already Exist";
            }
            else {
                return musicService.addMusic(music);
            }
        }
        return "UnAuthorized Access !!";
    }

    public List<Music> getMusics(String adminMail, String adminToken) {
        if (authToken.Authenticate(adminMail,adminToken)){
            return musicService.getAllMusics();
        }
        throw new RuntimeException("UnAuthorized Access !!");
    }

    public String updateMusic(String adminMail, String adminToken, Music music) {
        if (authToken.Authenticate(adminMail,adminToken) && adminMail.contains("@admin.com")){
            Integer musicId=music.getSongId();
            if (musicId!=null && musicService.isExist(musicId)){
                return musicService.updateMusic(music);

            }
            return "Music Doesn't Exist";
        }
        return "UnAuthorized Access!!";
    }

    public String removeMusic(String adminMail, String adminToken, Integer musicId) {
        if (authToken.Authenticate(adminMail,adminToken) && adminMail.contains("@admin.com")){
            return musicService.delMusic(musicId);
        }
        return "UnAuthorized Access !!";
    }

    public String makePlayList(PlayList playList, String userMail, String tokenVal) {
        if (authToken.Authenticate(userMail,tokenVal) && !userMail.contains("@admin.com")){
//            Integer listId=playList.getListId();
            String listName=playList.getListName();
            PlayList existList=playListService.getPlayListByName(listName);
            if(existList!=null){
                return "PlayList Already Exist !!";
            }
            else {
                User existuser=iUserRepo.findFirstByUserEmail(userMail);
                return playListService.createPlayList(playList,existuser);
            }
        }
        return "UnAuthorized Access";
    }

    public String updatePlayList(Integer playListId, String listName, String userMail, String tokenVal) {
        if(authToken.Authenticate(userMail,tokenVal)){
            User existUser=iUserRepo.findFirstByUserEmail(userMail);
            PlayList existList=playListService.getListById(playListId);
            if(existUser.equals(existList.getUser())){
                existList.setListName(listName);
                playListService.saveList(existList);
                return "PlayList Details Updated Successfully !!";
            }
            return "UnAuthorized Access !!";
        }
        return "UnAuthorized Access !!";
    }

    public String delPlayList(Integer playListId, String userMail, String tokenVal) {
        if (authToken.Authenticate(userMail,tokenVal) && playListService.isExist(playListId)){
            User existUser=iUserRepo.findFirstByUserEmail(userMail);
            PlayList existList=playListService.getListById(playListId);
            if(existUser.equals(existList.getUser())){
                playListService.delListById(playListId);
                return existUser.getUserName()+" Your "+ existList.getListName()+" PlayList Removed Successfully!! ";
            }
            return "UnAuthorized Deletion";
        }
        return "UnAuthorized Access !!";
    }

    public String addSongToPlayList(Integer songId, Integer playListId, String userMail, String tokenVal) {
        if (authToken.Authenticate(userMail,tokenVal) && playListService.isExist(playListId)){
            Music existMusic=musicService.getMusicById(songId);
            PlayList existList=playListService.getListById(playListId);
            List<Music> musicList=playListService.getListById(playListId).getSongs();
            User existUser=iUserRepo.findFirstByUserEmail(userMail);

            if(existUser.equals(existList.getUser()) && !musicList.contains(existMusic)){
                musicList.add(existMusic);
                existList.setSongs(musicList);
                playListService.saveList(existList);
                return "Music Added To "+existList.getListName()+" List";
            }
            return "UnAuthorized Access!! ";
        }
        return "UnAuthorized Access ";
    }

    public String delSongFromPlayList(Integer songId, Integer playListId, String userMail, String tokenVal) {
        if (authToken.Authenticate(userMail,tokenVal) && !userMail.contains("@admin.com")){
            PlayList existList=playListService.getListById(playListId);
            List<Music> exitsMusicList=existList.getSongs();
            Music existMusic=musicService.getMusicById(songId);
            if(existMusic!=null && existList!=null){
                //deleting muic from music-list
                exitsMusicList.remove(existMusic);
                existList.setSongs(exitsMusicList);
                playListService.saveList(existList);
                return existMusic.getSongTitle()+" Song Deleted from "+existList.getListName()+" List";
            }
            return "No Record Found for Id's";

        }
        return "UnAuthorized Access!! ";
    }

    public List<PlayList> getMyPlayList(String userMail, String tokenVal) {
        if (authToken.Authenticate(userMail, tokenVal) && !userMail.contains("@admin.com")){
            User existuser=iUserRepo.findFirstByUserEmail(userMail);
            return playListService.findListsByUser(existuser);
        }
        throw  new RuntimeException("UnAuthorized fetch Operation");
    }

    public List<Music> getMySongs(Integer listId, String userMail, String tokenVal) {
        if (authToken.Authenticate(userMail,tokenVal) && !userMail.contains("@admin.com")){
            User existUser=iUserRepo.findFirstByUserEmail(userMail);
            User plistUser=playListService.getListById(listId).getUser();
            if(existUser.equals(plistUser)){
                PlayList existPList=playListService.getListById(listId);
                return existPList.getSongs().stream().limit(2).collect(Collectors.toList());
            }
            return null;
        }
        throw new RuntimeException("UnAuthorized fetch !!");
    }

    public List<Music> getSong(String artistNam, String userMail, String tokenVal) {
        if (authToken.Authenticate(userMail,tokenVal) && !userMail.contains("@admin.com")){
            return musicService.getSongsByArtist(artistNam);
        }
        throw new RuntimeException("UnAuthorized fetch !!");
    }

    public List<User> getAllUsers(String adminMail, String adminToken) {
        if (authToken.Authenticate(adminMail,adminToken) && adminMail.contains("@admin.com")){
            return iUserRepo.findByUserRole(Role.USER);
        }
        return null;

    }

    public String playSongForUser(Music playSong,String email,String tokVal) {
        Music existSong=musicService.getMusicById(playSong.getSongId());
        if (authToken.Authenticate(email,tokVal) && existSong!=null){
            User existUser=iUserRepo.findFirstByUserEmail(email);
            return recentViewService.playSong(existSong,existUser);
        }
        return "UnAuthorized Access";

    }

    public User getUserByid(Integer userId) {
        return iUserRepo.findById(userId).orElseThrow();
    }
}
