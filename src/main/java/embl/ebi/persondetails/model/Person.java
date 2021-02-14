package embl.ebi.persondetails.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "age")
    private int age;
    @Column(name = "favourite_colour")
    private String favourite_colour;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFavourite_colour() {
        return favourite_colour;
    }

    public void setFavourite_colour(String favourite_colour) {
        this.favourite_colour = favourite_colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                age == person.age &&
                Objects.equals(first_name, person.first_name) &&
                Objects.equals(last_name, person.last_name) &&
                Objects.equals(favourite_colour, person.favourite_colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, age, favourite_colour);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", age=" + age +
                ", favourite_colour='" + favourite_colour + '\'' +
                '}';
    }
}
