package com.github.flaviobarbosa.musicspringgraphql;

import com.github.flaviobarbosa.musicspringgraphql.controller.ArtistController;
import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
import com.github.flaviobarbosa.musicspringgraphql.service.ArtistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest(ArtistController.class)
@Import(ArtistService.class)
class ArtistControllerIT {

  @Autowired
  private GraphQlTester graphQlTester;

  @Test
  void findAllArtists_ShouldReturnAllArtists() {
    // language=GraphQL
    String document = """
        query{
            allArtists {
                id
                name
            }
        }
        """;

    graphQlTester.document(document)
        .execute()
        .path("allArtists")
        .entityList(Artist.class)
        .hasSize(3);
  }

  @Test
  void findArtistById_ShouldReturnArtist() {
    // language=GraphQL
    String document = """
          query artistById($id: ID) {
              artistById(id: $id) {
                id
                name
              }
            }
        """;

    graphQlTester.document(document)
        .variable("id", 1)
        .execute()
        .path("artistById")
        .entity(Artist.class)
        .satisfies(artist -> {
          Assertions.assertEquals(1, artist.id());
          Assertions.assertEquals("Guns N Roses", artist.name());
        });
  }

  @Test
  void findArtistsByName_ShouldReturnArtists() {
    // language=GraphQL
    String document = """
          query ($name: String) {
            artistsByName(name: $name) {
              id
              name
            }
          }
        """;

    graphQlTester.document(document)
        .variable("name", "n")
        .execute()
        .path("artistsByName")
        .entityList(Artist.class)
        .hasSize(2);
  }

}