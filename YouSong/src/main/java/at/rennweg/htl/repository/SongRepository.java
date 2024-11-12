package at.rennweg.htl.repository;

import at.rennweg.htl.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s FROM Song s WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.artist) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Song> searchByTitleOrArtist(@Param("keyword") String keyword);
}
