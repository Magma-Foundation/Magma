export class SymbolFilter {
	test(input : String) : boolean {
		length : int=input.length( );
		i : int=0;
		if(i<length){ 
		c : char=input.charAt( i);
		if(Character.isLetter( c)||( 0!=i&&Character.isDigit( c))){ 
		i++;
		continue;}
		return false;}
		return true;
	}
	createMessage() : String {
		return "Not a symbol";
	}
}
