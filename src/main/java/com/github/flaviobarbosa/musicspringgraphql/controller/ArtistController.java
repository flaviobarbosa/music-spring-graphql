package com.github.flaviobarbosa.musicspringgraphql.controller;

import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
import com.github.flaviobarbosa.musicspringgraphql.service.ArtistService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ArtistController {

  private final ArtistService artistService;

  @QueryMapping
  public Artist artistById(@Argument int id) {
    return artistService.findById(id);
  }

  @QueryMapping("artistByName")
  public List<Artist> findArtistByName(@Argument String name) {
    return artistService.findByName(name);
  }
}
