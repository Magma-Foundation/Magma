import { Location } from "../../../../magmac/app/io/Location";
export class ImmutableParseState {
	temp : ?;
	ImmutableParseState(location : Location) : public {
		this.location=location;
	}
	findLocation() : Location {
		return this.location;
	}
}
