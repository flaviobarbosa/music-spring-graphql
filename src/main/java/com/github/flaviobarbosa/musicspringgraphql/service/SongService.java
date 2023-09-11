package com.github.flaviobarbosa.musicspringgraphql.service;

import com.github.flaviobarbosa.musicspringgraphql.model.Song;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SongService {

  private static final List<Song> songs = Arrays.asList(
      new Song(1, "Welcome to the jungle", 1),
      new Song(2, "Smells like teen spirit", 2),
      new Song(3, "Back in black", 3),
      new Song(4, "Patience", 1),
      new Song(5, "Come as you are", 2),
      new Song(6, "TNT", 3)
  );

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
}
