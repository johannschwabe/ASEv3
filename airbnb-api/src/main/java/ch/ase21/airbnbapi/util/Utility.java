package ch.ase21.airbnbapi.util;

public class Utility {

  private Utility() {/* void */}

  /**
   * Capitalize the given string and each word after the given separator in the given string.
   * @param string String to capitalize.
   * @param separator Separator after which to capitalize.
   * @return Capitalized string.
   */
  public static String capitalizeSplit(String string, String separator) {
    String[] spaceParts = string.split(separator);
    StringBuilder answer = new StringBuilder();
    for(int i = 0; i < spaceParts.length; i++){
      if(i > 0){
        answer.append(separator);
      }
      answer.append(spaceParts[i].substring(0, 1).toUpperCase()).append(spaceParts[i].substring(1));
    }
    return answer.toString();
  }
}
