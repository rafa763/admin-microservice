package tech.xserver.adminserver.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.xserver.adminserver.entity.MovieEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VotesDto {
    private Long id;
    private Long userId;
    private Long movieId;
    private String created_at;
}
