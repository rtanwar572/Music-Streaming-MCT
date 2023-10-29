package com.Rohit.Repo;

import com.Rohit.Model.Music;
import com.Rohit.Model.RecentView;
import com.Rohit.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface IRecentViewRepo extends JpaRepository<RecentView,Integer> {

    List<RecentView> findByUserOrderByViewTimeDesc(User user);

    RecentView findBySong(Music existSong);
}
