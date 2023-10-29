package com.Rohit.Service;

import com.Rohit.Model.Music;
import com.Rohit.Repo.IMusicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    @Autowired
    IMusicRepo iMusicRepo;

    public String addMusic(Music music) {
        iMusicRepo.save(music);
        return "Music Added!!";
    }

    public List<Music> getAllMusics() {
        return iMusicRepo.findAll();
    }

    public boolean isExist(Integer musicId) {
        return iMusicRepo.existsById(musicId);
    }

    public String updateMusic(Music music) {
        iMusicRepo.save(music);
        return "Music Details Updated !!";
    }

    public String delMusic(Integer musicId) {
        iMusicRepo.deleteById(musicId);
        return "Music Deleted Successfully !";
    }

    public Music getMusicById(Integer songId) {
        return iMusicRepo.findById(songId).orElseThrow();
    }

    public List<Music> getSongsByArtist(String artistNam) {
        return iMusicRepo.findBySongArtist(artistNam);
    }
//
//    public List<Music> findSongsByListId(Integer listId) {
//        return iMusicRepo.findByListId(listId);
//    }
}
