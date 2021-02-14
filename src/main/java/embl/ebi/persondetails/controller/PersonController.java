package embl.ebi.persondetails.controller;

import embl.ebi.persondetails.model.Person;
import embl.ebi.persondetails.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PersonController {

    @Autowired
   private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/")
    public String home(){

        return ("<h1>Welcome To EMBL EBI</h1>" +
                "<p>Please use below swagger link to test CRUD operations</p>" +
                "<p>if it fails,please give right IP address and PORT</p>"+
                "<a href=\"http://localhost:8080/swagger-ui.html#/\">http://localhost:8080/swagger-ui.html#/</a>");
    }

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons(){


        List<Person> list = personService.getAllPersons();

        if(list.size()==0){
            return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.ok().body(personService.getAllPersons());
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getAllPersonsById(@PathVariable long id, Person person){

            Person person1 = personService.getPersonById(id);

            if ((person1 == null)){

              return   ResponseEntity.notFound().build();
            }else


        return ResponseEntity.ok().body(personService.getPersonById(id));
    }

    //@PreAuthorize("permitAll()")
    @PostMapping("/createPersons")
    public ResponseEntity<Person> createPerson(@RequestBody Person createPersons){

        if (createPersons==null){

            return ResponseEntity.badRequest().build();
        }

                return  ResponseEntity.ok().body(personService.createPerson(createPersons));


    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable  long id, @RequestBody  Person person){

        if (person.getId()!=id){

          return ResponseEntity.notFound().build();
        }else
       return ResponseEntity.ok().body(this.personService.updatePerson(person));

    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable long id){

        try{
            this.personService.deletePerson(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/persons")
    public ResponseEntity<Person> deleteAllPerson(){

        try{
            this.personService.deleteAllPerson();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
