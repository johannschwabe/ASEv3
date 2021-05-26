package ch.ase21.backend.entity;

public class SaleScore {
  private final String id;
  private final Double score;
  private final String address;
  private final Integer price;
  private final String neighbourhood;

  public SaleScore(String id, Double score, String address, Integer price, String neighbourhood) {
    this.id = id;
    this.score = score;
    this.address = address;
    this.price = price;
    this.neighbourhood = neighbourhood;
  }

  public String getId() {
    return id;
  }

  public Double getScore() {
    return score;
  }

  public String getAddress(){
    return address;
  }

  public Integer getPrice(){
    return price;
  }

  public String getNeighbourhood(){
    return neighbourhood;
  }
}
