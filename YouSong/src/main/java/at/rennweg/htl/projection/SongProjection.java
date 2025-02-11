package at.rennweg.htl.projection;

public interface SongProjection {
    long getId();
    String getTitle();
    String getArtist();
    String getGenre();
    double getLength();
}
