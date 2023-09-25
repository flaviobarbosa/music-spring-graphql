package com.github.flaviobarbosa.musicspringgraphql.repository;

import com.github.flaviobarbosa.musicspringgraphql.model.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SongRepository extends JpaRepository<Song, Integer> {

  List<Song> findByNameContainingIgnoreCase(String name);

  @Query(
      value = "select s from Song s where s.artist.id = :artistId"
  )
  List<Song> findByArtist(@Param("artistId") Integer artistId);

}
