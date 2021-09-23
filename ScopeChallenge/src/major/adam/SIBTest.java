package major.adam;

import java.util.Comparator;

public class SIBTest {
    static final Comparator<Seat> SORT_PRICE;
    static {
        SORT_PRICE = new Comparator<Seat>() {
            @Override
            public int compare(Seat seat1, Seat seat2) {
                if (seat1.price < seat2.price) return -1;
                else if (seat2.price > seat1.price) return 1;
                else {
                    if (seat1.name == seat2.name) return 0;
                    return ((String) seat1.name).compareToIgnoreCase((String) seat2.name);
                }
            }
        }
    }
}
