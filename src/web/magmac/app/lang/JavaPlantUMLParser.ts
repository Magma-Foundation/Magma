export class JavaPlantUMLParser {
	private static parseNamespaced( child : String,  namespaced : JavaNamespacedNode) : Iter<PlantUMLRootSegment> {return 0;;}
	private static createSimpleName( base : JavaLang.Base) : String {return 0;;}
	private static createStructureSegment( structure : JavaLang.Structure) : PlantUMLRootSegment { let name : var=structure.name( ); let type : var=structure.type( );return 0;;}
	private static createSimpleNameFromType( type : JavaLang.JavaType) : String {return 0;;}
	private static createSimpleNameFromQualifiedType( qualified : JavaLang.Qualified) : String {return qualified.segments( ).iter( ).map( Segment.value).collect( new Joiner( ".")).orElse( "");;}
	private static parseRoot( unit : Unit<JavaLang.Root>) : Iter<PlantUMLRootSegment> {return unit.destruct( 0);;}
	private static parseRootSegment( fileName : String,  rootSegment : JavaRootSegment) : Iter<PlantUMLRootSegment> {return 0;;}
	private static parseStructure( structure : JavaLang.Structure) : Iter<PlantUMLRootSegment> { let segment : var=JavaPlantUMLParser.createStructureSegment( structure); let child : var=structure.name( );return Lists.of( segment).addAllLast( JavaPlantUMLParser.toInherits( child, structure.extended( ))).addAllLast( JavaPlantUMLParser.toInherits( child, structure.implemented( ))).iter( );;}
	private static toInherits( child : String,  maybeOption : Option<List<JavaLang.JavaType>>) : List<PlantUMLRootSegment> {return maybeOption.orElse( Lists.empty( )).iter( ).map( JavaPlantUMLParser.createSimpleNameFromType).map( 0).collect( new ListCollector<>( ));;}
	private static getPlantUMLInherits( child : String,  parent : String) : PlantUMLRootSegment {return new PlantUMLInherits( child, parent);;}
	public apply( initial : UnitSet<JavaLang.Root>) : CompileResult<UnitSet<PlantUMLRoot>> { let roots : var=initial.iter( ).flatMap( JavaPlantUMLParser.parseRoot).collect( new ListCollector<>( )).addFirst( new PlantUMLHeader( )).addLast( new PlantUMLFooter( )); let defaultLocation : var=new Location( Lists.empty( ), "diagram"); let mergedRoot : var=new PlantUMLRoot( roots);return CompileResults.Ok( new MapUnitSet<PlantUMLRoot>( ).add( new SimpleUnit<>( defaultLocation, mergedRoot)));;}
}
