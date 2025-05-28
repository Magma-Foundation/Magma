export class NumberFilter {
	test(input : String) : boolean {
		length : int=input.length( );
		i : int=0;
		if(i<length){ 
		c : char=input.charAt( i);
		if(Character.isDigit( c)){ 
		i++;
		continue;}
		return false;}
		return true;
	}
	createMessage() : String {
		return "Not a number";
	}
}
