package magmac.app.stage.parse;

import magmac.app.io.Location;

public final class ImmutableParseState implements ParseState {
    private final Location location;

    public ImmutableParseState(Location location) {
        this.location = location;
    }

    @Override
    public Location findLocation() {
        return this.location;
    }
}
