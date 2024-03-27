package tech.xserver.adminserver.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.xserver.adminserver.entity.MovieEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewsDto {
    private Long id;
    private Long UserId;
    private Long MovieId;
    private String created_at;
}
