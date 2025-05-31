import { IOResult } from "../../../../magmac/app/io/IOResult";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
export interface Sources {
	readAll() : IOResult<UnitSet<String>>;
}
