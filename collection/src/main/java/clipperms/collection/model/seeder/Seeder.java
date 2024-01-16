package clipperms.collection.model.seeder;


import clipperms.collection.exceptions.IncorrectInputException;
import clipperms.collection.model.Clipper;
import clipperms.collection.model.CollectedClipper;
import clipperms.collection.model.Series;
import clipperms.collection.service.interfaces.IClipperService;
import clipperms.collection.service.interfaces.ICollectedClipperService;
import clipperms.collection.service.interfaces.ISeriesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Profile("development")
@Configuration
@AllArgsConstructor
@Slf4j
public class Seeder {

    private static final String ROERMOND = "Roermond";
    private static final String LOMMEL = "Lommel";
    private static final String NIJMEGEN = "Nijmegen";

    @Bean
    CommandLineRunner run(IClipperService clipperService, ISeriesService seriesService, ICollectedClipperService collectedClipperService) {
        return args -> {
            try {
                if (clipperService.getClipperWithId("fa1fc0fb-372b-4b4a-b998-cb7eda404af9") != null) {
                    log.info("Seeder already ran");
                }
            } catch (IncorrectInputException nonExistingClipper) {
                log.info("Clipper not found, running seeder");
                                Map<String, byte[]> imageMap = getImageMap();

                Series skateSeries = new Series(UUID.fromString("962b7877-0d71-4e7d-bb34-24a12dd4617d"), "Skate Boards", new ArrayList<>(), imageMap.get("clipper_boarding - complete.png"), UUID.fromString("8abd08f3-02ae-48af-a552-fe19b552d867"), false);
                Series mascotteSeries = new Series(UUID.fromString("bf3a1d99-016a-4da8-bd57-70fb7b3a8d75"), "Mascotte", new ArrayList<>(), imageMap.get("clipper_mascotte - series.jpg"), UUID.fromString("8abd08f3-02ae-48af-a552-fe19b552d867"), true);
                seriesService.addSeries(skateSeries);
                seriesService.addSeries(mascotteSeries);

                Clipper skate1 = new Clipper(UUID.fromString("fa1fc0fb-372b-4b4a-b998-cb7eda404af9"), "Skate Boards 1", null, 1, imageMap.get("clipper_boarding- 1.jpg"), UUID.fromString("8abd08f3-02ae-48af-a552-fe19b552d867"));
                Clipper skate2 = new Clipper(UUID.fromString("943914cb-f741-4dc2-ad30-5d1b9b2feaf8"), "Skate Boards 2", null, 2, imageMap.get("clipper_boarding - 2.jpg"), UUID.fromString("8abd08f3-02ae-48af-a552-fe19b552d867"));
                Clipper skate3 = new Clipper(UUID.fromString("e14022a3-45f9-499c-af65-dc0a580d3ab9"), "Skate Boards 3", null, 3, imageMap.get("clipper_boarding - 3.jpg"), UUID.fromString("8abd08f3-02ae-48af-a552-fe19b552d867"));
                Clipper skate4 = new Clipper(UUID.fromString("4d0ac2bb-9a8a-4881-8d38-f886849037c6"), "Skate Boards 4", null, 4, imageMap.get("clipper_boarding - 4.jpg"), UUID.fromString("8abd08f3-02ae-48af-a552-fe19b552d867"));
                Clipper mascotte = new Clipper(UUID.fromString("da683ef1-a8bc-4791-b05e-010fd38b12fc"), "Mascotte M", null, 1, imageMap.get("clipper_mascotte - 5.jpg"), UUID.fromString("8abd08f3-02ae-48af-a552-fe19b552d867"));
                Clipper noSeries = new Clipper(UUID.fromString("4d581847-e3e9-4d06-b116-a22b82794fc5"), "Neon leaf", null, 1, imageMap.get("clipper-aansteker-cut.jpg"), UUID.fromString("8abd08f3-02ae-48af-a552-fe19b552d867"));

                clipperService.addClipper(noSeries, null);

                clipperService.addClipper(skate1, skateSeries.getId().toString());
                clipperService.addClipper(skate2, skateSeries.getId().toString());
                clipperService.addClipper(skate3, skateSeries.getId().toString());
                clipperService.addClipper(skate4, skateSeries.getId().toString());
                clipperService.addClipper(mascotte, mascotteSeries.getId().toString());

//                UUID superAdminId = UUID.fromString("8abd08f3-02ae-48af-a552-fe19b552d867");
//                UUID adminId = UUID.fromString("5727899c-7dbf-4feb-9dec-800d0996b653");
//
//                CollectedClipper cSkate1 = new CollectedClipper(UUID.fromString("489f550a-4612-43e8-a36c-1a88925ed81e"), skate1, superAdminId, "", LocalDate.now(), ROERMOND);
//                CollectedClipper cSkate2 = new CollectedClipper(UUID.fromString("5d4861b4-ead4-49f2-bd80-a069556e7aac"), skate2, superAdminId,"", LocalDate.now(), ROERMOND);
//                CollectedClipper cSkate3 = new CollectedClipper(UUID.fromString("8f0b5659-b3a3-42c6-9e61-260461d507a4"), skate3, superAdminId,  "", LocalDate.now(), ROERMOND);
//                CollectedClipper cSkate4 = new CollectedClipper(UUID.fromString("0bd1f4ee-a230-4658-a846-7cbb4ef4e0fd"), skate4, superAdminId, "", LocalDate.now(), ROERMOND);
//                CollectedClipper cMascotte1 = new CollectedClipper(UUID.fromString("45b3d63c-2bdc-48c0-9f92-5472ef1c7295"), mascotte, superAdminId, "", LocalDate.now(), LOMMEL);
//                CollectedClipper cMascotte2 = new CollectedClipper(UUID.fromString("23f8248b-d3d4-4a78-9694-b847cc13ea6b"), mascotte, adminId, "", LocalDate.now(), LOMMEL);
//                CollectedClipper cNoSeries = new CollectedClipper(UUID.fromString("25bc8b10-a90c-4f9a-aab2-a6e0e2ebaec0"), skate1, adminId, "", LocalDate.now(), NIJMEGEN);
//
//                collectedClipperService.addCollectedClipper(cSkate1);
//                collectedClipperService.addCollectedClipper(cSkate2);
//                collectedClipperService.addCollectedClipper(cSkate3);
//                collectedClipperService.addCollectedClipper(cSkate4);
//                collectedClipperService.addCollectedClipper(cMascotte1);
//                collectedClipperService.addCollectedClipper(cMascotte2);
//                collectedClipperService.addCollectedClipper(cNoSeries);

            } catch (Exception ex) {
                log.error("Seeder failed on: " + ex.getMessage());
            }
        };
    }

    /**
     * Method to extract all images from a directory and return them as a map
     *
     * @return List<Clipper>
     */
    private Map<String, byte[]> getImageMap() {
        String directoryPath = "./globalAssets/images";
        Path directory = Paths.get(directoryPath);

        Map<String, byte[]> imageMap = new HashMap<>();

        try {
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
                            log.error("Error while reading file: " + e.getMessage());
                        }
                    });
        } catch (Exception e) {
            log.error("Error while getting image byte[] directory: " + e.getMessage());
        }
        return imageMap;
    }
}
