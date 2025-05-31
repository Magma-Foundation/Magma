import { Option } from "../../magmac/api/Option";
import { Error } from "../../magmac/api/error/Error";
import { JavaRootSegment } from "../../magmac/app/lang/node/JavaRootSegment";
import { Root } from "../../magmac/app/lang/node/Root";
import { UnitSet } from "../../magmac/app/stage/unit/UnitSet";
export interface Application {
	parseAndStore(units : UnitSet<Root<JavaRootSegment>>) : Option<Error>;
}
