import { IOResult } from "../../../../magmac/app/io/IOResult";
import { Location } from "../../../../magmac/app/io/Location";
export interface Source { computeName()() : String; read()() : IOResult<String>; computeLocation()() : Location;
}
