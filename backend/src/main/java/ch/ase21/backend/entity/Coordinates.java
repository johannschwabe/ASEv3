package ch.ase21.backend.entity;

public class Coordinates {
  private final String id;
  private final Float longitude;
  private final Float latitude;

  public Coordinates(String id, Float latitude, Float longitude){
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getId(){
    return id;
  }

  public Float getLatitude(){
    return latitude;
  }

  public Float getLongitude(){
    return longitude;
  }
}
