import { Optional } from "../../../../java/util/Optional";
export class Max {
	createInitial() : Optional<Integer> {
		return Optional.empty( );
	}
	fold(current : Optional<Integer>, element : Integer) : Optional<Integer> {
		return Optional.of( current.filter( (Integer inner)  => inner>element).orElse( element));
	}
}
