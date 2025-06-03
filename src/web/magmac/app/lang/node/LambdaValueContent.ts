import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
export class LambdaValueContent {
	LambdaValueContent(value : JavaLang.Value) : public {this.value=value;;}
	value() : JavaLang.Value {return value;;}
}
