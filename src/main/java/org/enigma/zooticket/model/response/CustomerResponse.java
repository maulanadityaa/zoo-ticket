package org.enigma.zooticket.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enigma.zooticket.model.entity.User;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private User userCredential;
}
