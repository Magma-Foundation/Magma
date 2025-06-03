import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
export class TupleCollector<A,  AC,  B,  BC> {
	createInitial() : Tuple2<AC, BC> {return new Tuple2<>( this.leftCollector.createInitial( ), this.rightCollector.createInitial( ));;}
	fold(current : Tuple2<AC, BC>, element : Tuple2<A, B>) : Tuple2<AC, BC> {leftValue : AC=this.leftCollector.fold( current.left( ), element.left( ));rightValue : BC=this.rightCollector.fold( current.right( ), element.right( ));return new Tuple2<>( leftValue, rightValue);;}
}
