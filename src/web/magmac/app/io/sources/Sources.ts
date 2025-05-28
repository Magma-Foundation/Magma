import { IOResult } from "../../../../magmac/app/io/IOResult";
import { Location } from "../../../../magmac/app/io/Location";
import { Map } from "../../../../magmac/api/collect/map/Map";
export interface Sources { readAll()() : IOResult<Map<Location, String>>;
}
