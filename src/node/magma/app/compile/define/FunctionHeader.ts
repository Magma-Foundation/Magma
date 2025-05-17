export interface FunctionHeader<S extends FunctionHeader<S>> {
	generateWithAfterName(afterName: string): string;
	hasAnnotation(annotation: string): boolean;
	removeModifier(modifier: string): S;
	addModifier(modifier: string): S;
}
