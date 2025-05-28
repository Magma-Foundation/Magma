import { Optional } from "../../../../java/util/Optional";
export class Max {
	createInitial() : Optional<Integer> {
		return Optional.empty( );
	}
	fold(current : Optional<Integer>, element : Integer) : Optional<Integer> {
		return Optional.of( current.filter( (inner : Integer) => inner>element).orElse( element));
	}
}
