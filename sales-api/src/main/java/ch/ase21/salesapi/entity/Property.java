package ch.ase21.salesapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Property {
  @Id
  private Long id;
  private Long number;
  private Long borough;
  private String neighborhood;
  private String buildingClassCategory;
  private String taxClassAtPresent;
  private Long block;
  private Long lot;
  private String easement;
  private String buildingClassAtPresent;
  private String address;
  private String apartmentNumber;
  private Long zipCode;
  private Long residentialUnits;
  private Long commercialUnits;
  private Long totalUnits;
  private String landSquareFeet;
  private String grossSquareFeet;
  private Long yearBuilt;
  private Long taxClassAtTimeOfSale;
  private String buildingClassAtTimeOfSale;
  private String salePrice;
  private Timestamp saleDate;
  
  public Long getId(){
    return id;
  }

  public Long getNumber(){
    return number;
  }

  public Long getBorough(){
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

  public Long getBlock(){
    return block;
  }

  public Long getLot(){
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

  public Long getZipCode(){
    return zipCode;
  }

  public Long getResidentialUnits(){
    return residentialUnits;
  }

  public Long getCommercialUnits(){
    return commercialUnits;
  }

  public Long getTotalUnits(){
    return totalUnits;
  }

  public String getLandSquareFeet(){
    return landSquareFeet;
  }

  public String getGrossSquareFeet(){
    return grossSquareFeet;
  }

  public Long getYearBuilt(){
    return yearBuilt;
  }

  public Long getTaxClassAtTimeOfSale(){
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
}
