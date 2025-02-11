package at.rennweg.htl.controller;

import at.rennweg.htl.entity.Song;
import at.rennweg.htl.projection.SongProjection;
import at.rennweg.htl.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")  // Ersetze mit der tatsächlichen Frontend-URL

public class SongController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/songs")
    public ResponseEntity<List<SongProjection>> fetchSongs() {
        List<SongProjection> songs = songRepository.findAllProjectedBy();
        return ResponseEntity.ok(songs);
    }

    //wird gespeichert
    @PostMapping("/songs")
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        try {
            Song newSong = songRepository.save(song);
            return new ResponseEntity<>(newSong, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSong(@PathVariable Long id) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            song.get().setDataUrl(null);  // verhindert Senden von Song nDaten an Client
            return ResponseEntity.ok(song.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    //nur wenn man abspielt
    @GetMapping("/songs/data/{id}") //es soll /songs/id/data sein
    public ResponseEntity<String> getSongData(@PathVariable Long id) {
        Optional<Song> song = songRepository.findById(id);
        return song.map(value -> ResponseEntity.ok(value.getDataUrl()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
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
            song.setDataUrl(songDetails.getDataUrl());
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
}
