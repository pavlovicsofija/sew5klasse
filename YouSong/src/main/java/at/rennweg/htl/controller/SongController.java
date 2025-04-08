package at.rennweg.htl.controller;

import at.rennweg.htl.dto.SongDTO;
import at.rennweg.htl.entity.Song;
import at.rennweg.htl.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")  // Ersetze mit der tatsächlichen Frontend-URL

public class SongController {

    private final SongRepository songRepository;
    private final SongService songService;

    private final SongRepository songRepository;
    private final SongService songService;

>>>>>>> 604965f74d51d230ffa22ac0328b8c8d0e51bcb0
    @Autowired
    public SongController(SongRepository songRepository, SongService songService) {
        this.songRepository = songRepository;
        this.songService = songService;
    }

    @GetMapping("/songs")
    public ResponseEntity<List<SongProjection>> fetchSongs() {
        List<SongProjection> songs = songRepository.findAllProjectedBy();
        return ResponseEntity.ok(songs);
    }

    //wird gespeichert
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
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    //nur wenn man abspielt
    @GetMapping("/songs/data/{id}") //es soll /songs/id/data sein
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