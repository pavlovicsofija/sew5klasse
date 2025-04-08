package at.rennweg.htl.controller;

import at.rennweg.htl.dto.SongDTO;
import at.rennweg.htl.entity.Song;
import at.rennweg.htl.repository.SongRepository;
<<<<<<< HEAD
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
=======
import at.rennweg.htl.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
>>>>>>> 604965f74d51d230ffa22ac0328b8c8d0e51bcb0
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD
import java.io.IOException;
import java.util.List;
=======
>>>>>>> 604965f74d51d230ffa22ac0328b8c8d0e51bcb0
import java.util.Optional;

@RestController
@RequestMapping("/api")
<<<<<<< HEAD
@CrossOrigin(origins = "http://localhost:5173")
public class SongController {
=======
@CrossOrigin(origins = "http://localhost:8081")
public class SongController {

    private final SongRepository songRepository;
    private final SongService songService;

>>>>>>> 604965f74d51d230ffa22ac0328b8c8d0e51bcb0
    @Autowired
    public SongController(SongRepository songRepository, SongService songService) {
        this.songRepository = songRepository;
        this.songService = songService;
    }

    @GetMapping("/songs")
<<<<<<< HEAD
    public List<SongDTO> fetchSongs() {
        return songRepository.findAll().stream()
                .map(song -> new SongDTO(song.getId(), song.getTitle(), song.getArtist(), song.getGenre(), song.getLength()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        return songOptional.map(song -> ResponseEntity.ok().body(song))
                .orElse(ResponseEntity.notFound().build());
    }
=======
    public ResponseEntity<Page<Song>> fetchSongs(@RequestParam(defaultValue = "0") int page) { //page
        Page<Song> songs = songService.getSongs(page);
        return ResponseEntity.ok(songs);
    }

>>>>>>> 604965f74d51d230ffa22ac0328b8c8d0e51bcb0
    @PostMapping("/songs")
    public ResponseEntity<Object> createSong(@RequestParam(required = false) MultipartFile file, @RequestParam double length, @RequestBody SongDTO songDTO) throws IOException {
        if (file == null) {
            return ResponseEntity.badRequest().body("Fehler: Keine Datei hochgeladen.");
        }

<<<<<<< HEAD
        if (length <= 0) {
            return ResponseEntity.badRequest().body("Fehler: Ungültige Länge.");
=======
    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSong(@PathVariable Long id) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            song.get().setDataUrl(null);
            return ResponseEntity.ok(song.get());
>>>>>>> 604965f74d51d230ffa22ac0328b8c8d0e51bcb0
        }

<<<<<<< HEAD
        // Hier kannst du dann den Song speichern
        Song song = new Song();
        song.setTitle(songDTO.getTitle());
        song.setArtist(songDTO.getArtist());
        song.setGenre(songDTO.getGenre());
        song.setLength(songDTO.getLength());
        song.setFileData(new String(file.getBytes()));  // Speichert die Datei als String (je nach Bedarf anpassen)

        Song savedSong = songRepository.save(song);
        return ResponseEntity.ok(savedSong);
=======
    @GetMapping("/songs/data/{id}")
    public ResponseEntity<String> getSongData(@PathVariable Long id) {
        Optional<Song> song = songRepository.findById(id);
        return song.map(value -> ResponseEntity.ok(value.getDataUrl()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
>>>>>>> 604965f74d51d230ffa22ac0328b8c8d0e51bcb0
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<Object> updateSong(@PathVariable Long id, @RequestParam(required = false) MultipartFile file, @RequestParam double length, @RequestBody SongDTO songDTO) throws IOException {
        if (file == null) {
            return ResponseEntity.badRequest().body("Fehler: Keine Datei hochgeladen.");
        }

        if (length <= 0) {
            return ResponseEntity.badRequest().body("Fehler: Ungültige Länge.");
        }

        Optional<Song> existingSongOptional = songRepository.findById(id);
        if (existingSongOptional.isPresent()) {
            Song existingSong = existingSongOptional.get();
            existingSong.setTitle(songDTO.getTitle());
            existingSong.setArtist(songDTO.getArtist());
            existingSong.setGenre(songDTO.getGenre());
            existingSong.setLength(songDTO.getLength());
            existingSong.setFileData(new String(file.getBytes())); // Aktualisiert die Datei (je nach Bedarf anpassen)

            songRepository.save(existingSong);
            return ResponseEntity.ok(existingSong);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        if (songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/songs/search")
    public List<Song> searchSongs(@RequestParam String query) {
        return songRepository.findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCase(query, query);
    }

    @GetMapping("/songs/{id}/play")
    public ResponseEntity<String> getSongFileData(@PathVariable Long id) {
        SongProjection songFile = songRepository.findFileDataById(id);
        if (songFile != null) {
            return ResponseEntity.ok(songFile.getFileData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/songs/page")
    public ResponseEntity<Page<SongDTO>> fetchSongsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Song> songsPage = songRepository.findAll(PageRequest.of(page, size));
        Page<SongDTO> songDTOPage = songsPage.map(song -> new SongDTO(
                song.getId(),
                song.getTitle(),
                song.getArtist(),
                song.getGenre(),
                song.getLength()
        ));
        return ResponseEntity.ok(songDTOPage);
    }


}