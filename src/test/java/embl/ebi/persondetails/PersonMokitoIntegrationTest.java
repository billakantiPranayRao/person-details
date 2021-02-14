package embl.ebi.persondetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import embl.ebi.persondetails.controller.PersonController;
import embl.ebi.persondetails.exception.PersonNotFoundException;
import embl.ebi.persondetails.model.Person;
import embl.ebi.persondetails.service.PersonServiceImpl;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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


    @Test
    public void getPersonByIdTest() throws Exception{
        int id =1;

        personData();
        Mockito.when(personService.getPersonById(1)).thenReturn(personData());

        mockMvc.perform(get("/persons/ "+id+"")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString().isEmpty();

    }

    @Test
    public void createPersonDataTest() throws Exception {

        personData();

            mockMvc.perform(MockMvcRequestBuilders.post("/createPersons")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(asJsonString(personData()))).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();

    }


    @Test
    public void updatePersonDataTest() throws Exception {

        long id = 1;
        personData();
        String UpdateData =
                "{\n" +
                        "        \"id\": 1,\n" +
                        "        \"first_name\": \"Sarah\",\n" +
                        "        \"last_name\": \"Robinson\",\n" +
                        "        \"age\": 52,\n" +
                        "        \"favourite_colour\": \"white\"\n" +
                        "    }";

        mockMvc.perform(put("/persons/ "+id+"")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UpdateData)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().contains("Sarah");

    }

    @Test
    public void deletePersonsDataTest() throws Exception {

        createPersonDataTest();
        int id = 1;
        mockMvc.perform(delete("/persons/ "+id+"")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    public  String asJsonString(final Object obj) {
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
    public void getPersonByIdNotExistTest() throws Exception{
        int id =2;

        personData();
        Mockito.when(personService.getPersonById(1)).thenReturn(personData());

        mockMvc.perform(get("/persons/ "+id+"")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString().isEmpty();

    }

    @Test
    public void DeleteByNotExistId() throws Exception {

        int id =2;

        personData();
        Mockito.when(personService.getPersonById(1)).thenReturn(personData());

        mockMvc.perform(delete("/persons/ "+id+"")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void updatePersonDataNotExistTest() throws Exception {
        long id = 2;
        personData();
        String UpdateData =
                "{\n" +
                        "        \"id\": 1,\n" +
                        "        \"first_name\": \"Sarah\",\n" +
                        "        \"last_name\": \"Robinson\",\n" +
                        "        \"age\": 52,\n" +
                        "        \"favourite_colour\": \"white\"\n" +
                        "    }";

        mockMvc.perform(put("/persons/ "+id+"")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UpdateData)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    public Person personData(){
        Person  person = new Person();

        person.setId(1);
        person.setFirst_name("EMBL");
        person.setLast_name("EBI");
        person.setAge(20);
        person.setFavourite_colour("Blue");

        return person;

    }

}