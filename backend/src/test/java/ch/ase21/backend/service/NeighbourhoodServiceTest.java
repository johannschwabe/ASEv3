package ch.ase21.backend.service;

import ch.ase21.backend.entity.Airbnb;
import ch.ase21.backend.entity.Sale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class NeighbourhoodServiceTest {

  /**
   * Calculate the score of a great neighbourhood
   */
  @Test void calculateGreatRating(){
    Sale sale = new Sale("1");
    sale.setSalePrice("100000");
    sale.setTotalUnits(1);
    sale.setResidentialUnits(1);
    List<Sale> sales = new ArrayList<>();
    sales.add(sale);

    Airbnb airbnb = new Airbnb("1");
    airbnb.setPrice(200);
    List<Airbnb> airbnbs = new ArrayList<>();
    airbnbs.add(airbnb);

    Float score = NeighbourhoodService.calculateScore(airbnbs, sales);

    Assertions.assertNotNull(score);
    Assertions.assertEquals(1, score);
  }

  /**
   * Calculate the score of a bad neighbourhood
   */
  @Test void calculateBadRating(){
    Sale sale = new Sale("1");
    sale.setSalePrice("10000000");
    sale.setTotalUnits(1);
    sale.setResidentialUnits(1);
    List<Sale> sales = new ArrayList<>();
    sales.add(sale);

    Airbnb airbnb = new Airbnb("1");
    airbnb.setPrice(200);
    List<Airbnb> airbnbs = new ArrayList<>();
    airbnbs.add(airbnb);

    Float score = NeighbourhoodService.calculateScore(airbnbs, sales);

    Assertions.assertNotNull(score);
    Assertions.assertTrue(score < 0.1);
  }

  /**
   * Calculate the score of an average neighbourhood
   */
  @Test void calculateAverageRating(){
    Sale sale = new Sale("1");
    sale.setSalePrice("1000000");
    sale.setTotalUnits(1);
    sale.setResidentialUnits(1);
    List<Sale> sales = new ArrayList<>();
    sales.add(sale);

    Airbnb airbnb = new Airbnb("1");
    airbnb.setPrice(200);
    List<Airbnb> airbnbs = new ArrayList<>();
    airbnbs.add(airbnb);

    Float score = NeighbourhoodService.calculateScore(airbnbs, sales);

    Assertions.assertNotNull(score);
    Assertions.assertTrue(score < 0.6 && score > 0.4);
  }

  /**
   * Calculate the score without any sale properties.
   * Should return null.
   */
  @Test void calculateScoreMissingSales(){
    List<Sale> sales = new ArrayList<>();

    Airbnb airbnb = new Airbnb("1");
    airbnb.setPrice(200);
    List<Airbnb> airbnbs = new ArrayList<>();
    airbnbs.add(airbnb);

    Float score = NeighbourhoodService.calculateScore(airbnbs, sales);

    Assertions.assertNull(score);
  }

  /**
   * Calculate the score without any airbnb properties.
   * Should return null.
   */
  @Test void calculateScoreMissingAirbnbs(){
    Sale sale = new Sale("1");
    sale.setSalePrice("1000000");
    sale.setTotalUnits(1);
    sale.setResidentialUnits(1);
    List<Sale> sales = new ArrayList<>();
    sales.add(sale);

    List<Airbnb> airbnbs = new ArrayList<>();

    Float score = NeighbourhoodService.calculateScore(airbnbs, sales);

    Assertions.assertNull(score);
  }
}
