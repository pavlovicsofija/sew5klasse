package at.rennweg.htl.controller;

import at.rennweg.htl.entity.Song;
import at.rennweg.htl.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/songs")
    public List<Song> fetchSongs() {
        return songRepository.findAll();
    }

    @PostMapping("/songs")
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        try {
            Song newSong = songRepository.save(song);
            return new ResponseEntity<>(newSong, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable("id") Long id, @RequestBody Song songDetails) {
        Optional<Song> existingSong = songRepository.findById(id);

        if (existingSong.isPresent()) {
            Song song = existingSong.get();
            song.setTitle(songDetails.getTitle());
            song.setArtist(songDetails.getArtist());
            song.setGenre(songDetails.getGenre());
            song.setLength(songDetails.getLength());
            return new ResponseEntity<>(songRepository.save(song), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<HttpStatus> deleteSong(@PathVariable("id") Long id) {
        try {
            songRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/songs/search")
    public ResponseEntity<List<Song>> searchSongs(@RequestParam("keyword") String keyword) {
        List<Song> results = songRepository.searchByTitleOrArtist(keyword);
        return results.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(results) : ResponseEntity.ok(results);
    }
}
