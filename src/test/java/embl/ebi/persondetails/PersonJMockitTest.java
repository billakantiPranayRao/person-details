package embl.ebi.persondetails;

import embl.ebi.persondetails.controller.PersonController;
import embl.ebi.persondetails.model.Person;
import embl.ebi.persondetails.service.PersonService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
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
    public void getPersonsTest(@Mocked Person person) {
        new Expectations()
        {
            {
                personService.getAllPersons();
                result = new Exception();
            }
        };

        ResponseEntity<List<Person>> response = personController.getAllPersons();
        assertEquals(404, response.getStatusCodeValue());

        new Expectations()
        {
            {
                personService.getAllPersons();
                result = person;
            }
        };

        ResponseEntity<List<Person>> res = personController.getAllPersons();
        assertEquals(200, res.getStatusCodeValue());


    }

    @Test
    public  void GetPersonDetailsByIdTest(@Mocked Person person){

        int id =1;
        new Expectations()
        {
            {
                personService.getPersonById(1);
                result = person;
            }
        };

        ResponseEntity<Person> response = personController.getAllPersonsById(id);
        assertEquals(200, response.getStatusCodeValue());



    }

}











