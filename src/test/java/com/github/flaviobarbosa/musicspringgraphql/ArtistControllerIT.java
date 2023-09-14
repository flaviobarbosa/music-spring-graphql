package com.github.flaviobarbosa.musicspringgraphql;

import com.github.flaviobarbosa.musicspringgraphql.controller.ArtistController;
import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
import com.github.flaviobarbosa.musicspringgraphql.service.ArtistService;
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
}