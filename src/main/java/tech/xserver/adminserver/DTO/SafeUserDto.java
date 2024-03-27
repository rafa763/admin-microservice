package tech.xserver.adminserver.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SafeUserDto {
    private Long id;
    private String email;
    private String dob;
    private String created_at;
    private boolean active;
    private String role;
}