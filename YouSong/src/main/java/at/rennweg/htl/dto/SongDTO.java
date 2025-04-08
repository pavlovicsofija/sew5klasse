package at.rennweg.htl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {
    private long id;

    @NotBlank(message = "Der Titel darf nicht leer sein.")
    private String title;

    @NotBlank(message = "Der Künstlername darf nicht leer sein.")
    private String artist;

    @NotBlank(message = "Das Genre darf nicht leer sein.")
    private String genre;

    @Positive(message = "Die Länge muss eine positive Zahl sein.")
    private double length;
}