export class JavaPost {
	 JavaPost( variant : PostVariant,  value : JavaLang.Value) : public {super( variant, value);;}
	public static createPostRule( type : String,  suffix : String,  value : Rule) : Rule {return new TypeRule( type, new StripRule( new SuffixRule( new NodeRule( "child", value), suffix)));;}
	public variant() : PostVariant {return variant;;}
	public value() : JavaLang.Value {return value;;}
}
