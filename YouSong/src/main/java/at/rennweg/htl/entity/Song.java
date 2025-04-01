package at.rennweg.htl.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "artist", nullable = false)
    private String artist;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "length", nullable = false)
    private double length;

    @Column(name = "data_url", columnDefinition = "LONGTEXT")
    private String dataUrl;  // Hier wird die Musikdatei referenziert
}
