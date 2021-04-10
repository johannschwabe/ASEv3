package ch.ase21.airbnbapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Property {
  @Id
  private Long id;
  private String name;
  private Long hostId;
  private String hostName;
  private String neighbourhoodGroup;
  private String neighbourhood;
  private Float latitude;
  private Float longitude;
  private String roomType;
  private Long price;
  private Long minimumNights;
  private Long numberOfReviews;
  private Date lastReview;
  private Long reviewsPerMonth;
  private Long calculatedHostListingsCount;
  private Long availability365;

  public Long getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public Long getHostId(){
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

  public Long getPrice(){
    return price;
  }

  public Long getMinimumNights(){
    return minimumNights;
  }

  public Long getNumberOfReviews(){
    return numberOfReviews;
  }

  public Date getLastReview(){
    return lastReview;
  }

  public Long getReviewsPerMonth(){
    return reviewsPerMonth;
  }

  public Long getCalculatedHostListingsCount(){
    return calculatedHostListingsCount;
  }

  public Long getAvailability365(){
    return availability365;
  }
}
