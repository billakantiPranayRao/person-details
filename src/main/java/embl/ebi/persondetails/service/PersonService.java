package embl.ebi.persondetails.service;

import embl.ebi.persondetails.model.Person;

import java.util.List;

public interface PersonService {

 Person createPerson(Person createPersons);

 Person updatePerson(Person person);

 List<Person> getAllPersons();

 Person getPersonById(long id);
 
 void  deletePerson(long id);

 void deleteAllPerson();
}
