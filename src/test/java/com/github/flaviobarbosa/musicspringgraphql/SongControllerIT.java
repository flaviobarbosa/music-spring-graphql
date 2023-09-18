package com.github.flaviobarbosa.musicspringgraphql;

import static com.github.flaviobarbosa.musicspringgraphql.service.ArtistService.GUNS_N_ROSES;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.flaviobarbosa.musicspringgraphql.controller.SongController;
import com.github.flaviobarbosa.musicspringgraphql.model.Song;
import com.github.flaviobarbosa.musicspringgraphql.service.ArtistService;
import com.github.flaviobarbosa.musicspringgraphql.service.SongService;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest(SongController.class)
@Import({SongService.class, ArtistService.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SongControllerIT {

  @Autowired
  private GraphQlTester graphQlTester;

  @Test
  @Order(1)
  void findSongById_ShouldReturnSong() {
    // language=Graphql
    String document = """
          query ($id: ID) {
            songById(id: $id) {
              id
              name
              artist {
                id
                name   
              }
            }
          }
        """;

    Song song = graphQlTester.document(document)
        .variable("id", 1)
        .execute()
        .path("songById")
        .entity(Song.class)
        .get();

    assertThat(song).isNotNull();
    assertThat(song.artist().id()).isEqualTo(GUNS_N_ROSES.id());
    assertThat(song.artist().name()).isEqualTo(GUNS_N_ROSES.name());
  }

  @Test
  @Order(2)
  void findSongByName_ShouldReturnSongs() {
    // language=Graphql
    String document = """
          query ($name: String) {
            songByName(name: $name) {
              id
              name
              artist {
                id
                name        
              }
            }
          }
        """;

    List<Song> songs = graphQlTester.document(document)
        .variable("name", "t")
        .execute()
        .path("songByName")
        .entityList(Song.class)
        .get();

    assertThat(songs).hasSize(4);
  }

  @Test
  @Order(3)
  void findSongByArtist_ShouldReturnSongs() {
    // language=Graphql
    String document = """
          query ($id: ID) {
            songsByArtist(artistId: $id) {
              id
              name
              artist {
                id
                name              
              }
            }
          }
        """;

    graphQlTester.document(document)
        .variable("id", GUNS_N_ROSES.id())
        .execute()
        .path("songsByArtist")
        .entityList(Song.class)
        .hasSize(2);
  }
}
