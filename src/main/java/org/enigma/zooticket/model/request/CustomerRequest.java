package org.enigma.zooticket.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enigma.zooticket.model.entity.User;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String id;
    private String fullName;
    private String phone;
    private String email;
    private String dateOfBirth;
    private User user;
}
