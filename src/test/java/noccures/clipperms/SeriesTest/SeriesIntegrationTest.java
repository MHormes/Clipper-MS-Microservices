package noccures.clipperms.SeriesTest;

import noccures.clipperms.data.interfaces.ISeriesDataSource;
import noccures.clipperms.exceptions.ExceptionMessages;
import noccures.clipperms.model.Series;
import noccures.clipperms.service.SeriesService;
import noccures.clipperms.service.interfaces.ISeriesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class SeriesIntegrationTest {

    //Autowired db in test config to test logic and data layer integration
    @Autowired
    ISeriesDataSource seriesDataSource;

    ISeriesService seriesService;

    @BeforeEach
    void setUp() {
        seriesService = new SeriesService(seriesDataSource);
    }

    //Test adding new series. Asserts based on inputted name and name returned by db after add
    @Test
    void addNewSeriesSuccessfulTest() {
        //Create series to add
        Series seriesToAdd = new Series("Love letter", false, false);

        //Make new series instance to get db return
        Series databaseReturn = new Series();
        try {
            databaseReturn = seriesService.addSeries(seriesToAdd);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Check if name of inserted series and db return are the same
        Assertions.assertEquals(seriesToAdd.getName(), databaseReturn.getName());
    }

    //Test if correct series is fetched from db. Asserts based on name of added series and name of series gotten by id.
    @Test
    void getSeriesWithIdSuccessfulTest() {
        //Create series to add and get after adding (no series added means no series to get)
        Series seriesToAdd = new Series("Weed Letters", false, false);
        //Make new series instance to get db return
        Series databaseAddReturn = new Series();
        try {
            databaseAddReturn = seriesService.addSeries(seriesToAdd);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Get series with id from db
        Series databaseGetReturn = new Series();
        try {
            databaseGetReturn = seriesService.getSeriesWithId(databaseAddReturn.getId());
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Check if return of adding series has same name as return of getting series with id
        Assertions.assertEquals(databaseAddReturn.getName(), databaseGetReturn.getName());
    }

    //Test update series. Assert based on inputted data and db return value after update
    @Test
    void updateSeriesSuccessful(){
        //Create series to add
        Series seriesToAdd = new Series("Hippie mandala", false,  false);

        //Make new series instance to get db return
        Series databaseAddReturn = new Series();
        try {
            databaseAddReturn = seriesService.addSeries(seriesToAdd);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //create series with update values. Set id manually -> normally gets taken from FE
        Series seriesWithUpdate = new Series("new name", false, true);
        seriesWithUpdate.setId(databaseAddReturn.getId());

        //Make new series instance to get db return
        Series databaseUpdateReturn = new Series();
        try {
            databaseUpdateReturn = seriesService.updateSeries(seriesWithUpdate);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Assert if seriesWithUpdate value is the same as return after update. Check if complete is no longer false -> now true
        Assertions.assertEquals(seriesWithUpdate.getName(), databaseUpdateReturn.getName());
        Assertions.assertTrue(databaseUpdateReturn.isComplete());
    }

    //Test delete series. Assertion based on getting series with id after its being deleted -> not found? -> successful delete :)
    @Test
    void deleteSeriesSuccessful(){
        //Create series to add and delete
        Series seriesToAdd = new Series("60's drawings", false, false);

        //Make new series instance to get db return
        Series databaseAddReturn = new Series();
        try {
            databaseAddReturn = seriesService.addSeries(seriesToAdd);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Delete the series
        try{
            seriesService.deleteSeries(databaseAddReturn.getId());
        }
        catch (Exception ex){
            System.out.println(ex);
        }

        //Get series with id from db
        Series databaseGetReturn = null;
        try {
            databaseGetReturn = seriesService.getSeriesWithId(databaseAddReturn.getId());
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + databaseAddReturn.getId(), ex.getMessage());
        }

        //Check if return of getting series after delete is null
        Assertions.assertNull(databaseGetReturn);

    }
}
