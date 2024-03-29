#include "StateBuilder.c"
StateBuilder setExports(struct StateBuilder* __self__, Optional<String> exports){
        this.exports = exports;
        return this;
    }
StateBuilder setFunctions(struct StateBuilder* __self__, List<String> functions){
        this.functions = functions;
        return this;
    }
StateBuilder setImportString(struct StateBuilder* __self__, Optional<String> importString){
        this.importString = importString;
        return this;
    }
StateBuilder setStructs(struct StateBuilder* __self__, Optional<String> structs){
        this.structs = structs;
        return this;
    }
ApplicationState createState(struct StateBuilder* __self__){
        return new ApplicationState(importString, value, exports, structs, functions);
    }
struct StateBuilder StateBuilder(){
    private String value = ""
    private Optional<String> exports = Optional.empty()
    private List<String> functions = new ArrayList<>()
    private Optional<String> importString = Optional.empty()
    private Optional<String> structs = Optional.empty()
	pub def setValue(value : String) : StateBuilder => {
        this.value = value;
        return this;
    }
	struct StateBuilder this = {};
	return this;
}