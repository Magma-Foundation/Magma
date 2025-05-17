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
		return this.source.readString();
	}
	computeName(): string {
		let fileName = this.source.findFileName();
		let separator = fileName.lastIndexOf(".");
		return fileName.substring(0, separator);
	}
	computeNamespace(): List<string> {
		return this.sourceDirectory.relativize(this.source).getParent().query().collect(new ListCollector<string>());
	}
	computeLocation(): Location {
		return new Location(this.computeNamespace(), this.computeName());
	}
}
