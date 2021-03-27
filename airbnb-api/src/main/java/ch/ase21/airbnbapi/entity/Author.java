package ch.ase21.airbnbapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Author {

  @Id
  private String id;
  private String firstName;
  private String lastName;

  public String getId(){
    return id;
  }

  public String getFirstName(){
    return firstName;
  }

  public String getLastName(){
    return lastName;
  }
}
