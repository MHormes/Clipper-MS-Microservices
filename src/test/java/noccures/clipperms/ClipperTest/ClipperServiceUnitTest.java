package noccures.clipperms.ClipperTest;

import noccures.clipperms.data.interfaces.IClipperDataSource;
import noccures.clipperms.exceptions.ExceptionMessages;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.service.ClipperService;
import noccures.clipperms.service.interfaces.IClipperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
class ClipperServiceUnitTest {

    //Mocked db to ensure test only include service logic
    @Mock
    IClipperDataSource clipperDataSource;

    IClipperService clipperService;

    String clipperId = "b7319afb-946e-410d-a6f0-e72d7c3c314e";

    @BeforeEach
    void setUp() {
        clipperService = new ClipperService(clipperDataSource);
        when(clipperDataSource.getClipperWithId(UUID.fromString(clipperId))).thenReturn(null);
    }

    //Test if add method throws exception when clipper has empty name.
    @Test
    void addNewClipperEmptyName() {
        Clipper clipperToAdd = new Clipper("", null, 0, null);

        Clipper expectedResult = null;
        try {
            expectedResult = clipperService.addClipper(clipperToAdd, null);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.CLIPPER_NO_NAME, ex.getMessage());
        }

        //Check that no clipper value gets returned when exception is thrown.
        Assertions.assertNull(expectedResult);
    }

    @Test
    void addNewClipperNonExistingSeries(){
        //SeriesId set to null -> conversion from dto to model sets this value. Controller sends actual seriesId separately to add method
        Clipper clipperToAdd = new Clipper("new clipper", null, 0, null);

        Clipper expectedResult = null;
        try{
            expectedResult = clipperService.addClipper(clipperToAdd, "a9c516e4-1f15-473a-bf9b-ec750a4ed691");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            Assertions.assertEquals(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + "a9c516e4-1f15-473a-bf9b-ec750a4ed691", ex.getMessage());
        }

        //Check no clipper value gets returned from add
        Assertions.assertNull(expectedResult);
    }

    //Test if Get by id throws exception when clipper does not exist.
    @Test
    void getClipperWithIdNonExisting() {
        Clipper expectedResult = null;
        try {
            expectedResult = clipperService.getClipperWithId(clipperId);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.CLIPPER_WITH_ID_NOT_FOUND + clipperId, ex.getMessage());
        }

        //Check that no clipper value gets returned when exception is thrown.
        Assertions.assertNull(expectedResult);
    }

    //Test if exception is thrown when update clipper with non-existing id
    @Test
    void updateClipperNonExisting(){
        //Create clipper as if it comes from FE. Assign non-existing id for test -> will be assigned in FE normally
        Clipper clipperWithUpdate = new Clipper("updated clipper", null, 0, null);
        clipperWithUpdate.setId(UUID.fromString("b7319afb-946e-410d-a6f0-e72d7c3c314e"));

        Clipper expectedResult = null;
        try {
            expectedResult = clipperService.updateClipper(clipperWithUpdate);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.CLIPPER_WITH_ID_NOT_FOUND + clipperId, ex.getMessage());
        }

        //Check that no clipper value gets returned when exception is thrown.
        Assertions.assertNull(expectedResult);
    }

    //Test if exception is thrown when remove clipper with non-existing id
    @Test
    void deleteClipperNonExisting(){
        try{
            clipperService.deleteClipper(clipperId);
        }catch (Exception ex){
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.CLIPPER_WITH_ID_NOT_FOUND + clipperId, ex.getMessage());
        }
    }
}
