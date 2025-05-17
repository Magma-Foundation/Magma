import { Platform } from "../../../../magma/app/io/Platform";
export interface FunctionHeader<S extends FunctionHeader<S>> {
	generateWithAfterName(platform: Platform, afterName: string): string;
	hasAnnotation(annotation: string): boolean;
	removeModifier(modifier: string): S;
	addModifier(modifier: string): S;
}
