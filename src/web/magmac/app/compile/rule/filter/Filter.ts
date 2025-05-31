export interface Filter {
	test(input : String) : boolean;
	createMessage() : String;
}
