package clipperms.collection.SeriesTest;

import clipperms.collection.data.interfaces.ISeriesDataSource;
import clipperms.collection.exceptions.ExceptionMessages;
import clipperms.collection.model.Clipper;
import clipperms.collection.model.Series;
import clipperms.collection.service.SeriesService;
import clipperms.collection.service.interfaces.ISeriesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
class SeriesServiceUnitTest {

    @Mock
    ISeriesDataSource seriesDataSource;

    @Autowired
    ISeriesService seriesService;

    private final String seriesId1 = "9f612c11-192e-450b-903d-f4c2608d14ad";

    private final String seriesId2 = "0367e7c2-8772-4fda-b603-9d5c68e0357c";

    private final String seriesId3 = "c9f3ac2f-ce33-42ec-bbb2-4b6945051406";

    @BeforeEach
    void setUp() {
        seriesService = new SeriesService(seriesDataSource);
        //simulate expected database response when id does not exist -> return null
        when(seriesDataSource.getSeriesWithId(UUID.fromString(seriesId1))).thenReturn(null);
        //simulate series return with random amount of clippers inside -> amount of clippers can be exchanged,
        //as long as they get changed in the test methods 2.
        when(seriesDataSource.getSeriesWithId(UUID.fromString(seriesId2))).thenReturn(new Series(UUID.fromString(seriesId2), "custom series", new ArrayList<>(List.of(new Clipper(), new Clipper())),true));
        //simulate random number return on get taken series numbers -> numbers can be exchanged,
        // as long as they get changed in the test method 2.
        when(seriesDataSource.getTakenSeriesNumber(UUID.fromString(seriesId3))).thenReturn(new int[]{1, 3});
        //simulate expected database response when id should exist -> return series
        when(seriesDataSource.getSeriesWithId(UUID.fromString(seriesId3))).thenReturn(new Series("actual series", false, null));
    }


    //Test if add method throws exception when series has empty name.
    @Test()
    void addNewSeriesEmptyNameTest() {
        Series seriesToAdd = new Series("", false, null);

        Series expectedResult = null;
        try {
            expectedResult = seriesService.addSeries(seriesToAdd);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.SERIES_NO_NAME, ex.getMessage());
        }

        //Check that no series value gets returned when exception is thrown.
        Assertions.assertNull(expectedResult);
    }

    //Test if correct available numbers get returned for a custom series.

    //todo update tests to consume list of integer instead of single number
    @Test
    void getAvailableNumbersCustomSeries() {
        //Can use a fake series id since data source is mocked.
        int expectedNumber = 0;
        try{
            var intList = seriesService.getAvailableSeriesNumber(seriesId2);
            expectedNumber = intList.get(0);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals(3, expectedNumber);
    }

    //Test if correct available numbers get returned for an actual clipper series.
    @Test
    void getAvailableNumbersActualSeries() {
        //Can use a fake series id since data source is mocked.
        int expectedNumber = 0;
        try{
            var intList = seriesService.getAvailableSeriesNumber(seriesId3);
            expectedNumber = intList.get(0);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        Assertions.assertEquals(2, expectedNumber);
    }

    //Test if Get by id throws exception when series does not exist.
    @Test
    void getSeriesWithIdNonExisting() {
        Series expectedResult = null;
        try {
            expectedResult = seriesService.getSeriesWithId(seriesId1);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + seriesId1, ex.getMessage());
        }

        //Check that no series value gets returned when exception is thrown.
        Assertions.assertNull(expectedResult);
    }

    //Test if exception is thrown when update series with non-existing id
    @Test
    void updateSeriesNonExisting() {
        //Create series as if it comes from FE. Assign non-existing id for test -> will be assigned in FE normally
        Series seriesWithUpdate = new Series("updated series", false, null);
        seriesWithUpdate.setId(UUID.fromString(seriesId1));

        Series expectedResult = null;
        try {
            expectedResult = seriesService.updateSeries(seriesWithUpdate);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + seriesId1, ex.getMessage());
        }

        //Check that no series value gets returned when exception is thrown.
        Assertions.assertNull(expectedResult);
    }

    //Test if exception is thrown when remove series with non-existing id
    @Test
    void deleteSeriesNonExisting() {
        try {
            seriesService.deleteSeries(seriesId1);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + seriesId1, ex.getMessage());
        }
    }
}
