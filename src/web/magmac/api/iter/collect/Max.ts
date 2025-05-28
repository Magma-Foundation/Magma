import { Optional } from "../../../../java/util/Optional";
export class Max {
	public createInitial() : Optional<Integer> {
		return Optional.empty( );
	}
	public fold( current : Optional<Integer>,  element : Integer) : Optional<Integer> {
		return Optional.of( current.filter( ( inner : Integer) => inner>element).orElse( element));
	}
}
