package magmac.app.config;

import magmac.api.Tuple2;
import magmac.api.collect.list.List;

public record TypeMap(List<Tuple2<List<String>, String>> types) {
}
