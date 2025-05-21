package magma.app;

import magma.api.collect.list.List;

public record Location(List<String> namespace, String name) {
}
