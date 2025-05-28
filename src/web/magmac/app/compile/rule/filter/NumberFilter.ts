export class NumberFilter {
	test(input : String) : boolean { int length=input.length( ); int i=0;if(i<length){  char c=input.charAt( i);if(Character.isDigit( c)){ i++;continue;}return false;}return true;}
	createMessage() : String {return "Not a number";}
}
