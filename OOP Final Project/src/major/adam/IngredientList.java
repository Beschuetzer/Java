package major.adam;

import java.util.HashMap;

public class IngredientList {
  public static HashMap<Ingredients, Double> ingredients = new HashMap<Ingredients, Double>();

  public IngredientList() {
    ingredients.put(Ingredients.tomato, 1d);
    ingredients.put(Ingredients.lettuce, .5);
    ingredients.put(Ingredients.pickle, .75);
    ingredients.put(Ingredients.olive, 1.25);
    ingredients.put(Ingredients.carrot, .33);
    ingredients.put(Ingredients.dressing, .66);
  }

  public static double getPrice(Ingredients ingredient) {
    try {
      return IngredientList.ingredients.get(ingredient);
    } catch (Exception e) {
      System.out.println(e);
      return -1;
    }
  }
}
