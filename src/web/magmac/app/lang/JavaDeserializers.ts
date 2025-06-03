import { Option } from "../../../magmac/api/Option";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../magmac/app/compile/node/Node";
import { FunctionStatement } from "../../../magmac/app/lang/common/FunctionStatement";
import { JavaAssignmentNode } from "../../../magmac/app/lang/java/JavaAssignmentNode";
import { JavaBreak } from "../../../magmac/app/lang/java/JavaBreak";
import { JavaConstruction } from "../../../magmac/app/lang/java/JavaConstruction";
import { JavaContinue } from "../../../magmac/app/lang/java/JavaContinue";
import { JavaFunctionSegmentValue } from "../../../magmac/app/lang/java/JavaFunctionSegmentValue";
import { JavaFunctionStatement } from "../../../magmac/app/lang/java/JavaFunctionStatement";
import { JavaInvokable } from "../../../magmac/app/lang/java/JavaInvokable";
import { Lang } from "../../../magmac/app/lang/java/Lang";
import { Arguments } from "../../../magmac/app/lang/node/Arguments";
import { Catch } from "../../../magmac/app/lang/node/Catch";
import { ConditionalType } from "../../../magmac/app/lang/node/ConditionalType";
import { Else } from "../../../magmac/app/lang/node/Else";
import { FunctionSegmentValues } from "../../../magmac/app/lang/node/FunctionSegmentValues";
import { FunctionSegments } from "../../../magmac/app/lang/node/FunctionSegments";
import { JavaAccess } from "../../../magmac/app/lang/node/JavaAccess";
import { JavaAccessType } from "../../../magmac/app/lang/node/JavaAccessType";
import { JavaBlock } from "../../../magmac/app/lang/java/JavaBlock";
import { JavaBlockHeader } from "../../../magmac/app/lang/node/JavaBlockHeader";
import { JavaConditional } from "../../../magmac/app/lang/node/JavaConditional";
import { JavaDefinition } from "../../../magmac/app/lang/java/JavaDefinition";
import { JavaLambda } from "../../../magmac/app/lang/node/JavaLambda";
import { JavaNamespacedNode } from "../../../magmac/app/lang/java/JavaNamespacedNode";
import { JavaPost } from "../../../magmac/app/lang/java/JavaPost";
import { JavaReturnNode } from "../../../magmac/app/lang/java/JavaReturnNode";
import { JavaRootSegment } from "../../../magmac/app/lang/java/JavaRootSegment";
import { JavaStructureNodeDeserializer } from "../../../magmac/app/lang/node/JavaStructureNodeDeserializer";
import { JavaStructureType } from "../../../magmac/app/lang/node/JavaStructureType";
import { JavaTypes } from "../../../magmac/app/lang/node/JavaTypes";
import { JavaYieldNode } from "../../../magmac/app/lang/java/JavaYieldNode";
import { LambdaContents } from "../../../magmac/app/lang/node/LambdaContents";
import { LambdaHeaders } from "../../../magmac/app/lang/node/LambdaHeaders";
import { PostVariant } from "../../../magmac/app/lang/node/PostVariant";
import { StructureStatementValue } from "../../../magmac/app/lang/node/StructureStatementValue";
import { Try } from "../../../magmac/app/lang/node/Try";
import { TypedDeserializer } from "../../../magmac/app/lang/node/TypedDeserializer";
import { Values } from "../../../magmac/app/lang/node/Values";
import { JavaWhitespace } from "../../../magmac/app/lang/java/JavaWhitespace";
export class JavaDeserializers {
	deserialize(node : Node) : CompileResult<Lang.JavaCaller> {return 0;;}
	deserializeConstruction(node : Node) : Option<CompileResult<Lang.JavaCaller>> {return 0;;}
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
	deserializeWhitespace(node : Node) : Option<CompileResult<JavaWhitespace>> {return 0;;}
}
