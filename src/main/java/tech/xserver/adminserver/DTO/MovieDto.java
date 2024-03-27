package tech.xserver.adminserver.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieDto {
    private Long id;
    private String title;
    private String name;
    private String original_language;
    private String original_title;
    private String original_name;
    private String overview;
    private String poster_path;
    private String media_type;
    private double popularity;
    private String release_date;
    private String first_air_date;
    private boolean video;
    private double vote_average;
    private int vote_count;
    private boolean adult;
    private String backdrop_path;
    private String type;
//    private CertificationDto certification;
}
