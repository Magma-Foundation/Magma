export class SymbolFilter {
	private static isValidSymbolChar( c : char,  index : int) : boolean {return Character.isLetter( c)||( 0!=index&&Character.isDigit( c))||'_'==c;;}
	public test( input : String) : boolean { let length : var=input.length( ); let i : var=0;if(true){  let c : var=input.charAt( i);if(true){ i++;continue;;}return false;;}return true;;}
	public createMessage() : String {return "Not a symbol";;}
}
