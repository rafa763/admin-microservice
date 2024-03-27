package tech.xserver.adminserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Mail {
    @JsonProperty("to")
    private String to;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("text")
    private String text;
}
