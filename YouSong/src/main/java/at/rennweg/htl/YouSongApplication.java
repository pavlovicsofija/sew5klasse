package at.rennweg.htl;

import at.rennweg.htl.entity.Song;
import at.rennweg.htl.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YouSongApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(YouSongApplication.class, args);
	}

	@Autowired
	private SongRepository songRepository;

	@Override
	public void run(String... args) throws Exception {

		Song song1 = Song.builder()
				.title("Baby")
				.artist("Justin Bieber")
				.genre("Pop")
				.length(2.44)
				.build();

		Song song2 = Song.builder()
				.title("TikTok")
				.artist("Kesha")
				.genre("Pop")
				.length(3.224)
				.build();

		Song song3 = Song.builder()
				.title("DNA")
				.artist("BTS")
				.genre("K-Pop")
				.length(5.3255)
				.build();


		songRepository.save(song1);
		songRepository.save(song2);
		songRepository.save(song3);

	}
}
