package ch.ase21.airbnbapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Property {
  @Id
  private String id;
  private String name;
  private Integer hostId;
  private String hostName;
  private String neighbourhoodGroup;
  private String neighbourhood;
  private Float latitude;
  private Float longitude;
  private String roomType;
  private Integer price;
  private Integer minimumNights;
  private Integer numberOfReviews;
  private Date lastReview;
  private Integer reviewsPerMonth;
  private Integer calculatedHostListingsCount;
  private Integer availability365;

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
