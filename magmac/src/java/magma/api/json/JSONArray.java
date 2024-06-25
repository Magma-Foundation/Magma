package magma.api.json;

import magma.api.collect.List;

public record JSONArray(List<JSONValue> items) implements JSONValue {
}
