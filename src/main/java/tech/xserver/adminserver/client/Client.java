package tech.xserver.adminserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.xserver.adminserver.DTO.MovieDto;
import tech.xserver.adminserver.DTO.WrapperDto;

@FeignClient(name = "movieWrapper")
public interface Client {
    @RequestMapping("/movies")
    ResponseEntity<WrapperDto> getTrending(@RequestParam("page") Integer page, @RequestParam("size") Integer size);

    @RequestMapping("/movie/{id}")
    ResponseEntity<MovieDto> getMovie(@PathVariable("id") Long page);
}
