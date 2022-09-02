package noccures.clipperms.service.interfaces;

import noccures.clipperms.model.AppRole;
import noccures.clipperms.model.AppUser;

import java.util.List;

public interface IAppUserService {

    AppUser saveUser(AppUser user);

    AppRole saveRole(AppRole role);

    void addRoleToUser(String username, String roleName);

    AppUser getUser(String username);

    List<AppUser> getUsers();
}
