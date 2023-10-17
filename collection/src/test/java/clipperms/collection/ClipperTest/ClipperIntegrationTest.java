package clipperms.collection.ClipperTest;

import clipperms.collection.data.interfaces.IClipperDataSource;
import clipperms.collection.model.AppUser;
import clipperms.collection.model.Clipper;
import clipperms.collection.service.interfaces.IAppUserService;
import clipperms.collection.service.interfaces.IClipperService;
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
class ClipperIntegrationTest {

    //Autowired db in test config to test logic and data layer integration
    @Autowired
    IClipperDataSource clipperDataSource;

    @Autowired
    IClipperService clipperService;

    @Autowired
    IAppUserService appUserService;

    AppUser creator;

    @BeforeEach
    void setUp() {
        this.creator = new AppUser(UUID.randomUUID() ,"Maarten", "MHormes", "Hormes123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        appUserService.saveUser(creator);
    }

    //Test adding new clipper. Asserts based on inputted name and name returned by db after add
    @Test
    void addNewClipperSuccessfulTest() {
        //Create clipper to add
        Clipper clipperToAdd = new Clipper("Berlin <3", null, 0, creator);

        //Make new clipper instance to get db return
        Clipper databaseReturn = new Clipper();
        try {
            databaseReturn = clipperService.addClipper(clipperToAdd, null);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Check if name of inserted clipper and db return are the same
        Assertions.assertEquals(clipperToAdd.getName(), databaseReturn.getName());
    }

    //Test if correct clipper is fetched from db. Asserts based on name of added clipper and name of clipper gotten by id.
    @Test
    void getClipperWithIdSuccessfulTest() {
        //Create clipper to add and get after adding (no clipper added means no clipper to get)
        Clipper clipperToAdd = new Clipper("Modena <3", null, 0, creator);
        //Make new clipper instance to get db return
        Clipper databaseAddReturn = new Clipper();
        try {
            databaseAddReturn = clipperService.addClipper(clipperToAdd, null);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Get clipper with id from db
        Clipper databaseGetReturn = new Clipper();
        try {
            databaseGetReturn = clipperService.getClipperWithId(databaseAddReturn.getId().toString());
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Check if   added clipper has same name as return of getting clipper with id
        Assertions.assertEquals(clipperToAdd.getName(), databaseGetReturn.getName());
    }

    //Test update clipper. Assert based on inputted data and db return value after update
    @Test
    void updateClipperSuccessful(){
        //Create clipper to add
        Clipper clipperToAdd = new Clipper("California <3", null, 0, creator);

        //Make new clipper instance to get db return
        Clipper databaseAddReturn = new Clipper();
        try {
            databaseAddReturn = clipperService.addClipper(clipperToAdd, null);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //create clipper with update values. Set id manually -> normally gets taken from FE
        Clipper clipperWithUpdate = new Clipper("new name", null, 0, creator);
        clipperWithUpdate.setId(databaseAddReturn.getId());

        //Make new clipper instance to get db return
        Clipper databaseUpdateReturn = new Clipper();
        try {

            databaseUpdateReturn = clipperService.updateClipper(clipperWithUpdate, null);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //Assert if clipperWithUpdate value is the same as return after update.
        Assertions.assertEquals(clipperWithUpdate.getName(), databaseUpdateReturn.getName());
    }

    //Test delete clipper. Assertion based on getting clipper with id after its being deleted -> not found? -> successful delete :)
    @Test
    void deleteClipperSuccessful(){
        //Create clipper to add and delete
        Clipper clipperToAdd = new Clipper("Valencia <3", null, 0, creator);

        //Make new clipper instance to get db return
        Clipper databaseAddReturn = new Clipper();
        try {
            databaseAddReturn = clipperService.addClipper(clipperToAdd, null);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try{
            clipperService.deleteClipper(databaseAddReturn.getId().toString());
        }
        catch (Exception ex){
            System.out.println("SECOND" + ex);
        }

        //Get clipper with id from db
        Clipper databaseGetReturn = null;
        try {
            databaseGetReturn = clipperService.getClipperWithId(databaseAddReturn.getId().toString());
        } catch (Exception ex) {
            System.out.println("THIRD" + ex);
            Assertions.assertEquals(ExceptionMessages.CLIPPER_WITH_ID_NOT_FOUND + databaseAddReturn.getId(), ex.getMessage());
        }

        //Check if return of getting clipper after delete is null
        Assertions.assertNull(databaseGetReturn);

    }
}
