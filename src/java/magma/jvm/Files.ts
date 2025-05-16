import { Actual } from "./magma.ts";
import { List } from "./magma/api/collect.ts";
import { IOError } from "./magma/api/io.ts";
import { Path } from "./magma/api/io.ts";
import { Result } from "./magma/api/result.ts";
import { Option } from "./magma/api/option.ts";
import { Main } from "./magma/app.ts";
import { IOException } from "./java/io.ts";
import { PrintWriter } from "./java/io.ts";
import { StringWriter } from "./java/io.ts";
import { Paths } from "./java/nio/file.ts";
import { Stream } from "./java/util/stream.ts";
class Files  {
	static get(first: string, ...more: string[]): Path;
}
