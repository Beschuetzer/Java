package major.adam;

import java.util.HashSet;
import java.util.Objects;

public class HeavenlyBody {
    private String name;
    private double orbitalPeriod;
    private HashSet<HeavenlyBody> satellites;

    public HeavenlyBody(String name, double rotationPeriod) {
        this.name = name;
        this.orbitalPeriod = rotationPeriod;
        this.satellites = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public HeavenlyBody(HashSet<HeavenlyBody> satellites) {
        this.satellites = new HashSet<>(satellites);
    }

    public boolean addSatellite(HeavenlyBody toAdd) {
        if (toAdd != null) {
            this.satellites.add(new HeavenlyBody(toAdd.getName(), toAdd.orbitalPeriod));
            return  true;
        }
        return false;
    }

    public HashSet<HeavenlyBody> getSatellites() {
        return new HashSet<>(satellites);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeavenlyBody that = (HeavenlyBody) o;
        return orbitalPeriod == that.orbitalPeriod && Objects.equals(name, that.name) && Objects.equals(satellites, that.satellites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, orbitalPeriod, satellites);
    }

}
