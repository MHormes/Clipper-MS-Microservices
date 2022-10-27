package noccures.clipperms.SeriesTest;

import noccures.clipperms.data.interfaces.ISeriesDataSource;
import noccures.clipperms.exceptions.ExceptionMessages;
import noccures.clipperms.model.Clipper;
import noccures.clipperms.model.Series;
import noccures.clipperms.service.SeriesService;
import noccures.clipperms.service.interfaces.ISeriesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
class SeriesServiceUnitTest {

    @Mock
    ISeriesDataSource seriesDataSource;

    ISeriesService seriesService;

    private final String clipperId1 = "9f612c11-192e-450b-903d-f4c2608d14ad";

    private final String clipperId2 = "0367e7c2-8772-4fda-b603-9d5c68e0357c";

    private final String clipperId3 = "c9f3ac2f-ce33-42ec-bbb2-4b6945051406";

    @BeforeEach
    void setUp() {
        seriesService = new SeriesService(seriesDataSource);
        //simulate expected database response when id does not exist -> return null
        when(seriesDataSource.getSeriesWithId(clipperId1)).thenReturn(null);
        //simulate series return with random amount of clippers inside -> amount of clippers can be exchanged,
        //as long as they get changed in the test methods 2.
        when(seriesDataSource.getSeriesWithId(clipperId2)).thenReturn(new Series(UUID.fromString(clipperId2), "custom series", new ArrayList<>(List.of(new Clipper(), new Clipper())),true));
        //simulate random number return on get taken series numbers -> numbers can be exchanged,
        // as long as they get changed in the test method 2.
        when(seriesDataSource.getTakenSeriesNumber(clipperId3)).thenReturn(new int[]{1, 3});
        //simulate expected database response when id should exist -> return series
        when(seriesDataSource.getSeriesWithId(clipperId3)).thenReturn(new Series("actual series", false));
    }


    //Test if add method throws exception when series has empty name.
    @Test()
    void addNewSeriesEmptyNameTest() {
        Series seriesToAdd = new Series("", false);

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
    @Test
    void getAvailableNumbersCustomSeries() {
        //Can use a fake series id since data source is mocked.
        int expectedNumber = 0;
        try{
            expectedNumber = seriesService.getAvailableSeriesNumber(clipperId2);
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
            expectedNumber = seriesService.getAvailableSeriesNumber(clipperId3);
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
            expectedResult = seriesService.getSeriesWithId(clipperId1);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + clipperId1, ex.getMessage());
        }

        //Check that no series value gets returned when exception is thrown.
        Assertions.assertNull(expectedResult);
    }

    //Test if exception is thrown when update series with non-existing id
    @Test
    void updateSeriesNonExisting() {
        //Create series as if it comes from FE. Assign non-existing id for test -> will be assigned in FE normally
        Series seriesWithUpdate = new Series("updated series", false);
        seriesWithUpdate.setId(UUID.fromString(clipperId1));

        Series expectedResult = null;
        try {
            expectedResult = seriesService.updateSeries(seriesWithUpdate);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + clipperId1, ex.getMessage());
        }

        //Check that no series value gets returned when exception is thrown.
        Assertions.assertNull(expectedResult);
    }

    //Test if exception is thrown when remove series with non-existing id
    @Test
    void deleteSeriesNonExisting() {
        try {
            seriesService.deleteSeries(clipperId1);
        } catch (Exception ex) {
            System.out.println(ex);
            Assertions.assertEquals(ExceptionMessages.SERIES_WITH_ID_NOT_FOUND + clipperId1, ex.getMessage());
        }
    }
}
