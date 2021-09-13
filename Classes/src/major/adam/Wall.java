package major.adam;

public class Wall {
  private double width;
  private double height;

  public Wall() {

  }

  public Wall(double width, double height) {
    this.width = width < 0 ? 0 : width;
    this.height = height < 0 ? 0 : height;
  }

  public double getHeight() {
    return this.height;
  }

  public double getWidth() {
    return this.width;
  }

  public void setHeight(double height) {
    this.height = height < 0 ? 0 : height;
  }

  public void setWidth(double width) {
    this.width = width < 0 ? 0 : width;
  }

  public double getArea() {
    return this.width * this.height;
  }
}
