typedef struct {
	char* (*asCharArray)();
} String_;
typedef struct {
	int length;
	int index;
	Option_int (*next)();
} RangeHead;
typedef struct {
	Option_char* (*createInitial)();
	Option_char* (*fold)();
} Joiner;
typedef struct {
	char left;
	DivideState right;
} Tuple_char_DivideState;
typedef struct {
	char* left;
	List__char* right;
} Tuple_char*_List__char*;
typedef struct {
	char left;
	List__char right;
} Tuple_char_List__char;
typedef struct {
	char* value;
	char* (*asCharArray)();
} JavaString;
typedef struct {
	String_ beforeType;
	String_ type;
	String_ name;
} Node;
typedef struct {
} Iterators;
typedef struct {
	Option_R (*map)();
	char* (*orElse)();
	int (*isPresent)();
	Option_char* (*or)();
	char* (*orElseGet)();
	Option_R (*flatMap)();
} Option_char*;
typedef struct {
	Option_R (*map)();
	char (*orElse)();
	int (*isPresent)();
	Option_char (*or)();
	char (*orElseGet)();
	Option_R (*flatMap)();
} Option_char;
typedef struct {
	Option_R (*map)();
	DivideState (*orElse)();
	int (*isPresent)();
	Option_DivideState (*or)();
	DivideState (*orElseGet)();
	Option_R (*flatMap)();
} Option_DivideState;
typedef struct {
	Option_R (*map)();
	R (*orElse)();
	int (*isPresent)();
	Option_R (*or)();
	R (*orElseGet)();
	Option_R (*flatMap)();
} Option_R;
typedef struct {
	DivideState (*popAndAppend)();
	DivideState (*advance)();
	DivideState (*append)();
	List__char* (*segments)();
	int (*isLevel)();
	DivideState (*enter)();
	DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	Tuple_char_DivideState (*pop)();
} DivideState;
typedef struct {
	Option_R (*map)();
	T (*orElse)();
	int (*isPresent)();
	Option_T (*or)();
	T (*orElseGet)();
	Option_R (*flatMap)();
} Option_T;
typedef struct {
	Option_R (*map)();
	int (*orElse)();
	int (*isPresent)();
	Option_int (*or)();
	int (*orElseGet)();
	Option_R (*flatMap)();
} Option_int;
typedef struct {
	char (*get)();
	int (*size)();
	List__char (*add)();
	int (*isEmpty)();
	Tuple_char_List__char (*pop)();
	Iterator_char (*iter)();
	List__char (*addAll)();
} List__char;
typedef struct {
	List__char queue;
	List__char* segments;
	StringBuilder buffer;
	int depth;
	DivideState (*advance)();
	DivideState (*append)();
	int (*isLevel)();
	DivideState (*enter)();
	DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	Tuple_char_DivideState (*pop)();
	DivideState (*popAndAppend)();
	List__char* (*segments)();
} MutableDivideState;
typedef struct {
	char* (*get)();
	int (*size)();
	List__char* (*add)();
	int (*isEmpty)();
	Tuple_char*_List__char* (*pop)();
	Iterator_char* (*iter)();
	List__char* (*addAll)();
} List__char*;
typedef struct {
	Iterator_R (*map)();
	C (*collect)();
	Iterator_char (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_char (*concat)();
	Option_char (*next)();
} Iterator_char;
typedef struct {
	Iterator_R (*map)();
	C (*collect)();
	Iterator_char* (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_char* (*concat)();
	Option_char* (*next)();
} Iterator_char*;
typedef struct {
	Iterator_R (*map)();
	C (*collect)();
	Iterator_R (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_R (*concat)();
	Option_R (*next)();
} Iterator_R;
typedef struct {
	Iterator_R (*map)();
	C (*collect)();
	Iterator_T (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_T (*concat)();
	Option_T (*next)();
} Iterator_T;
typedef struct {
} Main;
