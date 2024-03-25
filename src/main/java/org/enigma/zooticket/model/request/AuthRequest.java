package org.enigma.zooticket.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enigma.zooticket.constant.ERole;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
    private String fullName;
    private String mobilePhone;
    private String email;
    private String dateOfBirth;
    private ERole role;
}
