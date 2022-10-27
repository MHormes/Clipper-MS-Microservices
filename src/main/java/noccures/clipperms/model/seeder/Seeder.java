package noccures.clipperms.model.seeder;

import lombok.AllArgsConstructor;
import noccures.clipperms.model.AppRole;
import noccures.clipperms.model.AppUser;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.Series;
import noccures.clipperms.service.AppUserService;
import noccures.clipperms.service.ClipperService;
import noccures.clipperms.service.interfaces.IAppUserService;
import noccures.clipperms.service.interfaces.IClipperService;
import noccures.clipperms.service.interfaces.ISeriesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.UUID;

@Configuration
@AllArgsConstructor
public class Seeder {

    @Bean
    CommandLineRunner run(IAppUserService userService, IClipperService clipperService, ISeriesService seriesService) {
        return args -> {

            AppRole adminRole = new AppRole(UUID.randomUUID(), "ROLE_ADMIN");
            AppRole userRole = new AppRole(UUID.randomUUID(), "ROLE_USER");
            AppRole superAdminRole = new AppRole(UUID.randomUUID(), "ROLE_SUPER_ADMIN");

            userService.saveRole(adminRole);
            userService.saveRole(userRole);
            userService.saveRole(superAdminRole);

            AppUser superAdmin = new AppUser(UUID.fromString("b1e7544d-b5bc-429a-83c8-8b222ae2519f"), "Maarten Hormes", "MHormes", "1234", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            AppUser admin = new AppUser(UUID.fromString("9354e367-d749-4cf1-bbd8-c778d807eb7d"), "John Doe", "admin", "admin", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            AppUser user = new AppUser(UUID.fromString("66127a88-438e-4b88-ae4a-0f33218aead9"), "Pietje Bel", "user", "user", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            userService.saveUser(superAdmin);
            userService.saveUser(admin);
            userService.saveUser(user);

            userService.addRoleToUser("MHormes", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("admin", "ROLE_USER");


            Series skateSeries = new Series(UUID.fromString("962b7877-0d71-4e7d-bb34-24a12dd4617d"), "Skate Boards", new ArrayList<>(), superAdmin, false);
            Series mascotteSeries = new Series(UUID.fromString("bf3a1d99-016a-4da8-bd57-70fb7b3a8d75"), "Mascotte", new ArrayList<>(), admin, true);
            seriesService.addSeries(skateSeries);
            seriesService.addSeries(mascotteSeries);


            Clipper skate1 = new Clipper(UUID.fromString("fa1fc0fb-372b-4b4a-b998-cb7eda404af9"), "Skate Boards 1", null, 1, superAdmin);
            Clipper skate2 = new Clipper(UUID.fromString("943914cb-f741-4dc2-ad30-5d1b9b2feaf8"), "Skate Boards 2", null, 2, superAdmin);
            Clipper skate3 = new Clipper(UUID.fromString("e14022a3-45f9-499c-af65-dc0a580d3ab9"), "Skate Boards 3", null, 3, superAdmin);
            Clipper skate4 = new Clipper(UUID.fromString("4d0ac2bb-9a8a-4881-8d38-f886849037c6"), "Skate Boards 4", null, 4, superAdmin);
            Clipper mascotte = new Clipper(UUID.fromString("da683ef1-a8bc-4791-b05e-010fd38b12fc"), "Mascotte M",null, 1, admin);
            Clipper noSeries = new Clipper(UUID.fromString("4d581847-e3e9-4d06-b116-a22b82794fc5"), "Neon leaf",null, 1, superAdmin);

            clipperService.addClipper(skate1, skateSeries.getId().toString());
            clipperService.addClipper(skate2, skateSeries.getId().toString());
            clipperService.addClipper(skate3, skateSeries.getId().toString());
            clipperService.addClipper(skate4, skateSeries.getId().toString());
            clipperService.addClipper(mascotte, mascotteSeries.getId().toString());
            clipperService.addClipper(noSeries, null);
        };
    }

}
