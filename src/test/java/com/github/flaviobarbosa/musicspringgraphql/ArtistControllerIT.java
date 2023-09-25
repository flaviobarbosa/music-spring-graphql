package com.github.flaviobarbosa.musicspringgraphql;

import static com.github.flaviobarbosa.musicspringgraphql.service.ArtistService.GUNS_N_ROSES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.flaviobarbosa.musicspringgraphql.controller.ArtistController;
import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
import com.github.flaviobarbosa.musicspringgraphql.service.ArtistService;
import java.util.HashMap;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest(ArtistController.class)
@Import(ArtistService.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //adding order because of the in-memory artist list
class ArtistControllerIT {

  @Autowired
  private GraphQlTester graphQlTester;

  @Autowired
  private ArtistService artistService;

  @Test
  @Order(1)
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
  @Order(2)
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
          assertEquals(1, artist.getId());
          assertEquals(GUNS_N_ROSES.getName(), artist.getName());
        });
  }

  @Test
  @Order(3)
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

  @Test
  @Order(4)
  void shouldAddNewArtist() {
    int currentNumberOfArtists = artistService.getAll().size();

    // language=GraphQL
    String document = """
          mutation ($artist: ArtistInput) {
            addArtist(artist: $artist) {
              id
              name
            }
          }
        """;

    String artistName = "Pink Floyd";
    HashMap<String, String> variables = new HashMap<>() {{
      put("name", artistName);
    }};

    graphQlTester.document(document)
        .variable("artist", variables)
        .execute()
        .path("addArtist")
        .entity(Artist.class)
        .satisfies(artist -> {
          assertNotNull(artist.getId());
          assertEquals(artistName, artist.getName());
        });

    int newNumberOfArtists = artistService.getAll().size();

    assertEquals(currentNumberOfArtists + 1, newNumberOfArtists);
  }

}