package major.adam;

import java.util.List;

public interface ISaveable {
    List<String> save();
    boolean load(List<String> valuesToLoad);
}
