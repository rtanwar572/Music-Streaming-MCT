package com.Rohit.Service;

import com.Rohit.Model.Music;
import com.Rohit.Model.PlayList;
import com.Rohit.Model.User;
import com.Rohit.Repo.IMusicRepo;
import com.Rohit.Repo.IPlayListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayListService {
    @Autowired
    IPlayListRepo iPlayListRepo;
    @Autowired
    MusicService musicService;
    @Autowired
    IMusicRepo iMusicRepo;

    public boolean isExist(Integer listId) {
        return iPlayListRepo.existsById(listId);
    }

    public String createPlayList(PlayList playList, User existUser) {
        playList.setUser(existUser);
        playList.setSongs(null);
        iPlayListRepo.save(playList);
        return playList.getUser().getUserName()+" Your PlayList Created Successfully !!";
    }

    public PlayList getPlayListByName(String listName) {
        return iPlayListRepo.findFirstByListName(listName);
    }

    public PlayList getListById(Integer playListId) {
        return iPlayListRepo.findById(playListId).orElseThrow();
    }

    public void saveList(PlayList existList) {
        iPlayListRepo.save(existList);
    }

    public void delListById(Integer playListId) {
        iPlayListRepo.deleteById(playListId);
    }

    public List<PlayList> findListsByUser(User existuser) {
        return iPlayListRepo.findByUser(existuser);
    }


//    public List<Music> getTop10ByUserAndListId(User existUser, Integer listId) {
//        return iPlayListRepo.findTop10ByUserAndListId(existUser,listId);
//    }
}
