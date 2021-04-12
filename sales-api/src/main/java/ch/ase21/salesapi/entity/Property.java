package ch.ase21.salesapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Timestamp;

@Entity
public class Property {
  @Id
  private String id;
  private Integer number;
  private Integer borough;
  private String neighborhood;
  private String buildingClassCategory;
  private String taxClassAtPresent;
  private Integer block;
  private Integer lot;
  private String easement;
  private String buildingClassAtPresent;
  private String address;
  private String apartmentNumber;
  private Integer zipCode;
  private Integer residentialUnits;
  private Integer commercialUnits;
  private Integer totalUnits;
  private String landSquareFeet;
  private String grossSquareFeet;
  private Integer yearBuilt;
  private Integer taxClassAtTimeOfSale;
  private String buildingClassAtTimeOfSale;
  private String salePrice;
  private Timestamp saleDate;
  @Transient
  private Coordinates coordinates;
  
  public String getId(){
    return id;
  }

  public Integer getNumber(){
    return number;
  }

  public Integer getBorough(){
    return borough;
  }

  public String getNeighborhood(){
    return neighborhood;
  }

  public String getBuildingClassCategory(){
    return buildingClassCategory;
  }

  public String getTaxClassAtPresent(){
    return taxClassAtPresent;
  }

  public Integer getBlock(){
    return block;
  }

  public Integer getLot(){
    return lot;
  }

  public String getEasement(){
    return easement;
  }

  public String getBuildingClassAtPresent(){
    return buildingClassAtPresent;
  }

  public String getAddress(){
    return address;
  }

  public String getApartmentNumber(){
    return apartmentNumber;
  }

  public Integer getZipCode(){
    return zipCode;
  }

  public Integer getResidentialUnits(){
    return residentialUnits;
  }

  public Integer getCommercialUnits(){
    return commercialUnits;
  }

  public Integer getTotalUnits(){
    return totalUnits;
  }

  public String getLandSquareFeet(){
    return landSquareFeet;
  }

  public String getGrossSquareFeet(){
    return grossSquareFeet;
  }

  public Integer getYearBuilt(){
    return yearBuilt;
  }

  public Integer getTaxClassAtTimeOfSale(){
    return taxClassAtTimeOfSale;
  }

  public String getBuildingClassAtTimeOfSale(){
    return buildingClassAtTimeOfSale;
  }

  public String getSalePrice(){
    return salePrice;
  }

  public Timestamp getSaleDate(){
    return saleDate;
  }

  public Coordinates getCoordinates(){
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates){
    this.coordinates = coordinates;
  }
}
