package clipperms.collection.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AddRoleToUserRequest {

    private String username;

    private String roleName;
}
