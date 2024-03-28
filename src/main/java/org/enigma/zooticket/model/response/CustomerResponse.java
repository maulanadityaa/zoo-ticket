package org.enigma.zooticket.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enigma.zooticket.constant.EStatus;
import org.enigma.zooticket.model.entity.User;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String dateOfBirth;
    private EStatus status;
    private UserResponse userCredential;
}
