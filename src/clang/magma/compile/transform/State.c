#include "State.h"
int __lambda0__(){return import_;
}
int __lambda1__(){return frame;
}
public State(Location location, List_<List_<String>> imports, List_<Frame> frames){this.location = location;
        this.imports = imports;
        this.frames = frames;
}
public State(Location location){this(location, Lists.empty(), Lists.of(Frame()));
}
State defineImport(Node import_){List_<String> namespace = import_.findNodeList("namespace")
                .orElse(Lists.empty())
                .stream()
                .map(node -> node.findString("value"))
                .flatMap(Streams::fromOption)
                .collect(new ListCollector<>());return State(location, imports.add(namespace), frames);
}
State clearImports(){return State(location, imports.clear(), frames);
}
Option<List_<String>> qualifyName(String name){if (name.isEmpty()) {
            return new None<>();
        }if (name.equals(location.name())) {
            return new Some<>(location.namespace().add(location.name()));
        }return imports.stream().filter(__lambda0__.findLast().orElse("").equals(name)).next();
}
State defineType(Node type){List_<Frame> newFrames = frames.mapLast(last -> last.defineType(type));return State(location, imports, newFrames);
}
State enter(){return State(location, imports, frames.add(Frame()));
}
State exit(){return State(location, imports, frames.subList(0, frames.size()-1));
}
int isTypeParamDefined(String type){return frames.stream().anyMatch(__lambda1__.isTypeDefined(type));
}
List_<String> namespace(){return location.namespace();
}
String name(){return location.name();
}
