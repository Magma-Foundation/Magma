import { Location } from "../../../../magmac/app/io/Location";
export class ImmutableParseState {private final location() : Location;
	 ImmutableParseState( location() : Location) : public {
		this.location=location;
	}
	public findLocation() : Location {
		return this.location;
	}
}
