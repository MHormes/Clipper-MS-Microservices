package clipperms.collection.model.seeder;

import clipperms.collection.model.*;
import clipperms.collection.service.interfaces.IAppUserService;
import clipperms.collection.service.interfaces.IClipperService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import clipperms.collection.model.*;
import clipperms.collection.service.interfaces.ICollectedClipperService;
import clipperms.collection.service.interfaces.ISeriesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Profile("development")
@Configuration
@AllArgsConstructor
@Slf4j
public class Seeder {

    private static final String roermond = "Roermond";
    private static final String lommel = "Lommel";
    private static final String nijmwegen = "Nijmegen";

    @Bean
    CommandLineRunner run(IAppUserService userService, IClipperService clipperService, ISeriesService seriesService, ICollectedClipperService collectedClipperService) {
        return args -> {
            //todo configure seeder to check if data already present
            try {
                AppRole adminRole = new AppRole(UUID.fromString("52791c01-1e24-4786-9a5a-afbbc6f2c9a5"), "ROLE_ADMIN");
                AppRole userRole = new AppRole(UUID.fromString("74874e01-7f83-42ac-ac66-3710104beca6"), "ROLE_USER");
                AppRole superAdminRole = new AppRole(UUID.fromString("b3212c05-03f9-4778-b9b6-4e3b99512ca9"), "ROLE_SUPER_ADMIN");

                userService.saveRole(adminRole);
                userService.saveRole(userRole);
                userService.saveRole(superAdminRole);

                AppUser superAdmin = new AppUser(UUID.fromString("b1e7544d-b5bc-429a-83c8-8b222ae2519f"), "Maarten Hormes", "MHormes", "1234", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                AppUser admin = new AppUser(UUID.fromString("9354e367-d749-4cf1-bbd8-c778d807eb7d"), "Sten Ruijten", "SRuijten", "1234", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                AppUser manon = new AppUser(UUID.fromString("66127a88-438e-4b88-ae4a-0f33218aead9"), "Manon Olde", "MOlde", "1234", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                AppUser joy = new AppUser(UUID.fromString("4317068d-136e-4518-b81f-aa5a520f429f"), "Joy schmitz", "JSchmitz", "1234", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

                userService.saveUser(superAdmin);
                userService.saveUser(admin);
                userService.saveUser(manon);
                userService.saveUser(joy);

                userService.addRoleToUser("MHormes", "ROLE_SUPER_ADMIN");
                userService.addRoleToUser("SRuijten", "ROLE_USER");

                String directoryPath = "./globalAssets/images";
                Path directory = Paths.get(directoryPath);

                Map<String, byte[]> imageMap = new HashMap<>();

                try{
                    // Iterate over each file in the directory
                    Files.walk(directory)
                            .filter(Files::isRegularFile)
                            .forEach(file -> {
                                try {
                                    // Read the file content into a byte array
                                    byte[] imageBytes = Files.readAllBytes(file);
                                    String name = file.getFileName().toString();

                                    imageMap.put(name, imageBytes);

                                } catch (IOException e) {
                                    // Handle exceptions if any occurred during the process
                                    e.printStackTrace();
                                }
                            });
                }catch (Exception e){
                    log.error("Error while getting image byte[] directory: " + e.getMessage());
                }


                Series skateSeries = new Series(UUID.fromString("962b7877-0d71-4e7d-bb34-24a12dd4617d"), "Skate Boards", new ArrayList<>(), imageMap.get("clipper_boarding - complete.png"), superAdmin, false);
                Series mascotteSeries = new Series(UUID.fromString("bf3a1d99-016a-4da8-bd57-70fb7b3a8d75"), "Mascotte", new ArrayList<>(), imageMap.get("clipper_mascotte - series.jpg"), admin, true);
                seriesService.addSeries(skateSeries);
                seriesService.addSeries(mascotteSeries);

                Clipper skate1 = new Clipper(UUID.fromString("fa1fc0fb-372b-4b4a-b998-cb7eda404af9"), "Skate Boards 1", null, 1, superAdmin, imageMap.get("clipper_boarding- 1.jpg"));
                Clipper skate2 = new Clipper(UUID.fromString("943914cb-f741-4dc2-ad30-5d1b9b2feaf8"), "Skate Boards 2", null, 2, superAdmin, imageMap.get("clipper_boarding - 2.jpg"));
                Clipper skate3 = new Clipper(UUID.fromString("e14022a3-45f9-499c-af65-dc0a580d3ab9"), "Skate Boards 3", null, 3, superAdmin, imageMap.get("clipper_boarding - 3.jpg"));
                Clipper skate4 = new Clipper(UUID.fromString("4d0ac2bb-9a8a-4881-8d38-f886849037c6"), "Skate Boards 4", null, 4, superAdmin, imageMap.get("clipper_boarding - 4.jpg"));
                Clipper mascotte = new Clipper(UUID.fromString("da683ef1-a8bc-4791-b05e-010fd38b12fc"), "Mascotte M", null, 1, admin, imageMap.get("clipper_mascotte - 5.jpg"));
                Clipper noSeries = new Clipper(UUID.fromString("4d581847-e3e9-4d06-b116-a22b82794fc5"), "Neon leaf", null, 1, superAdmin, imageMap.get("clipper-aansteker-cut.jpg"));

                clipperService.addClipper(noSeries, null);

                clipperService.addClipper(skate1, skateSeries.getId().toString());
                clipperService.addClipper(skate2, skateSeries.getId().toString());
                clipperService.addClipper(skate3, skateSeries.getId().toString());
                clipperService.addClipper(skate4, skateSeries.getId().toString());
                clipperService.addClipper(mascotte, mascotteSeries.getId().toString());

                CollectedClipper cSkate1 = new CollectedClipper(UUID.fromString("489f550a-4612-43e8-a36c-1a88925ed81e"), skate1, superAdmin, "", LocalDate.now(), roermond);
                CollectedClipper cSkate2 = new CollectedClipper(UUID.fromString("5d4861b4-ead4-49f2-bd80-a069556e7aac"), skate2, superAdmin, "", LocalDate.now(), roermond);
                CollectedClipper cSkate3 = new CollectedClipper(UUID.fromString("8f0b5659-b3a3-42c6-9e61-260461d507a4"), skate3, superAdmin, "", LocalDate.now(), roermond);
                CollectedClipper cSkate4 = new CollectedClipper(UUID.fromString("0bd1f4ee-a230-4658-a846-7cbb4ef4e0fd"), skate4, superAdmin, "", LocalDate.now(), roermond);
                CollectedClipper cMascotte1 = new CollectedClipper(UUID.fromString("45b3d63c-2bdc-48c0-9f92-5472ef1c7295"), mascotte, superAdmin, "", LocalDate.now(), lommel);
                CollectedClipper cMascotte2 = new CollectedClipper(UUID.fromString("23f8248b-d3d4-4a78-9694-b847cc13ea6b"), mascotte, admin, "", LocalDate.now(), lommel);
                CollectedClipper cNoSeries = new CollectedClipper(UUID.fromString("25bc8b10-a90c-4f9a-aab2-a6e0e2ebaec0"), skate1, admin, "", LocalDate.now(), nijmwegen);

                collectedClipperService.addCollectedClipper(cSkate1);
                collectedClipperService.addCollectedClipper(cSkate2);
                collectedClipperService.addCollectedClipper(cSkate3);
                collectedClipperService.addCollectedClipper(cSkate4);
                collectedClipperService.addCollectedClipper(cMascotte1);
                collectedClipperService.addCollectedClipper(cMascotte2);
                collectedClipperService.addCollectedClipper(cNoSeries);
            } catch (Exception ex) {
                log.error("DB already holds data. Seeder failed on: " + ex.getMessage());
             }
        };

    }
}
