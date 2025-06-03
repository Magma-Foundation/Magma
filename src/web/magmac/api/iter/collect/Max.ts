export class Max {
	public createInitial() : Optional<Integer> {return Optional.empty( );;}
	public fold( current : Optional<Integer>,  element : Integer) : Optional<Integer> {return Optional.of( current.filter( 0).orElse( element));;}
}
