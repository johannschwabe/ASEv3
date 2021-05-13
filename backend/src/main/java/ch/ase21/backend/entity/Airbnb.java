package ch.ase21.backend.entity;

public class Airbnb {
  private final String id;
  private String name = null;
  private Integer hostId = null;
  private String hostName = null;
  private String neighbourhoodGroup = null;
  private String neighbourhood = null;
  private Float latitude = null;
  private Float longitude = null;
  private String roomType = null;
  private Integer price = null;
  private Integer minimumNights = null;
  private Integer numberOfReviews = null;
  private String lastReview = null;
  private Integer reviewsPerMonth = null;
  private Integer calculatedHostListingsCount = null;
  private Integer availability365 = null;

  public static final String APARTMENT_ROOM_TYPE = "Entire home/apt";
  public static final String PRIVATE_ROOM_TYPE = "Private room";

  public Airbnb(String id){ this.id = id; }

  public Airbnb(String id,
                String name,
                Integer hostId,
                String hostName,
                String neighbourhoodGroup,
                String neighbourhood,
                Float latitude,
                Float longitude,
                String roomType,
                Integer price,
                Integer minimumNights,
                Integer numberOfReviews,
                String lastReview,
                Integer reviewsPerMonth,
                Integer calculatedHostListingsCount,
                Integer availability365)
  {
    this.id = id;
    this.name = name;
    this.hostId = hostId;
    this.hostName = hostName;
    this.neighbourhoodGroup = neighbourhoodGroup;
    this.neighbourhood = neighbourhood;
    this.latitude = latitude;
    this.longitude = longitude;
    this.roomType = roomType;
    this.price = price;
    this.minimumNights = minimumNights;
    this.numberOfReviews = numberOfReviews;
    this.lastReview = lastReview;
    this.reviewsPerMonth = reviewsPerMonth;
    this.calculatedHostListingsCount = calculatedHostListingsCount;
    this.availability365 = availability365;
  }

  public String getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public Integer getHostId(){
    return hostId;
  }

  public String getHostName(){
    return hostName;
  }

  public String getNeighbourhoodGroup(){
    return neighbourhoodGroup;
  }

  public String getNeighbourhood(){
    return neighbourhood;
  }

  public Float getLatitude(){
    return latitude;
  }

  public Float getLongitude(){
    return longitude;
  }

  public String getRoomType(){
    return roomType;
  }

  public void setRoomType(String roomType){
    this.roomType = roomType;
  }

  public Integer getPrice(){
    return price;
  }

  public void setPrice(Integer price){
    this.price = price;
  }

  public Integer getMinimumNights(){
    return minimumNights;
  }

  public Integer getNumberOfReviews(){
    return numberOfReviews;
  }

  public String getLastReview(){
    return lastReview;
  }

  public Integer getReviewsPerMonth(){
    return reviewsPerMonth;
  }

  public Integer getCalculatedHostListingsCount(){
    return calculatedHostListingsCount;
  }

  public Integer getAvailability365(){
    return availability365;
  }
}
