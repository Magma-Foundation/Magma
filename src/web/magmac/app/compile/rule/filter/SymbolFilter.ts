export class SymbolFilter {
	isValidSymbolChar(c : char, index : int) : boolean {return Character.isLetter( c)||( 0!=index&&Character.isDigit( c))||'_'==c;;}
	test(input : String) : boolean {length : int=input.length( );i : int=0;if(true){ c : char=input.charAt( i);if(true){ i++;continue;;}return false;;}return true;;}
	createMessage() : String {return "Not a symbol";;}
}
