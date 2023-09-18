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

  public static final Artist GUNS_N_ROSES = new Artist(1, "Guns N' Roses");
  public static final Artist NIRVAVA = new Artist(2, "Nirvana");
  public static final Artist AC_DC = new Artist(3, "ACDC");

  private static final List<Artist> artists = new ArrayList<>() {{
    add(GUNS_N_ROSES);
    add(NIRVAVA);
    add(AC_DC);
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

  public boolean existsById(int id) {
    return artists.stream().anyMatch(artist -> artist.id().equals(id));
  }
}
