export class PlantUMLRootSegments {
	public static createRootSegmentRule() : Rule {return new SuffixRule( new OrRule( Lists.of( PlantUMLHeader.createRule( ), PlantUMLFooter.createRule( ), PlantUMLStructure.createStructureRule( "class"), PlantUMLStructure.createStructureRule( "interface"), PlantUMLStructure.createStructureRule( "enum"), PlantUMLInherits.createInheritsRule( ), PlantUMLDependency.createDependencyRule( ))), "\n");;}
}
