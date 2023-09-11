package com.github.flaviobarbosa.musicspringgraphql.service;

import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
import com.github.flaviobarbosa.musicspringgraphql.model.ArtistInput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

  private static Integer LAST_ID = 3;

  private static final List<Artist> artists = new ArrayList<>() {{
    add(new Artist(1, "Guns N Roses"));
    add(new Artist(2, "Nirvana"));
    add(new Artist(3, "ACDC"));
  }};

  public Artist findById(int id) {
    return artists.stream()
        .filter(artist -> artist.id().equals(id))
        .findFirst()
        .orElse(null);
  }

  public List<Artist> findByName(String name) {
    return artists.stream()
        .filter(artist -> artist.name().toLowerCase().contains(name.toLowerCase()))
        .collect(Collectors.toList());
  }

  public Artist addArtist(ArtistInput newArist) {
    Artist artist = new Artist(nextId(), newArist.name());
    artists.add(artist);
    return artist;
  }

  private Integer nextId() {
    return ++LAST_ID;
  }

  public List<Artist> getAll() {
    return artists;
  }
}
