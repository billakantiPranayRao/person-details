package embl.ebi.persondetails.repository;

import embl.ebi.persondetails.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {


}
