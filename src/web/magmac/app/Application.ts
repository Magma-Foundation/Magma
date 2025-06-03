import { Option } from "../../magmac/api/Option";
import { Error } from "../../magmac/api/error/Error";
import { JavaLang } from "../../magmac/app/lang/java/JavaLang";
import { UnitSet } from "../../magmac/app/stage/unit/UnitSet";
export interface Application {
	 parseAndStore( units : UnitSet<JavaLang.JavaRoot>) : Option<Error>;
}
