import from 'magma.api.collect.List'.ts;
import from 'magma.api.result.Result'.ts;
import from 'magma.api.option.Option'.ts;
interface Path  {
	writeString(output: string): Option<IOError>;
	readString(): Result<string, IOError>;
	resolveSibling(siblingName: string): Path;
	walk(): Result<List<Path>, IOError>;
	findFileName(): string;
	endsWith(suffix: string): boolean;
}
