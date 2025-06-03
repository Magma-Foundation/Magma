import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { InitialDestructor } from "../../../magmac/app/compile/node/InitialDestructor";
import { InitialDestructorImpl } from "../../../magmac/app/compile/node/InitialDestructorImpl";
import { Node } from "../../../magmac/app/compile/node/Node";
export class Destructors {
	destructWithType(type : String, node : Node) : Option<InitialDestructor> {if(true){ return new Some<>( new InitialDestructorImpl( node));;}return new None<>( );;}
	destruct(node : Node) : InitialDestructor {return new InitialDestructorImpl( node);;}
}
