package com.github.flaviobarbosa.musicspringgraphql;

import static com.github.flaviobarbosa.musicspringgraphql.service.ArtistService.GUNS_N_ROSES;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.flaviobarbosa.musicspringgraphql.controller.SongController;
import com.github.flaviobarbosa.musicspringgraphql.model.Song;
import com.github.flaviobarbosa.musicspringgraphql.service.ArtistService;
import com.github.flaviobarbosa.musicspringgraphql.service.SongService;
import java.util.HashMap;
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

  @Autowired
  private SongService songService;

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
    assertThat(song.getArtist().getId()).isEqualTo(GUNS_N_ROSES.getId());
    assertThat(song.getArtist().getName()).isEqualTo(GUNS_N_ROSES.getName());
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
        .variable("id", GUNS_N_ROSES.getId())
        .execute()
        .path("songsByArtist")
        .entityList(Song.class)
        .hasSize(2);
  }

  @Test
  @Order(4)
  void shouldAddNewSong() {
    // language=Graphql
    String document = """
      mutation ($newSong: SongInput) {
        addSong(song: $newSong) {
          id
          name
          artist {
            id
            name          
          }
        } 
      }
    """;

    String songName = "Live and Let Die";
    HashMap<String, Object> variables = new HashMap<>() {{
      put("name", songName);
      put("artistId", GUNS_N_ROSES.getId());
    }};

    int currentSongs = songService.findByArtist(GUNS_N_ROSES.getId()).size();

    Song song = graphQlTester.document(document)
        .variable("newSong", variables)
        .execute()
        .path("addSong")
        .entity(Song.class)
        .get();

    assertThat(song.getId()).isNotNull();
    assertThat(song.getName()).isEqualTo(songName);
    assertThat(song.getArtist().getId()).isEqualTo(GUNS_N_ROSES.getId());

    int newNumberOfSongs = songService.findByArtist(GUNS_N_ROSES.getId()).size();

    assertThat(newNumberOfSongs).isEqualTo(currentSongs + 1);
  }
}
