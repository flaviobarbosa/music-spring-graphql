package com.github.flaviobarbosa.musicspringgraphql.service;

import java.util.Arrays;
import java.util.List;
import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

  private static List<Artist> artists = Arrays.asList(
      new Artist(1, "Guns N Roses"),
      new Artist(2, "Nirvana"),
      new Artist(3, "ACDC")
  );

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
}
