export class SymbolFilter {
	test(input : String) : boolean {
		 int length=input.length( );
		 int i=0;
		if(i<length){ 
		 char c=input.charAt( i);
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
