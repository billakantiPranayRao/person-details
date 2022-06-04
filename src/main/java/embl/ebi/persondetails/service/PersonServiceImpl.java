package embl.ebi.persondetails.service;

import embl.ebi.persondetails.exception.PersonNotFoundException;
import embl.ebi.persondetails.model.Person;
import embl.ebi.persondetails.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public Person createPerson(Person createPersons) {
        return personRepository.save(createPersons);
    }

    @Override
    public Person updatePerson(Person person) {
        Optional<Person> personData = this.personRepository.findById(person.getId());
        if (personData.isPresent()) {

            Person personUpdate = personData.get();
            personUpdate.setFirst_name(person.getFirst_name());
            personUpdate.setLast_name(person.getLast_name());
            personUpdate.setAge(person.getAge());
            personUpdate.setFavourite_colour(person.getFavourite_colour());
            personRepository.save(personUpdate);
            return personUpdate;
        } else
            throw new PersonNotFoundException("Requested person details are not found in the records with this id " + person.getId());

    }

    @Override
    public List<Person> getAllPersons() {
        return this.personRepository.findAll();
    }

    @Override
    public Person getPersonById(long id) {
        Optional<Person> personData = this.personRepository.findById(id);
        if (personData.isPresent()) {

            return personData.get();
        } else {
            throw new PersonNotFoundException("Record not found with this id: " + id);
        }
    }

    @Override
    public void deletePerson(long id) {
        Optional<Person> personData = this.personRepository.findById(id);
        if (personData.isPresent()) {

            this.personRepository.delete(personData.get());
        } else {
            throw new PersonNotFoundException("Record not found with this id: " + id);
        }

    }

    @Override
    public void deleteAllPerson() {
        //List<Person> personData = this.personRepository.findAll();


           this.personRepository.deleteAll();


    }
}
