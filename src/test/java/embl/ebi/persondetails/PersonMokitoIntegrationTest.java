package embl.ebi.persondetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import embl.ebi.persondetails.controller.PersonController;
import embl.ebi.persondetails.model.Person;
import embl.ebi.persondetails.service.PersonServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonController.class)
public class PersonMokitoIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @InjectMocks
    private PersonController personController;

    @MockBean
    private PersonServiceImpl personService;

    @LocalServerPort
    private String port;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private Person person;


    @Test
    public void getPersonByIdTest() throws Exception {
        int id = 2;


        Mockito.when(personService.getPersonById(2)).thenReturn(person);

        mockMvc.perform(get("/persons/ " + id + "")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString().contains(person.getFirst_name());

    }

    @Test
    public void createPersonDataTest() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/createPersons")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(asJsonString(person)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();

    }


    @Test
    public void updatePersonDataTest() throws Exception {

        long id = 2;


        Person updatePerson = new Person();

        updatePerson.setId(2);
        updatePerson.setFirst_name("India");
        updatePerson.setAge(10);

        mockMvc.perform(put("/persons/ " + id + "")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatePerson))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString().contains("India");

    }

    @Test
    public void deletePersonsDataTest() throws Exception {

        Mockito.when(personService.getPersonById(2)).thenReturn(person);
        mockMvc.perform(delete("/persons/{id} ", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Unit Test cases Negative Scenarios

    @Test
    public void getPersonNotExistTest() throws Exception {

        mockMvc.perform(get("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString().isEmpty();

    }

    @Test
    public void getPersonByIdNotExistTest() throws Exception {
        int id = 1;

        Mockito.when(personService.getPersonById(2)).thenReturn(person);

        mockMvc.perform(get("/persons/ " + id + "")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString().isEmpty();

    }


    @Test
    public void updatePersonDataNotExistTest() throws Exception {
        long id = 1;

        Person updatePerson = new Person();

        updatePerson.setId(2);
        updatePerson.setFirst_name("India");
        updatePerson.setAge(10);


        mockMvc.perform(put("/persons/ " + id + "")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatePerson))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    @Before
    public void personData() {
        person = new Person();

        person.setId(2);
        person.setFirst_name("EMBL");
        person.setLast_name("EBI");
        person.setAge(20);
        person.setFavourite_colour("Blue");


    }

}