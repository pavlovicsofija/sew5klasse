package at.rennweg.htl.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

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

    @NotBlank(message = "Der Titel darf nicht leer sein.")
    private String title;

    @NotBlank(message = "Der Künstlername darf nicht leer sein.")
    private String artist;

    @NotBlank(message = "Das Genre darf nicht leer sein.")
    private String genre;

    @Positive(message = "Die Länge muss eine positive Zahl sein.")
    private double length;

    @Column(name = "data_url", columnDefinition = "LONGTEXT")
    private String dataUrl;  // Musikdatei wird hier gespeichert
}