#include "State.h"
auto __lambda0__();
auto __lambda1__();
magma.compile.transform.public State(magma.compile.source.Location location, magma.collect.list.List_<magma.collect.list.List_<String>> imports, magma.collect.list.List_<magma.compile.transform.Frame> frames){this.location = location;
        this.imports = imports;
        this.frames = frames;
}
magma.compile.transform.public State(magma.compile.source.Location location){this(location, Lists.empty(), Lists.of(Frame()));
}
magma.compile.transform.State defineImport(magma.compile.Node import_){List_<String> namespace = import_.findNodeList("namespace")
                .orElse(Lists.empty())
                .stream()
                .map(node -> node.findString("value"))
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<>());return State(location, imports.add(namespace), frames);
}
magma.compile.transform.State clearImports(){return State(location, imports.clear(), frames);
}
magma.option.Option<magma.collect.list.List_<String>> qualifyName(String name){if (name.isEmpty()) {
            return new None<>();
        }if (name.equals(location.name())) {
            return new Some<>(location.namespace().add(location.name()));
        }return imports.stream().filter(__lambda0__.findLast().orElse("").equals(name)).next();
}
magma.compile.transform.State defineType(magma.compile.Node type){List_<Frame> newFrames = frames.mapLast(last -> last.defineType(type));return State(location, imports, newFrames);
}
magma.compile.transform.State enter(){return State(location, imports, frames.add(Frame()));
}
magma.compile.transform.State exit(){return State(location, imports, frames.subList(0, frames.size()-1));
}
int isTypeParamDefined(String type){return frames.stream().anyMatch(__lambda1__.isTypeDefined(type));
}
magma.collect.list.List_<String> namespace(){return location.namespace();
}
String name(){return location.name();
}
