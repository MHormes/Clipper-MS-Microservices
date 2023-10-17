package clipperms.collection.SeriesTest;

import clipperms.collection.data.interfaces.ISeriesDataSource;
import clipperms.collection.model.AppUser;
import clipperms.collection.model.Series;
import clipperms.collection.service.SeriesService;
import clipperms.collection.service.interfaces.IAppUserService;
import clipperms.collection.service.interfaces.ISeriesService;
import clipperms.collection.exceptions.ExceptionMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
class SeriesIntegrationTest {

    //Autowired db in test config to test logic and data layer integration
    @Autowired
    ISeriesDataSource seriesDataSource;

    @Autowired
    ISeriesService seriesService;

    @Autowired
    IAppUserService appUserService;

    AppUser creator;

    @BeforeEach
    void setUp() {
        this.creator = new AppUser(UUID.randomUUID() ,"Maarten", "MHormes", "Hormes123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        appUserService.saveUser(creator);
    }

    //Test adding new series. Asserts based on inputted name and name returned by db after add
    @Test
    void addNewSeriesSuccessfulTest() {
        //Create series to add
        Series seriesToAdd = new Series("Love letter", false, creator);

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
        Series seriesToAdd = new Series("Weed Letters",  false, creator);
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
            databaseGetReturn = seriesService.getSeriesWithId(databaseAddReturn.getId().toString());
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
        Series seriesToAdd = new Series("Hippie mandala", false, creator);

        //Make new series instance to get db return
        Series databaseAddReturn = new Series();
        try {
            databaseAddReturn = seriesService.addSeries(seriesToAdd);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //create series with update values. Set id manually -> normally gets taken from FE (not included in create return)
        databaseAddReturn.setName("new name");

        //Make new series instance to get db return
        Series databaseUpdateReturn = new Series();
        try {
            databaseUpdateReturn = seriesService.updateSeries(databaseAddReturn);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Assert if seriesWithUpdate value is the same as return after update
        Assertions.assertEquals(databaseAddReturn.getName(), databaseUpdateReturn.getName());
    }

    //Test delete series. Assertion based on getting series with id after its being deleted -> not found? -> successful delete :)
    @Test
    void deleteSeriesSuccessful(){
        //Create series to add and delete
        Series seriesToAdd = new Series("60's drawings", false, creator);

        //Make new series instance to get db return
        Series databaseAddReturn = new Series();
        try {
            databaseAddReturn = seriesService.addSeries(seriesToAdd);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Delete the series
        try{
            seriesService.deleteSeries(databaseAddReturn.getId().toString());
        }
        catch (Exception ex){
            System.out.println(ex);
        }

        //Get series with id from db
        Series databaseGetReturn = null;
        try {
            databaseGetReturn = seriesService.getSeriesWithId(databaseAddReturn.getId().toString());
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + databaseAddReturn.getId(), ex.getMessage());
        }

        //Check if return of getting series after delete is null
        Assertions.assertNull(databaseGetReturn);

    }
}
