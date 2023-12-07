package clipperms.collection.service.interfaces;

import clipperms.collection.model.AppRole;
import clipperms.collection.model.AppUser;

import java.util.List;

public interface IAppUserService {


    AppUser saveUser(AppUser user);

    AppRole saveRole(AppRole role);

    void addRoleToUser(String username, String roleName);
}
