package com.github.flaviobarbosa.musicspringgraphql.repository;

import com.github.flaviobarbosa.musicspringgraphql.model.Artist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

  List<Artist> findByNameContainingIgnoreCase(String name);

}
