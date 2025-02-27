/*
* Represents a student in the DBS class.
*/

package entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeSpent> timeSpentList;

    public Student() {
    }

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name; 
    }

    public String getEmail() {
        return email; 
    }

    public void setEmail(String email) {
        this.email = email; 
    }

    public List<TimeSpent> getTimeSpentList() {
        return timeSpentList; 
    }

    public void setTimeSpentList(List<TimeSpent> timeSpentList) {
        this.timeSpentList = timeSpentList; 
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }

}
