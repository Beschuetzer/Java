package major.adam;

import java.util.HashSet;
import java.util.Objects;

public class HeavenlyBody {
    private final double orbitalPeriod;
    private final HashSet<HeavenlyBody> satellites;
    private final Key key;

    public HeavenlyBody(String name, double orbitalPeriod) {
        this(name, orbitalPeriod, CelestialBodyType.UNKNOWN);
    }

    public HeavenlyBody(String name, double orbitalPeriod, CelestialBodyType bodyType) {
        this.orbitalPeriod = orbitalPeriod;
        this.satellites = new HashSet<>();
        this.key = new Key(name, bodyType);
    }

    public Key getKey() {
        return key;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public boolean addSatellite(HeavenlyBody toAdd) {
        if (toAdd != null) {
            this.satellites.add(new HeavenlyBody(toAdd.key.getName(), toAdd.orbitalPeriod));
            return true;
        }
        return false;
    }

    public HashSet<HeavenlyBody> getSatellites() {
        return new HashSet<>(satellites);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (obj instanceof HeavenlyBody) {
            HeavenlyBody heavenlyBody = (HeavenlyBody) obj;
            return this.getKey().equals(((HeavenlyBody) obj).getKey());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    public static Key makeKey(String name, CelestialBodyType bodyType) {
        return new Key(name, bodyType);
    }

    public static final class Key {
        private final String name;
        private final CelestialBodyType bodyType;

        private Key(String name, CelestialBodyType bodyType) {
            this.name = name;
            this.bodyType = bodyType;
        }

        public String getName() {
            return name;
        }

        public CelestialBodyType getBodyType() {
            return bodyType;
        }

        @Override
        public final boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;

            Key key = (Key) obj;
            if (this.name.equals(key.getName())) {
                return (key.getBodyType().equals(this.getBodyType()));
            }
            return false;
        }

        @Override
        public final int hashCode() {
            return Objects.hash(name, bodyType);
        }
    }
}

