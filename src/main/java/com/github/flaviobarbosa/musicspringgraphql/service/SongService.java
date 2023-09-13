package com.github.flaviobarbosa.musicspringgraphql.service;

import com.github.flaviobarbosa.musicspringgraphql.model.Song;
import com.github.flaviobarbosa.musicspringgraphql.model.SongInput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SongService {

  private static Integer LAST_ID = 6;

  private final List<Song> songs = new ArrayList<>() {{
    add(new Song(1, "Welcome to the jungle", 1));
    add(new Song(2, "Smells like teen spirit", 2));
    add(new Song(3, "Back in black", 3));
    add(new Song(4, "Patience", 1));
    add(new Song(5, "Come as you are", 2));
    add(new Song(6, "TNT", 3));
  }};

  private final ArtistService artistService;

  public Song findById(int id) {
    return songs.stream()
        .filter(song -> song.id().equals(id))
        .findFirst()
        .orElse(null);
  }

  public List<Song> findByName(String name) {
    return songs.stream()
        .filter(song -> song.name().toLowerCase().contains(name.toLowerCase()))
        .collect(Collectors.toList());
  }

  public List<Song> findByArtist(int artistId) {
    return songs.stream()
        .filter(song -> song.artistId().equals(artistId))
        .collect(Collectors.toList());
  }

  public Song addSong(SongInput newSong) {
    if(!artistService.existsById(newSong.artistId())) {
      //TODO check how to return exception with graphql
      throw new RuntimeException("Artist not found");
    }

    Song song = new Song(nextId(), newSong.name(), newSong.artistId());
    songs.add(song);
    return song;
  }

  private Integer nextId() {
    return ++LAST_ID;
  }
}
