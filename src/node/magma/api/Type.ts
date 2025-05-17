export interface Type {
	generate(): string;
	isFunctional(): boolean;
	isVar(): boolean;
	generateBeforeName(): string;
}
