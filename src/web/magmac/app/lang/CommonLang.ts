export class AbstractDefinition<T> {
	 AbstractDefinition( maybeAnnotations : Option<List<Annotation>>,  modifiers : List<Modifier>,  name : String,  maybeTypeParams : Option<List<TypescriptLang.TypeParam>>,  type : T) : public {this.maybeAnnotations=maybeAnnotations;this.modifiers=modifiers;this.name=name;this.maybeTypeParams=maybeTypeParams;this.type=type;;}
	public maybeAnnotations() : Option<List<Annotation>> {return this.maybeAnnotations;;}
	public modifiers() : List<Modifier> {return this.modifiers;;}
	public name() : String {return this.name;;}
	public maybeTypeParams() : Option<List<TypescriptLang.TypeParam>> {return this.maybeTypeParams;;}
	public type() : T {return this.type;;}
}
export class CommonLang {
	public static Statements( key : String,  childRule : Rule) : Rule {return NodeListRule.createNodeListRule( key, new StatementFolder( ), childRule);;}
}
