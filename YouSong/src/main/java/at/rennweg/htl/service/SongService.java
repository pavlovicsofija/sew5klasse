package at.rennweg.htl.service;

import at.rennweg.htl.entity.Song;
import at.rennweg.htl.repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Page<Song> getSongs(int page) {
        return songRepository.findAll(PageRequest.of(page, 5)); //nur 5 pro seite
    }
}
