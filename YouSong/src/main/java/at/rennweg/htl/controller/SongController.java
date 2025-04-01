package at.rennweg.htl.controller;

import at.rennweg.htl.entity.Song;
import at.rennweg.htl.repository.SongRepository;
import at.rennweg.htl.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class SongController {

    private final SongRepository songRepository;
    private final SongService songService;

    @Autowired
    public SongController(SongRepository songRepository, SongService songService) {
        this.songRepository = songRepository;
        this.songService = songService;
    }

    @GetMapping("/songs")
    public ResponseEntity<Page<Song>> fetchSongs(@RequestParam(defaultValue = "0") int page) { //page
        Page<Song> songs = songService.getSongs(page);
        return ResponseEntity.ok(songs);
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

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSong(@PathVariable Long id) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            song.get().setDataUrl(null);
            return ResponseEntity.ok(song.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/songs/data/{id}")
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
