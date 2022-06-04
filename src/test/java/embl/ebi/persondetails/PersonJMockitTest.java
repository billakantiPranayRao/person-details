package embl.ebi.persondetails;

import embl.ebi.persondetails.controller.PersonController;
import embl.ebi.persondetails.model.Person;
import embl.ebi.persondetails.service.PersonService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonJMockitTest {

    @Tested(fullyInitialized = true)
    private PersonController personController;

    @Injectable
    private PersonService personService;


    @Test
    public void getPersonsTest() {
        new Expectations()
        {
            {
                personService.getAllPersons();
                result = new Exception();
            }
        };

        ResponseEntity<List<Person>> response = personController.getAllPersons();
        assertEquals(404, response.getStatusCodeValue());


    }


    /*@Test
    public void getContentCardModalTestException() throws Exception
    {
        new Expectations()
        {
            {
                contentCardsModalWidgetService.getContentCardModal(anyString);
                result = new Exception();
            }
        };
        ResponseEntity<ContentCardModalInfo> response = contentCardsWidgetController.getContentCardModal("TEST");
        assertEquals(500, response.getStatusCodeValue());
    }


    @Test
    public void getCurrentCoverageTestException() throws Exception
    {
        new Expectations()
        {
            {
                currentCoverageContentCardModalService.getCurrentCoverageContentCardModalDetails();
                result = new Exception();
            }
        };
        ResponseEntity<CurrentYearCoverage> response = contentCardsWidgetController.getCurrentCoverage();
        assertEquals(500, response.getStatusCodeValue());
    }*/


}











