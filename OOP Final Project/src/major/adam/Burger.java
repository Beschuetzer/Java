package major.adam;


public class Burger {
  private final IngredientList INGREDIENT_HASHMAP = new IngredientList();


  public Burger() {

  }

  public double getPrice(Ingredients ingredient) {
    try {
      return INGREDIENT_HASHMAP.getPrice(ingredient);
    } catch (Exception err) {
      System.out.println(err);
      return -1d;
    }
  }

}
