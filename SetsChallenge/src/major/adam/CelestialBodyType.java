package major.adam;

public enum CelestialBodyType {
    STAR("Star"), PLANET("Planet"), MOON("Moon"), UNKNOWN("Unknown");

    private String name;

    CelestialBodyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
