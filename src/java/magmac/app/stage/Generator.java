package magmac.app.stage;

import magmac.app.io.Location;

import java.util.Map;

public interface Generator {
    Map<Location, String> generateAll(Roots roots);
}
