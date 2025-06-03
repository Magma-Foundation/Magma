import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Iters } from "../../../../magmac/api/iter/Iters";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { InitialDestructor } from "../../../../magmac/app/compile/node/InitialDestructor";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Annotation } from "../../../../magmac/app/lang/common/Annotation";
import { Arguments } from "../../../../magmac/app/lang/node/Arguments";
import { ConditionalType } from "../../../../magmac/app/lang/node/ConditionalType";
import { FunctionSegmentValues } from "../../../../magmac/app/lang/node/FunctionSegmentValues";
import { FunctionSegments } from "../../../../magmac/app/lang/node/FunctionSegments";
import { LambdaContents } from "../../../../magmac/app/lang/node/LambdaContents";
import { Modifier } from "../../../../magmac/app/lang/node/Modifier";
import { OperationDeserializer } from "../../../../magmac/app/lang/node/OperationDeserializer";
import { Operator } from "../../../../magmac/app/lang/node/Operator";
import { PostVariant } from "../../../../magmac/app/lang/node/PostVariant";
import { StructureStatementValue } from "../../../../magmac/app/lang/node/StructureStatementValue";
import { TypedDeserializer } from "../../../../magmac/app/lang/node/TypedDeserializer";
import { TypescriptLang } from "../../../../magmac/app/lang/web/TypescriptLang";
import { * } from "../../../../static magmac/app/lang/java/JavaLang/*";
export class JavaDeserializers {
	deserialize(node : Node) : CompileResult<JavaCaller> {return 0;;}
	deserializeConstruction(node : Node) : Option<CompileResult<JavaCaller>> {return 0;;}
	deserializeInvocation(node : Node) : Option<CompileResult<JavaInvokable>> {return 0;;}
	deserializeRootSegment(node : Node) : CompileResult<JavaRootSegment> {return 0;;}
	deserializeAccess(type : JavaAccessType, node : Node) : Option<CompileResult<JavaAccess>> {return 0;;}
	deserializeYield(node : Node) : Option<CompileResult<JavaYieldNode>> {return 0;;}
	deserializePost(variant : PostVariant, node : Node) : Option<CompileResult<JavaPost>> {return 0;;}
	deserializeLambda(node : Node) : Option<CompileResult<JavaLambda>> {return 0;;}
	deserializeAccessWithType(type : JavaAccessType) : TypedDeserializer<JavaAccess> {return 0;;}
	deserializeFunctionStatement(node : Node) : Option<CompileResult<JavaFunctionStatement>> {return 0;;}
	deserializeReturn(node : Node) : Option<CompileResult<JavaReturnNode>> {return 0;;}
	deserializeBlockHeader(node : Node) : CompileResult<JavaBlockHeader> {return 0;;}
	deserializeBlock(node : Node) : Option<CompileResult<JavaBlock>> {return 0;;}
	deserializeConditional(type : ConditionalType, node : Node) : Option<CompileResult<JavaConditional>> {return 0;;}
	deserializeStructureStatement(node : Node) : CompileResult<StructureStatementValue> {return 0;;}
	deserializeBreak(node : Node) : Option<CompileResult<JavaBreak>> {return 0;;}
	deserializeContinue(node : Node) : Option<CompileResult<JavaContinue>> {return 0;;}
	deserializeWhitespace(node : Node) : Option<CompileResult<JavaLang.JavaWhitespace>> {return 0;;}
	deserializeTry(node : Node) : Option<CompileResult<JavaTry>> {return 0;;}
	deserializeElse(node : Node) : Option<CompileResult<JavaElse>> {return 0;;}
	deserializeString(node : Node) : Option<CompileResult<JavaStringNode>> {return 0;;}
	deserializeChar(node : Node) : Option<CompileResult<JavaCharNode>> {return 0;;}
	deserializeLambdaHeader(node : Node) : CompileResult<JavaLambdaHeader> {return 0;;}
	deserializeNumber(node : Node) : Option<CompileResult<JavaNumberNode>> {return 0;;}
	deserializeNot(node : Node) : Option<CompileResult<JavaNot>> {return 0;;}
	deserializeIndex(value : Node) : Option<CompileResult<JavaIndexNode>> {return 0;;}
	deserializeSwitch(node : Node) : Option<CompileResult<JavaSwitchNode>> {return 0;;}
	deserializeValue(node : Node) : Option<CompileResult<JavaValue>> {break;break;return 0;;}
	wrapAsDeserializer(operator : Operator) : TypedDeserializer<JavaValue> {return 0;;}
	deserializeJavaOrError(node : Node) : CompileResult<JavaValue> {return 0;;}
	deserializeSymbol(node : Node) : Option<CompileResult<JavaSymbol>> {return 0;;}
	deserializeLambdaParameter(node : Node) : CompileResult<JavaLambdaParameter> {return 0;;}
	deserializeDefinition(node : Node) : CompileResult<JavaDefinition> {return 0;;}
	deserializeTypedDefinition(node : Node) : Option<CompileResult<JavaDefinition>> {return 0;;}
	deserializeMultipleHeader(node : Node) : Option<CompileResult<JavaLambdaHeader>> {return 0;;}
	deserialize0(deserialize : InitialDestructor) : CompileResult<CommonLang.Definition<JavaType>> {return 0;;}
}
