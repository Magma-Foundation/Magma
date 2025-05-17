import { Path } from "../../../magma/api/io/Path";
import { IOError } from "../../../magma/api/io/IOError";
import { Result } from "../../../magma/api/result/Result";
import { List } from "../../../magma/api/collect/list/List";
import { ListCollector } from "../../../magma/api/collect/list/ListCollector";
import { Location } from "../../../magma/app/io/Location";
export class Source {
	sourceDirectory: Path;
	source: Path;
	constructor (sourceDirectory: Path, source: Path) {
		this.sourceDirectory = sourceDirectory;
		this.source = source;
	}
	read(): Result<string, IOError> {
		return this/*auto*/.source.readString(/*auto*/);
	}
	computeName(): string {
		let fileName: string = this/*auto*/.source.findFileName(/*auto*/);
		let separator: number = fileName/*auto*/.lastIndexOf(".");
		return fileName/*auto*/.substring(0/*auto*/, separator/*auto*/);
	}
	computeNamespace(): List<string> {
		return this/*auto*/.sourceDirectory.relativize(this/*auto*/.source).getParent(/*auto*/).query(/*auto*/).collect(new ListCollector<string>(/*auto*/));
	}
	computeLocation(): Location {
		return new Location(this/*auto*/.computeNamespace(/*auto*/), this/*auto*/.computeName(/*auto*/));
	}
}
