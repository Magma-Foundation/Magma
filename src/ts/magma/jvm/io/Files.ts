/*[
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect, 
	Head: magma.api.collect, 
	JVMList: magma.api.collect, 
	List: magma.api.collect, 
	ListCollector: magma.api.collect, 
	Lists: magma.api.collect, 
	HeadedQuery: magma.api.collect.query, 
	Query: magma.api.collect.query, 
	RangeHead: magma.api.collect, 
	SingleHead: magma.api.collect, 
	Console: magma.api, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Result: magma.api.result, 
	Characters: magma.api.text, 
	Strings: magma.api.text, 
	Tuple2: magma.api, 
	Tuple2Impl: magma.api, 
	Main: magma.app, 
	Files: magma.jvm.io
]*/
import { Actual } from "../../../magma/annotate/Actual";
import { Namespace } from "../../../magma/annotate/Namespace";
import { JVMList } from "../../../magma/api/collect/JVMList";
import { List } from "../../../magma/api/collect/List";
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
