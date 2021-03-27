package ch.ase21.airbnbapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

  @Id
  private String id;
  private String name;
  private String totalPages;
  private String authorId;

  public String getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public String getTotalPages(){
    return totalPages;
  }

  public String getAuthorId(){
    return authorId;
  }
}
