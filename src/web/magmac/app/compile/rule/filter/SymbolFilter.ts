export class SymbolFilter {
	isValidSymbolChar(c : char, index : int) : boolean {return Character.isLetter( c)||( 0!=index&&Character.isDigit( c))||'_'==c;;}
	test(input : String) : boolean {break;break;if(true){ break;if(true){ i++;continue;;}return false;;}return true;;}
	createMessage() : String {return "Not a symbol";;}
}
