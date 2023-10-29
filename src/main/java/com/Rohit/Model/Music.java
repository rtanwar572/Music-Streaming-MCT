package com.Rohit.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,scope = Music.class,property = "songId")
public class Music {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer songId;
    @NotNull
    private String songTitle;
    private String songDesc;
    @Pattern(regexp = "^(?:[1-9]\\d*|0)(?:M|K)?$")
    private String popularity;
    @NotBlank
    private String songArtist;
    @NotEmpty
    private String songCover;
    @Pattern(regexp = "^(?:[0-1]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")
    private String songDuration;

}
