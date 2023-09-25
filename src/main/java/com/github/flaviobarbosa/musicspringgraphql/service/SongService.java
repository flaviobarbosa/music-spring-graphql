package com.github.flaviobarbosa.musicspringgraphql.service;

import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
import com.github.flaviobarbosa.musicspringgraphql.model.Song;
import com.github.flaviobarbosa.musicspringgraphql.model.SongInput;
import com.github.flaviobarbosa.musicspringgraphql.repository.SongRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SongService {

  private final ArtistService artistService;
  private final SongRepository songRepository;

  public Song findById(int id) {
    return songRepository.findById(id).orElse(null);
  }

  public List<Song> findByName(String name) {
    return songRepository.findByNameContainingIgnoreCase(name);
  }

  public List<Song> findByArtist(int artistId) {
    return songRepository.findByArtist(artistId);
  }

  public Song addSong(SongInput newSong) {
    if(!artistService.existsById(newSong.artistId())) {
      //TODO check how to return exception with graphql
      throw new RuntimeException("Artist not found");
    }

    Artist artist = artistService.findById(newSong.artistId());
    Song song = new Song(newSong.name(), artist);
    return songRepository.save(song);
  }
}
