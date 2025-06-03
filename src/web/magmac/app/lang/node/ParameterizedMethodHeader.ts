import { List } from "../../../../magmac/api/collect/list/List";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { TypescriptLang } from "../../../../magmac/app/lang/web/TypescriptLang";
export class ParameterizedMethodHeader<T extends Serializable> {
	public serialize() : Node {return this.header.serialize( ).withNodeListSerialized( "parameters", this.parameters);;}
}
