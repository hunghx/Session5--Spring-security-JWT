package ra.service;

import ra.entity.Role;
import ra.entity.RoleName;

public interface IRoleService {
    Role findByRoleName(RoleName roleName);
}
