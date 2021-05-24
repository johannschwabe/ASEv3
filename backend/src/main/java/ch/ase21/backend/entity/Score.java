package ch.ase21.backend.entity;

public class Score {
  private final String id;
  private final Double score;

  public Score(String id, Double score) {
    this.id = id;
    this.score = score;
  }

  public Double getScore() {
    return score;
  }

  public String getId() {
    return id;
  }
}
