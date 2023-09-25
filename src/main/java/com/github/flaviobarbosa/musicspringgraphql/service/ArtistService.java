package com.github.flaviobarbosa.musicspringgraphql.service;

import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
import com.github.flaviobarbosa.musicspringgraphql.model.ArtistInput;
import com.github.flaviobarbosa.musicspringgraphql.repository.ArtistRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArtistService {

  private final ArtistRepository artistRepository;

  public Artist findById(int id) {
    return artistRepository.findById(id).orElse(null);
  }

  public List<Artist> findByName(String name) {
    return artistRepository.findByNameContainingIgnoreCase(name);
  }

  public Artist addArtist(ArtistInput newArist) {
    Artist artist = new Artist(newArist.name());
    return artistRepository.save(artist);
  }

  public List<Artist> getAll() {
    return artistRepository.findAll();
  }

  public boolean existsById(int id) {
    return artistRepository.existsById(id);
  }
}
