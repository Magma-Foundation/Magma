export class SymbolFilter {
	isValidSymbolChar(c : char, index : int) : boolean;
	test(input : String) : boolean;
	createMessage() : String;
}
