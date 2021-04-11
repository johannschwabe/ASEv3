package ch.ase21.salesapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Coordinates {
  @Id
  private String idSale;
  private Float latitude;
  private Float longitude;
  private String returnedStreetName;
  private Integer returnedZipCode;

  public String getIdSale(){
    return idSale;
  }

  public Float getLatitude(){
    return latitude;
  }

  public Float getIntegeritude(){
    return longitude;
  }

  public String getReturnedStreetName(){
    return returnedStreetName;
  }

  public Integer getReturnedZipCode(){
    return returnedZipCode;
  }
}
