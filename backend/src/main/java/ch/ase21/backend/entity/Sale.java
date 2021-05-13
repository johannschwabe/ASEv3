package ch.ase21.backend.entity;

public class Sale {
  private final String id;
  private Integer number = null;
  private Integer borough = null;
  private String neighbourhood = null;
  private String buildingClassCategory = null;
  private String taxClassAtPresent = null;
  private Integer block = null;
  private Integer lot = null;
  private String easement = null;
  private String buildingClassAtPresent = null;
  private String address = null;
  private String apartmentNumber = null;
  private Integer zipCode = null;
  private Integer residentialUnits = null;
  private Integer commercialUnits = null;
  private Integer totalUnits = null;
  private Integer landSquareFeet = null;
  private Integer grossSquareFeet = null;
  private Integer yearBuilt = null;
  private Integer taxClassAtTimeOfSale = null;
  private String buildingClassAtTimeOfSale = null;
  private Integer salePrice = null;
  private String saleDate = null;
  // Coordinates
  private String idSale = null;
  private Float latitude = null;
  private Float longitude = null;
  private String returnedStreetName = null;
  private Integer returnedZipCode = null;
  
  public Sale(String id){ this.id = id; }

  public Sale(String id,
              Integer number,
              Integer borough,
              String neighbourhood,
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
              Integer landSquareFeet,
              Integer grossSquareFeet,
              Integer yearBuilt,
              Integer taxClassAtTimeOfSale,
              String buildingClassAtTimeOfSale,
              Integer salePrice,
              String saleDate,
              String idSale,
              Float latitude,
              Float longitude,
              String returnedStreetName,
              Integer returnedZipCode)
  {
    this.id = id;
    this.number = number;
    this.borough = borough;
    this.neighbourhood = neighbourhood;
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

  public String getNeighbourhood(){
    return neighbourhood;
  }

  public void setNeighbourhood(String neighbourhood){
    this.neighbourhood = neighbourhood;
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

  public void setResidentialUnits(Integer residentialUnits){
    this.residentialUnits = residentialUnits;
  }

  public Integer getCommercialUnits(){
    return commercialUnits;
  }

  public Integer getTotalUnits(){
    return totalUnits;
  }

  public void setTotalUnits(Integer totalUnits){
    this.totalUnits = totalUnits;
  }

  public Integer getLandSquareFeet(){
    return landSquareFeet;
  }

  public Integer getGrossSquareFeet(){
    return grossSquareFeet;
  }

  public void setGrossSquareFeet(Integer grossSquareFeet){
    this.grossSquareFeet = grossSquareFeet;
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

  public Integer getSalePrice(){
    return salePrice;
  }

  public void setSalePrice(Integer salePrice){
    this.salePrice = salePrice;
  }

  public String getSaleDate(){
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
