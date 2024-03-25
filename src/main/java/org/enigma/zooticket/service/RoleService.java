package org.enigma.zooticket.service;

import org.enigma.zooticket.constant.ERole;
import org.enigma.zooticket.model.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);

    Role getByName(ERole name);
}
