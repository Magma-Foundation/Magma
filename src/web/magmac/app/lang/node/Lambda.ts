import { JavaLang } from "../../../../magmac/app/lang/java/JavaLang";
export class Lambda {
	Lambda(header : JavaLang.JavaLambdaHeader, content : JavaLang.JavaLambdaContent) : public {this.header=header;this.content=content;;}
	header() : JavaLang.JavaLambdaHeader {return header;;}
	content() : JavaLang.JavaLambdaContent {return content;;}
}
