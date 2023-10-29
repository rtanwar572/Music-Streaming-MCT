package com.Rohit.Repo;

import com.Rohit.Model.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMusicRepo extends JpaRepository<Music,Integer> {

    List<Music> findBySongArtist(String artistNam);
}
