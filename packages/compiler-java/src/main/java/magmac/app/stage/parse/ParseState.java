package magmac.app.stage.parse;

import magmac.app.io.Location;

/**
 * Information tracked while parsing source units.
 */
public interface ParseState {
    /**
     * Returns the current source location.
     */
    Location findLocation();
}
