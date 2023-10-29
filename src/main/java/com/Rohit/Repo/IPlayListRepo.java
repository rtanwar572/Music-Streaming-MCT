package com.Rohit.Repo;

import com.Rohit.Model.Music;
import com.Rohit.Model.PlayList;
import com.Rohit.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlayListRepo extends JpaRepository<PlayList,Integer> {
//    PlayList findByListName(String listName);

    PlayList findFirstByListName(String listName);

    List<PlayList> findByUser(User existuser);

//    List<Music> findTop10ByUser(User existUser);

//    List<Music> findTop10ByUserAndListId(User existUser, Integer listId);


//    List<Music> findTop10ByUserAndListId(User user, Integer listId);

//    List<Music> findByListId(Integer listId);
}
