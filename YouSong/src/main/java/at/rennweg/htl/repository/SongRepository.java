package at.rennweg.htl.repository;

import at.rennweg.htl.entity.Song;
import at.rennweg.htl.projection.SongProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    // Gibt nur die Projektion ohne Musikdaten zurück
    List<SongProjection> findAllProjectedBy();
}
