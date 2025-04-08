package at.rennweg.htl.repository;

import at.rennweg.htl.entity.Song;
import at.rennweg.htl.projection.SongProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(String title, String artist);
    @Query("SELECT s.fileData AS fileData FROM Song s WHERE s.id = :id")
    SongProjection findFileDataById(Long id);
}
