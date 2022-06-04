package embl.ebi.persondetails;

import embl.ebi.persondetails.model.Person;
import embl.ebi.persondetails.repository.PersonRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonTests {

    @Autowired
   private PersonRepository personRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    @Order(1)
    public  void PersonDataIsEmpty(){

       List<Person> person = personRepository.findAll();

       assert(person).isEmpty();
    }

    @Test
    @Order(2)
    @Rollback()
    public  void PersonDataCreate(){

        Person person = new Person();
        person.setFirst_name("EMBL");
        person.setLast_name("EBI");
        person.setAge(20);
        person.setFavourite_colour("White");
       //Person savePerson = personRepository.save(person);
       Person savePerson = testEntityManager.persist(person);

        assertThat(savePerson.getId()).isGreaterThan(0);
        assertThat(person).hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(4)
    public void PersonDataGet(){


        Person person = new Person();
        person.setFirst_name("EMBL");
        person.setLast_name("EBI");
        person.setAge(22);
        person.setFavourite_colour("Yellow");
        testEntityManager.persist(person);

        Person person1 = new Person();
        person1.setFirst_name("EMBL");
        person1.setLast_name("EBI");
        person1.setAge(24);
        person1.setFavourite_colour("Blue");
        testEntityManager.persist(person1);


        List<Person> persondata = personRepository.findAll();

        assert(persondata).contains(person);
        assert(persondata).contains(person1);
       // assert(true).size();

    }

    @Test
    @Order(3)
    public void personUpdate(){

        Person person = new Person();
        person.setFirst_name("EMBL");
        person.setLast_name("EBI");
        person.setAge(20);
        person.setFavourite_colour("White");
        testEntityManager.persist(person);

        Optional<Person> person1 = personRepository.findById(person.getId());

        Person person2 = person1.get();
        person2.setFirst_name("EMBL_EBI");
        personRepository.save(person2);

        Optional<Person> upDatedPerson = personRepository.findById(person.getId());
        assert(upDatedPerson.get().getFirst_name().equals(person2.getFirst_name()));

    }

    @Test
    @Order(5)
    public void deletePerson(){

        Person person = new Person();
        person.setFirst_name("EMBL");
        person.setLast_name("EBI");
        person.setAge(20);
        person.setFavourite_colour("White");
        testEntityManager.persist(person);

            this.personRepository.deleteAll();
        assertThat(personRepository.findAll()).hasNoNullFieldsOrProperties();


    }
}
