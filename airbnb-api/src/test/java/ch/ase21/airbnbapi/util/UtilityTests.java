package ch.ase21.airbnbapi.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilityTests {

  @Test
  void capitalizeSplit() {
    String input = "space and-dash";
    String expected1 = "Space And-dash";
    String expected2 = "Space and-Dash";

    String output1 = Utility.capitalizeSplit(input, " ");
    String output2 = Utility.capitalizeSplit(input, "-");

    Assertions.assertEquals(expected1, output1);
    Assertions.assertEquals(expected2, output2);
  }

}
