package embl.ebi.persondetails;

import embl.ebi.persondetails.exception.PersonNotFoundException;
import embl.ebi.persondetails.model.Person;
import embl.ebi.persondetails.repository.PersonRepository;
import embl.ebi.persondetails.service.PersonServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonMokitoTest {

  @Autowired
  private PersonServiceImpl personService;

  @MockBean
  private PersonRepository personRepository;

  @Test
  public void getPersonsTest() {

    Person person = new Person();
    person.setFirst_name("Hello");
    person.setLast_name("EMBL");

    when(personRepository.findAll()).thenReturn(Stream.of(person).collect(Collectors.toList()));

    assertEquals(1, personService.getAllPersons().size());

  }

  @Test
  public void getPersonsDataById() {

    long id = 1;
    personData();
    when(personRepository.findById(id)).thenReturn(Optional.of(personData()));

    Person person1 = personService.getPersonById(id);

    assertEquals(personData(), person1);
    assertThat(person1.getFirst_name()).isEqualTo("EMBL");


  }

  @Test
  public void createPerson() {

    personData();

    when(personRepository.save(personData())).thenReturn(personData());

    assertEquals(personData(), personService.createPerson(personData()));

  }

  @Test
  public void deletePerson() {
    personData();

    when(personRepository.save(personData())).thenReturn(personData());
    personService.deleteAllPerson();

    assertThat(personService.getAllPersons().isEmpty());

  }

  @Test
  public void update() throws PersonNotFoundException {

    long id = 1;
    personData();

    Person person1 = new Person();
    person1.setId(1);
    person1.setFirst_name("Sarha");
    person1.setLast_name("Robinson");
    person1.setAge(20);
    person1.setFavourite_colour("RED");

    when(personRepository.findById(id)).thenReturn(Optional.of(personData()));
    Person updated = personService.updatePerson(person1);
    assertEquals(personData().getAge(), updated.getAge());
  }

  //Negative Nest Cases


  @Test
  public void getPersonsNotExistTest() {

    Person person = new Person();
    person.setFirst_name("Hello");
    person.setLast_name("EMBL");

    when(personRepository.findAll()).thenReturn(Stream.of(person).collect(Collectors.toList()));

    assertNotEquals(2, personService.getAllPersons().size());

  }


  @Test
  public void updatePersonNotExistTest() throws PersonNotFoundException {

    long id = 1;
    personData();

    Person person1 = new Person();
    person1.setId(1);
    person1.setFirst_name("Sarha");
    person1.setLast_name("Robinson");
    person1.setAge(16);
    person1.setFavourite_colour("RED");

    when(personRepository.findById(id)).thenReturn(Optional.of(personData()));
    Person updated = personService.updatePerson(person1);
    assertNotEquals(personData().getAge(), updated.getAge());
  }


  public Person personData() {
    Person person = new Person();

    person.setId(1);
    person.setFirst_name("EMBL");
    person.setLast_name("EBI");
    person.setAge(20);
    person.setFavourite_colour("Blue");

    return person;
  }
}
