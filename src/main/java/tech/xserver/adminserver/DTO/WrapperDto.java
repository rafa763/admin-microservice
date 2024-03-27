package tech.xserver.adminserver.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WrapperDto {
    private Integer page;
    private List<MovieDto> results;
    private Integer total_pages;
    private Integer total_results;

}
