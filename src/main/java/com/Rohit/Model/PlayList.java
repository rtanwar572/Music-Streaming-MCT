package com.Rohit.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,scope = PlayList.class,property = "listId")
public class PlayList {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer listId;
    @NotBlank
    @NotNull
    private String listName;

    @ManyToOne
    @JoinColumn(name = "fk-user-id")
    User user;

    @ManyToMany
    @JoinTable(
            name = "Playlist_Music",
            joinColumns = @JoinColumn(name = "fk-playlist-id"),
            inverseJoinColumns = @JoinColumn(name = "fk-music-id")
    )
    List<Music> songs;

}
