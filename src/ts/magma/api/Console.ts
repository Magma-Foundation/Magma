import { Actual } from "../../magma/annotate/Actual";
import { Namespace } from "../../magma/annotate/Namespace";
export interface ConsoleInstance {
	printErrLn(message: string): void;

}
export declare const Console: ConsoleInstance;
