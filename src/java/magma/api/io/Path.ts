import { List } from "./magma/api/collect.ts";
import { Result } from "./magma/api/result.ts";
import { Option } from "./magma/api/option.ts";
export interface Path  {
	writeString(output: string): Option<IOError>;
	readString(): Result<string, IOError>;
	resolveSibling(siblingName: string): Path;
	walk(): Result<List<Path>, IOError>;
	findFileName(): string;
	endsWith(suffix: string): boolean;
}
