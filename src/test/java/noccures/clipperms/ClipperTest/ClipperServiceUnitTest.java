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

import static org.mockito.Mockito.when;

@SpringBootTest
class ClipperServiceUnitTest {

    //Mocked db to ensure test only include service logic
    @Mock
    IClipperDataSource clipperDataSource;

    IClipperService clipperService;

    @BeforeEach
    void setUp() {
        clipperService = new ClipperService(clipperDataSource);
        when(clipperDataSource.getClipperWithId("1")).thenReturn(null);
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
            expectedResult = clipperService.addClipper(clipperToAdd, "non-existing");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            Assertions.assertEquals(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + "non-existing", ex.getMessage());
        }

        //Check no clipper value gets returned from add
        Assertions.assertNull(expectedResult);
    }

    //Test if Get by id throws exception when clipper does not exist.
    @Test
    void getClipperWithIdNonExisting() {
        Clipper expectedResult = null;
        try {
            expectedResult = clipperService.getClipperWithId("1");
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.CLIPPER_WITH_ID_NOT_FOUND + "1", ex.getMessage());
        }

        //Check that no clipper value gets returned when exception is thrown.
        Assertions.assertNull(expectedResult);
    }

    //Test if exception is thrown when update clipper with non-existing id
    @Test
    void updateClipperNonExisting(){
        //Create clipper as if it comes from FE. Assign non-existing id for test -> will be assigned in FE normally
        Clipper clipperWithUpdate = new Clipper("updated clipper", null, 0, null);
        clipperWithUpdate.setId("1");

        Clipper expectedResult = null;
        try {
            expectedResult = clipperService.updateClipper(clipperWithUpdate);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.CLIPPER_WITH_ID_NOT_FOUND + "1", ex.getMessage());
        }

        //Check that no clipper value gets returned when exception is thrown.
        Assertions.assertNull(expectedResult);
    }

    //Test if exception is thrown when remove clipper with non-existing id
    @Test
    void deleteClipperNonExisting(){
        try{
            clipperService.deleteClipper("1");
        }catch (Exception ex){
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.CLIPPER_WITH_ID_NOT_FOUND + "1", ex.getMessage());
        }
    }
}
