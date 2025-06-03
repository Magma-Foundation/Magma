import { Location } from "../../../../magmac/app/io/Location";
export class ImmutableParseState {
	ImmutableParseState(location : Location) : public {this.location=location;;}
	findLocation() : Location {return this.location;;}
}
