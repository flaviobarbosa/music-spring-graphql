package com.github.flaviobarbosa.musicspringgraphql.service;

import static com.github.flaviobarbosa.musicspringgraphql.service.ArtistService.AC_DC;
import static com.github.flaviobarbosa.musicspringgraphql.service.ArtistService.GUNS_N_ROSES;
import static com.github.flaviobarbosa.musicspringgraphql.service.ArtistService.NIRVAVA;

import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
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
    add(new Song(1, "Welcome to the jungle", GUNS_N_ROSES));
    add(new Song(2, "Smells like teen spirit", NIRVAVA));
    add(new Song(3, "Back in black", AC_DC));
    add(new Song(4, "Patience", GUNS_N_ROSES));
    add(new Song(5, "Come as you are", NIRVAVA));
    add(new Song(6, "TNT", AC_DC));
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
        .filter(song -> song.artist().id().equals(artistId))
        .collect(Collectors.toList());
  }

  public Song addSong(SongInput newSong) {
    if(!artistService.existsById(newSong.artistId())) {
      //TODO check how to return exception with graphql
      throw new RuntimeException("Artist not found");
    }

    Artist artist = artistService.findById(newSong.artistId());
    Song song = new Song(nextId(), newSong.name(), artist);
    songs.add(song);
    return song;
  }

  private Integer nextId() {
    return ++LAST_ID;
  }
}
