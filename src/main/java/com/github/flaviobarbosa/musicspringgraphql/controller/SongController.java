package com.github.flaviobarbosa.musicspringgraphql.controller;

import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
import com.github.flaviobarbosa.musicspringgraphql.model.Song;
import com.github.flaviobarbosa.musicspringgraphql.model.SongInput;
import com.github.flaviobarbosa.musicspringgraphql.service.ArtistService;
import com.github.flaviobarbosa.musicspringgraphql.service.SongService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class SongController {

  private final SongService songService;
  private final ArtistService artistService;

  @QueryMapping("songById")
  public Song findSongById(@Argument Integer id) {
    return songService.findById(id);
  }

  @QueryMapping("songByName")
  public List<Song> findSongByName(@Argument String name) {
    return songService.findByName(name);
  }

  @QueryMapping("songsByArtist")
  public List<Song> songsByArtist(@Argument int artistId) {
    return songService.findByArtist(artistId);
  }

  @MutationMapping
  public Song addSong(@Argument SongInput song) {
    return songService.addSong(song);
  }

  @SchemaMapping
  public Artist artist(Song song) {
    return artistService.findById(song.artist().id());
  }
}
