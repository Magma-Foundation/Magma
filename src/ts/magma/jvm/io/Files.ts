import { Actual } from "../../../magma/annotate/Actual";
import { Namespace } from "../../../magma/annotate/Namespace";
import { List } from "../../../magma/api/collect/List";
import { Lists } from "../../../magma/api/collect/Lists";
import { RangeHead } from "../../../magma/api/collect/RangeHead";
import { HeadedQuery } from "../../../magma/api/collect/query/HeadedQuery";
import { Query } from "../../../magma/api/collect/query/Query";
import { IOError } from "../../../magma/api/io/IOError";
import { Path } from "../../../magma/api/io/Path";
import { None } from "../../../magma/api/option/None";
import { Option } from "../../../magma/api/option/Option";
import { Some } from "../../../magma/api/option/Some";
import { Result } from "../../../magma/api/result/Result";
import { Main } from "../../../magma/app/Main";
import { IOException } from "../../../java/io/IOException";
import { PrintWriter } from "../../../java/io/PrintWriter";
import { StringWriter } from "../../../java/io/StringWriter";
import { Paths } from "../../../java/nio/file/Paths";
import { Stream } from "../../../java/util/stream/Stream";
export interface FilesInstance {
	get(first: string, ...more: string[]): Path;

}
export declare const Files: FilesInstance;
