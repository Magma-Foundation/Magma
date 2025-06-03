import { Option } from "../../magmac/api/Option";
import { Error } from "../../magmac/api/error/Error";
import { JavaRoot } from "../../magmac/app/lang/node/JavaRoot";
import { UnitSet } from "../../magmac/app/stage/unit/UnitSet";
export interface Application {
	parseAndStore(units : UnitSet<JavaRoot>) : Option<Error>;
}
