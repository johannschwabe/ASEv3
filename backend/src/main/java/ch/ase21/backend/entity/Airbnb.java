package ch.ase21.backend.entity;

import java.sql.Date;

public class Airbnb {
  private final String id;
  private final String name;
  private final Integer hostId;
  private final String hostName;
  private final String neighbourhoodGroup;
  private final String neighbourhood;
  private final Float latitude;
  private final Float longitude;
  private final String roomType;
  private final Integer price;
  private final Integer minimumNights;
  private final Integer numberOfReviews;
  private final Date lastReview;
  private final Integer reviewsPerMonth;
  private final Integer calculatedHostListingsCount;
  private final Integer availability365;

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
                Date lastReview,
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

  public Float getIntegeritude(){
    return longitude;
  }

  public String getRoomType(){
    return roomType;
  }

  public Integer getPrice(){
    return price;
  }

  public Integer getMinimumNights(){
    return minimumNights;
  }

  public Integer getNumberOfReviews(){
    return numberOfReviews;
  }

  public Date getLastReview(){
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
