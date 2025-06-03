export class TupleCollector<A,  AC,  B,  BC> {
	public createInitial() : Tuple2<AC, BC> {return new Tuple2<>( this.leftCollector.createInitial( ), this.rightCollector.createInitial( ));;}
	public fold( current : Tuple2<AC, BC>,  element : Tuple2<A, B>) : Tuple2<AC, BC> { let leftValue : var=this.leftCollector.fold( current.left( ), element.left( )); let rightValue : var=this.rightCollector.fold( current.right( ), element.right( ));return new Tuple2<>( leftValue, rightValue);;}
}
