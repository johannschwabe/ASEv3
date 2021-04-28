package ch.ase21.backend.entity;

import java.sql.Timestamp;

public class Sale {
  private final String id;
  private final Integer number;
  private final Integer borough;
  private final String neighborhood;
  private final String buildingClassCategory;
  private final String taxClassAtPresent;
  private final Integer block;
  private final Integer lot;
  private final String easement;
  private final String buildingClassAtPresent;
  private final String address;
  private final String apartmentNumber;
  private final Integer zipCode;
  private final Integer residentialUnits;
  private final Integer commercialUnits;
  private final Integer totalUnits;
  private final String landSquareFeet;
  private final String grossSquareFeet;
  private final Integer yearBuilt;
  private final Integer taxClassAtTimeOfSale;
  private final String buildingClassAtTimeOfSale;
  private final String salePrice;
  private final Timestamp saleDate;
  // Coordinates
  private final String idSale;
  private final Float latitude;
  private final Float longitude;
  private final String returnedStreetName;
  private final Integer returnedZipCode;

  public Sale(String id,
              Integer number,
              Integer borough,
              String neighborhood,
              String buildingClassCategory,
              String taxClassAtPresent,
              Integer block,
              Integer lot,
              String easement,
              String buildingClassAtPresent,
              String address,
              String apartmentNumber,
              Integer zipCode,
              Integer residentialUnits,
              Integer commercialUnits,
              Integer totalUnits,
              String landSquareFeet,
              String grossSquareFeet,
              Integer yearBuilt,
              Integer taxClassAtTimeOfSale,
              String buildingClassAtTimeOfSale,
              String salePrice,
              Timestamp saleDate,
              String idSale,
              Float latitude,
              Float longitude,
              String returnedStreetName,
              Integer returnedZipCode)
  {
    this.id = id;
    this.number = number;
    this.borough = borough;
    this.neighborhood = neighborhood;
    this.buildingClassCategory = buildingClassCategory;
    this.taxClassAtPresent = taxClassAtPresent;
    this.block = block;
    this.lot = lot;
    this.easement = easement;
    this.buildingClassAtPresent = buildingClassAtPresent;
    this.address = address;
    this.apartmentNumber = apartmentNumber;
    this.zipCode = zipCode;
    this.residentialUnits = residentialUnits;
    this.commercialUnits = commercialUnits;
    this.totalUnits = totalUnits;
    this.landSquareFeet = landSquareFeet;
    this.grossSquareFeet = grossSquareFeet;
    this.yearBuilt = yearBuilt;
    this.taxClassAtTimeOfSale = taxClassAtTimeOfSale;
    this.buildingClassAtTimeOfSale = buildingClassAtTimeOfSale;
    this.salePrice = salePrice;
    this.saleDate = saleDate;
    // Coordinates
    this.idSale = idSale;
    this.latitude = latitude;
    this.longitude = longitude;
    this.returnedStreetName = returnedStreetName;
    this.returnedZipCode = returnedZipCode;
  }

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

  // Coordinates

  public String getIdSale(){
    return idSale;
  }

  public Float getLatitude(){
    return latitude;
  }

  public Float getLongitude(){
    return longitude;
  }

  public String getReturnedStreetName(){
    return returnedStreetName;
  }

  public Integer getReturnedZipCode(){
    return returnedZipCode;
  }
}
