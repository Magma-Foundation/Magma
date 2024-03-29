const { ArrayList } = require("java.util");
const { List } = require("java.util");
const { Optional } = require("java.util");setExportssetFunctionssetImportStringsetStructscreateState
function StateBuilder(){
    private String value = ""
    private Optional<String> exports = Optional.empty()
    private List<String> functions = new ArrayList<>()
    private Optional<String> importString = Optional.empty()
    private Optional<String> structs = Optional.empty()
	pub def setValue(value : String) : StateBuilder => {
        this.value = value;
        return this;
    }
	function setExports(exports){
        this.exports = exports;
        return this;
    }
	function setFunctions(functions){
        this.functions = functions;
        return this;
    }
	function setImportString(importString){
        this.importString = importString;
        return this;
    }
	function setStructs(structs){
        this.structs = structs;
        return this;
    }
	function createState(){
        return new ApplicationState(importString, value, exports, structs, functions);
    }
	return {setExportssetFunctionssetImportStringsetStructscreateState};
}
module.exports = {
	StateBuilder
};