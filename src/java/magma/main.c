/* private *//* Type */struct Type {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private @ *//* Actual */struct Actual {/*  */
};
/* Symbols: [Type] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
/* private *//* Parameter */struct Parameter {/*  */
};
/* Symbols: [Type, Actual] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Parameter_generate(/*  */);
/* Option<content-startDefinition content-end> */struct Option_/* Definition */ Parameter_toDefinition(/*  */);
/* private static *//* StandardLibrary */struct StandardLibrary {/*  */
};
/* Symbols: [Type, Actual, Parameter] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [] */
/* private static *//* Lists */struct Lists {/*  */
};
/* Symbols: [Type, Actual, Parameter, StandardLibrary] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [] */
/* private sealed *//* Option<char*> */struct Option_char_ref {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* Iterator<char*> */struct Iterator_char_ref {/*  */
};
/* Option<char*> */struct Option_char_ref Iterator_char_ref_next(/*  */);
int Iterator_char_ref_anyMatch(int (*predicate)(char*));
int Iterator_char_ref_allMatch(int (*predicate)(char*));
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_filter(int (*predicate)(char*));
/* private sealed *//* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private sealed *//* Option<int> */struct Option_int {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* List<char*> */struct List_char_ref {/*  */
};
/* Option<int> */struct Option_int List_char_ref_indexOf(char* element, int (*equator)(char*, char*));
/* List<char*> */struct List_char_ref List_char_ref_addAllLast(/* List<char*> */struct List_char_ref others);
/* Iterator<char*> */struct Iterator_char_ref List_char_ref_iterateReversed(/*  */);
char* List_char_ref_last(/*  */);
/* List<char*> */struct List_char_ref List_char_ref_mapLast(char* (*mapper)(char*));
/* private static *//* DivideState */struct DivideState {
	/* private */ /* List<char*> */struct List_char_ref segments;
	/* private */ char* buffer;
	/* private */ int depth;/* private DivideState(List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } *//* public DivideState() {
            this(new ArrayList<String>(), "", 0);
        } *//*  */
};
/* private */ /* DivideState */struct DivideState DivideState_advance(/*  */);/*  {
            this.segments = this.segments.addLast(this.buffer);
            this.buffer = "";
            return this;
        } */
/* private */ /* DivideState */struct DivideState DivideState_append(char c);/*  {
            this.buffer = this.buffer + c;
            return this;
        } */
/* public */ int DivideState_isLevel(/*  */);/*  {
            return this.depth == 0;
        } */
/* public */ /* DivideState */struct DivideState DivideState_enter(/*  */);/*  {
            this.depth++;
            return this;
        } */
/* public */ /* DivideState */struct DivideState DivideState_exit(/*  */);/*  {
            this.depth--;
            return this;
        } */
/* public */ int DivideState_isShallow(/*  */);/*  {
            return this.depth == 1;
        } */
/* private sealed *//* Option<Type> */struct Option_Type {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* Iterator<Type> */struct Iterator_Type {/*  */
};
/* Option<Type> */struct Option_Type Iterator_Type_next(/*  */);
int Iterator_Type_anyMatch(int (*predicate)(/* Type */struct Type));
int Iterator_Type_allMatch(int (*predicate)(/* Type */struct Type));
/* Iterator<Type> */struct Iterator_Type Iterator_Type_filter(int (*predicate)(/* Type */struct Type));
/* private sealed *//* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* List<Type> */struct List_Type {/*  */
};
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type List_Type_removeLast(/*  */);
int List_Type_isEmpty(/*  */);
/* Type */struct Type List_Type_get(int index);
/* Option<int> */struct Option_int List_Type_indexOf(/* Type */struct Type element, int (*equator)(/* Type */struct Type, /* Type */struct Type));
/* List<Type> */struct List_Type List_Type_addAllLast(/* List<Type> */struct List_Type others);
/* Iterator<Type> */struct Iterator_Type List_Type_iterateReversed(/*  */);
/* Type */struct Type List_Type_last(/*  */);
/* List<Type> */struct List_Type List_Type_mapLast(/* Type */struct Type (*mapper)(/* Type */struct Type));
/* private *//* Frame */struct Frame {/* public Frame() {
            this(new ArrayList<>(), new ArrayList<>(), new None<String>());
        } *//*  */
	/* List<char*> */struct List_char_ref typeParameters;
	/* List<Type> */struct List_Type typeArguments;
	/* Option<char*> */struct Option_char_ref maybeStructName;
};
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* public */ /* Option<Type> */struct Option_Type Frame_resolveTypeParam(char* name);/*  {
            return this.typeParameters.indexOf(name, String::equals).flatMap(index -> {
                if (index < this.typeArguments.size()) {
                    return new Some<>(this.typeArguments.get(index));
                }
                return new None<>();
            });
        } */
/* public */ int Frame_isTypeParamDefined(char* value);/*  {
            return this.typeParameters.contains(value, String::equals);
        } */
/* public */ /* Frame */struct Frame Frame_defineTypeParameters(/* List<char*> */struct List_char_ref typeParameters);/*  {
            return new Frame(this.typeParameters.addAllLast(typeParameters), this.typeArguments, this.maybeStructName);
        } */
/* public */ /* Frame */struct Frame Frame_defineStruct(char* name);/*  {
            return new Frame(this.typeParameters, this.typeArguments, new Some<String>(name));
        } */
/* public */ /* Frame */struct Frame Frame_defineTypeArguments(/* List<Type> */struct List_Type typeArguments);/*  {
            return new Frame(this.typeParameters, this.typeArguments.addAllLast(typeArguments), this.maybeStructName);
        } */
/* public */ /* Option<content-startObjectType content-end> */struct Option_/* ObjectType */ Frame_createObjectType(/*  */);/*  {
            return this.maybeStructName.map(name -> {
                return new ObjectType(name, this.typeArguments);
            });
        } */
/* private sealed *//* Option<Frame> */struct Option_Frame {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* Iterator<Frame> */struct Iterator_Frame {/*  */
};
/* Option<Frame> */struct Option_Frame Iterator_Frame_next(/*  */);
int Iterator_Frame_anyMatch(int (*predicate)(/* Frame */struct Frame));
int Iterator_Frame_allMatch(int (*predicate)(/* Frame */struct Frame));
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* private sealed *//* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* List<Frame> */struct List_Frame {/*  */
};
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame List_Frame_removeLast(/*  */);
int List_Frame_isEmpty(/*  */);
/* Frame */struct Frame List_Frame_get(int index);
/* Option<int> */struct Option_int List_Frame_indexOf(/* Frame */struct Frame element, int (*equator)(/* Frame */struct Frame, /* Frame */struct Frame));
/* List<Frame> */struct List_Frame List_Frame_addAllLast(/* List<Frame> */struct List_Frame others);
/* Iterator<Frame> */struct Iterator_Frame List_Frame_iterateReversed(/*  */);
/* Frame */struct Frame List_Frame_last(/*  */);
/* List<Frame> */struct List_Frame List_Frame_mapLast(/* Frame */struct Frame (*mapper)(/* Frame */struct Frame));
/* private *//* Stack */struct Stack {/* public Stack() {
            this(new ArrayList<Frame>().addLast(new Frame()));
        } *//*  */
	/* List<Frame> */struct List_Frame frames;
};
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* public */ /* Stack */struct Stack Stack_defineTypeParameters(/* List<char*> */struct List_char_ref typeParameters);/*  {
            return this.mapLastFrame(last -> last.defineTypeParameters(typeParameters));
        } */
/* private */ /* Stack */struct Stack Stack_mapLastFrame(/* Frame */struct Frame (*mapper)(/* Frame */struct Frame));/*  {
            return new Stack(this.frames.mapLast(mapper));
        } */
/* public */ /* Stack */struct Stack Stack_defineStructPrototype(char* name);/*  {
            return this.mapLastFrame(last -> last.defineStruct(name));
        } */
/* public */ /* Stack */struct Stack Stack_defineTypeArguments(/* List<Type> */struct List_Type typeArguments);/*  {
            return this.mapLastFrame(last -> last.defineTypeArguments(typeArguments));
        } */
/* public */ /* Option<content-startObjectType content-end> */struct Option_/* ObjectType */ Stack_findCurrentObjectType(/*  */);/*  {
            return this.frames
                    .iterateReversed()
                    .map(Frame::createObjectType)
                    .flatMap(Iterators::fromOptional)
                    .next();
        } */
/* public */ /* Option<Type> */struct Option_Type Stack_resolveTypeArgument(char* value);/*  {
            return this.frames
                    .iterateReversed()
                    .map(frame -> frame.resolveTypeParam(value))
                    .flatMap(Iterators::fromOptional)
                    .next();
        } */
/* public */ int Stack_isTypeParamDefined(char* value);/*  {
            return this.frames
                    .iterateReversed()
                    .anyMatch(frame -> frame.isTypeParamDefined(value));
        } */
/* public */ /* Stack */struct Stack Stack_enter(/*  */);/*  {
            return new Stack(this.frames.addLast(new Frame()));
        } */
/* public */ /* Stack */struct Stack Stack_exit(/*  */);/*  {
            return new Stack(this.frames.removeLast().map(listFramePair -> listFramePair.left()).orElse(this.frames));
        } */
/* private *//* ObjectType */struct ObjectType {/*  */
	char* name;
	/* List<Type> */struct List_Type arguments;
};
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* public */ char* ObjectType_stringify(/*  */);/*  {
            var joined = this.arguments.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(result -> "_" + result)
                    .orElse("");

            return this.name + joined;
        } */
/* public */ char* ObjectType_generate(/*  */);/*  {
            return generatePlaceholder(this.generateAsTemplate()) + "struct " + this.stringify();
        } */
/* public */ char* ObjectType_generateAsTemplate(/*  */);/*  {
            var joined = this.arguments.iterate()
                    .map(Type::generateAsTemplate)
                    .collect(new Joiner(", "))
                    .map(result -> "<" + result + ">")
                    .orElse("");

            return this.name + joined;
        } */
/* public */ int ObjectType_equalsTo(/* Type */struct Type other);/*  {
            return other instanceof ObjectType objectType
                    && this.name.equals(objectType.name)
                    && this.arguments.equalsTo(objectType.arguments, Type::equalsTo);
        } */
/* public */ /* Type */struct Type ObjectType_strip(/*  */);/*  {
            var newArguments = this.arguments.iterate()
                    .map(Type::strip)
                    .collect(new ListCollector<>());

            return new ObjectType(this.name, newArguments);
        } */
/* public */ int ObjectType_isParameterized(/*  */);/*  {
            return this.arguments.iterate().anyMatch(Type::isParameterized);
        } */
/* private sealed *//* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref {/*  */
};
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_next(/*  */);
int Iterator_Tuple2_char_ref_List_char_ref_anyMatch(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
int Iterator_Tuple2_char_ref_List_char_ref_allMatch(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* private sealed *//* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref {/*  */
};
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_removeLast(/*  */);
int List_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_get(int index);
/* Option<int> */struct Option_int List_Tuple2_char_ref_List_char_ref_indexOf(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element, int (*equator)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref, /* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addAllLast(/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref others);
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_iterateReversed(/*  */);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_last(/*  */);
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_mapLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*mapper)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* private sealed *//* Option<ObjectType> */struct Option_ObjectType {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* Iterator<ObjectType> */struct Iterator_ObjectType {/*  */
};
/* Option<ObjectType> */struct Option_ObjectType Iterator_ObjectType_next(/*  */);
int Iterator_ObjectType_anyMatch(int (*predicate)(/* ObjectType */struct ObjectType));
int Iterator_ObjectType_allMatch(int (*predicate)(/* ObjectType */struct ObjectType));
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* private sealed *//* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* List<ObjectType> */struct List_ObjectType {/*  */
};
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType List_ObjectType_removeLast(/*  */);
int List_ObjectType_isEmpty(/*  */);
/* ObjectType */struct ObjectType List_ObjectType_get(int index);
/* Option<int> */struct Option_int List_ObjectType_indexOf(/* ObjectType */struct ObjectType element, int (*equator)(/* ObjectType */struct ObjectType, /* ObjectType */struct ObjectType));
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addAllLast(/* List<ObjectType> */struct List_ObjectType others);
/* Iterator<ObjectType> */struct Iterator_ObjectType List_ObjectType_iterateReversed(/*  */);
/* ObjectType */struct ObjectType List_ObjectType_last(/*  */);
/* List<ObjectType> */struct List_ObjectType List_ObjectType_mapLast(/* ObjectType */struct ObjectType (*mapper)(/* ObjectType */struct ObjectType));
/* private sealed *//* Option<CompileState> */struct Option_CompileState {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private sealed *//* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* CompileState */struct CompileState {/* public CompileState() {
            this(new ArrayList<>(), new ListMap<>(String::equals), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Stack());
        } *//*  */
	/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref generations;
	/* Map<char*, content-startOption<content-startCompileState content-end> content-endstruct Option_content-startCompileState content-end (*)(content-startList<Type> content-endstruct List_Type)> */struct Map_char_ref_Func_List_Type_Option_/* CompileState */ expandables;
	/* List<ObjectType> */struct List_ObjectType expansions;
	/* List<ObjectType> */struct List_ObjectType structures;
	/* List<char*> */struct List_char_ref methods;
	/* Stack */struct Stack stack;
};
/* public */ /* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState CompileState_findExpandable(char* name);/*  {
            return this.expandables.find(name);
        } */
/* private */ /* CompileState */struct CompileState CompileState_mapStack(/* Stack */struct Stack (*mapper)(/* Stack */struct Stack));/*  {
            return new CompileState(this.generations, this.expandables, this.expansions, this.structures, this.methods, mapper.apply(this.stack));
        } */
/* public */ int CompileState_isTypeDefined(char* base);/*  {
            return this.isCurrentStructName(base) || this.isStructureDefined(base);
        } */
/* private */ int CompileState_isStructureDefined(char* base);/*  {
            return this.structures.iterate().anyMatch(structure -> structure.name.equals(base));
        } */
/* private */ int CompileState_isCurrentStructName(char* base);/*  {
            return this.stack.findCurrentObjectType()
                    .filter(inner -> inner.name.equals(base))
                    .isPresent();
        } */
/* public */ /* CompileState */struct CompileState CompileState_addMethod(char* method);/*  {
            return new CompileState(this.generations, this.expandables, this.expansions, this.structures, this.methods.addLast(method), this.stack);
        } */
/* public */ /* CompileState */struct CompileState CompileState_addStructure(/* ObjectType */struct ObjectType type);/*  {
            return new CompileState(this.generations, this.expandables, this.expansions, this.structures.addLast(type), this.methods, this.stack);
        } */
/* private *//* Joiner */struct Joiner {/* public Joiner() {
            this("");
        } *//*  */
	char* delimiter;
};
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* public */ /* Option<char*> */struct Option_char_ref Joiner_createInitial(/*  */);/*  {
            return new None<>();
        } */
/* public */ /* Option<char*> */struct Option_char_ref Joiner_fold(/* Option<char*> */struct Option_char_ref maybeCurrent, char* element);/*  {
            return new Some<>(maybeCurrent.map(current -> current + this.delimiter + element).orElse(element));
        } */
/* private static *//* Iterators */struct Iterators {/*  */
};
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* private *//* Ref */struct Ref {/*  */
	/* Type */struct Type type;
};
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* public */ char* Ref_stringify(/*  */);/*  {
            return this.type.stringify() + "_ref";
        } */
/* public */ char* Ref_generate(/*  */);/*  {
            return this.type.generate() + "*";
        } */
/* public */ int Ref_equalsTo(/* Type */struct Type other);/*  {
            return other instanceof Ref ref && this.type.equalsTo(ref.type);
        } */
/* public */ /* Type */struct Type Ref_strip(/*  */);/*  {
            return new Ref(this.type.strip());
        } */
/* public */ int Ref_isParameterized(/*  */);/*  {
            return this.type.isParameterized();
        } */
/* private *//* Placeholder */struct Placeholder {/*  */
	char* value;
};
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* public */ char* Placeholder_stringify(/*  */);/*  {
            return generatePlaceholder(this.value);
        } */
/* public */ char* Placeholder_generate(/*  */);/*  {
            return generatePlaceholder(this.value);
        } */
/* public */ /* Option<content-startDefinition content-end> */struct Option_/* Definition */ Placeholder_toDefinition(/*  */);/*  {
            return new None<>();
        } */
/* public */ int Placeholder_equalsTo(/* Type */struct Type other);/*  {
            return other instanceof Placeholder placeholder && this.value.equals(placeholder.value);
        } */
/* public */ /* Type */struct Type Placeholder_strip(/*  */);/*  {
            return this;
        } */
/* public */ int Placeholder_isParameterized(/*  */);/*  {
            return true;
        } */
/* private sealed *//* Option<Definition> */struct Option_Definition {/*  */
};
/* Symbols: [] */
/* Expanding: [] */
/* Expansions: [] */
/* Symbols: [] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head] */
/* Expansions: [] */
char* Type_stringify(/*  */);
char* Type_generate(/*  */);
/* default */ char* Type_generateAsTemplate(/*  */);/*  {
            return this.generate();
        } */
int Type_equalsTo(/* Type */struct Type other);
/* Type */struct Type Type_strip(/*  */);
int Type_isParameterized(/*  */);
/* default */ char* Type_generateWithName(char* name);/*  {
            return this.generate() + " " + name;
        } */
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>] */
/* List<char*> */struct List_char_ref List_char_ref_addLast(char* element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>] */
/* Iterator<char*> */struct Iterator_char_ref Iterator_char_ref_concat(/* Iterator<char*> */struct Iterator_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>] */
/* Option<char*> */struct Option_char_ref Option_char_ref_or(/* Option<char*> */struct Option_char_ref (*other)());
int Option_char_ref_isPresent(/*  */);
/* Option<char*> */struct Option_char_ref Option_char_ref_filter(int (*predicate)(char*));
char* Option_char_ref_orElse(char* other);
char* Option_char_ref_orElseGet(char* (*other)());
int Option_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>] */
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_or(/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<char*>, char*>> */struct Option_Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_filter(int (*predicate)(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref));
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElse(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref other);
/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref Option_Tuple2_List_char_ref_char_ref_orElseGet(/* Tuple2<List<char*>, char*> */struct Tuple2_List_char_ref_char_ref (*other)());
int Option_Tuple2_List_char_ref_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>] */
/* Option<int> */struct Option_int Option_int_or(/* Option<int> */struct Option_int (*other)());
int Option_int_isPresent(/*  */);
/* Option<int> */struct Option_int Option_int_filter(int (*predicate)(int));
int Option_int_orElse(int other);
int Option_int_orElseGet(int (*other)());
int Option_int_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>] */
/* List<Type> */struct List_Type List_Type_addLast(/* Type */struct Type element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>] */
/* Iterator<Type> */struct Iterator_Type Iterator_Type_concat(/* Iterator<Type> */struct Iterator_Type other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>] */
/* Option<Type> */struct Option_Type Option_Type_or(/* Option<Type> */struct Option_Type (*other)());
int Option_Type_isPresent(/*  */);
/* Option<Type> */struct Option_Type Option_Type_filter(int (*predicate)(/* Type */struct Type));
/* Type */struct Type Option_Type_orElse(/* Type */struct Type other);
/* Type */struct Type Option_Type_orElseGet(/* Type */struct Type (*other)());
int Option_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>] */
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_or(/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isPresent(/*  */);
/* Option<Tuple2<List<Type>, Type>> */struct Option_Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_filter(int (*predicate)(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type));
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElse(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type other);
/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type Option_Tuple2_List_Type_Type_orElseGet(/* Tuple2<List<Type>, Type> */struct Tuple2_List_Type_Type (*other)());
int Option_Tuple2_List_Type_Type_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>] */
/* List<Frame> */struct List_Frame List_Frame_addLast(/* Frame */struct Frame element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>] */
/* Iterator<Frame> */struct Iterator_Frame Iterator_Frame_concat(/* Iterator<Frame> */struct Iterator_Frame other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>] */
/* Option<Frame> */struct Option_Frame Option_Frame_or(/* Option<Frame> */struct Option_Frame (*other)());
int Option_Frame_isPresent(/*  */);
/* Option<Frame> */struct Option_Frame Option_Frame_filter(int (*predicate)(/* Frame */struct Frame));
/* Frame */struct Frame Option_Frame_orElse(/* Frame */struct Frame other);
/* Frame */struct Frame Option_Frame_orElseGet(/* Frame */struct Frame (*other)());
int Option_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>] */
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_or(/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isPresent(/*  */);
/* Option<Tuple2<List<Frame>, Frame>> */struct Option_Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_filter(int (*predicate)(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame));
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElse(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame other);
/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame Option_Tuple2_List_Frame_Frame_orElseGet(/* Tuple2<List<Frame>, Frame> */struct Tuple2_List_Frame_Frame (*other)());
int Option_Tuple2_List_Frame_Frame_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>] */
/* List<Tuple2<char*, List<char*>>> */struct List_Tuple2_char_ref_List_char_ref List_Tuple2_char_ref_List_char_ref_addLast(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref Iterator_Tuple2_char_ref_List_char_ref_concat(/* Iterator<Tuple2<char*, List<char*>>> */struct Iterator_Tuple2_char_ref_List_char_ref other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>] */
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<char*, List<char*>>> */struct Option_Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref));
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref other);
/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref Option_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<char*, List<char*>> */struct Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>] */
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_or(/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isPresent(/*  */);
/* Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>> */struct Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_filter(int (*predicate)(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref));
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElse(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref other);
/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_orElseGet(/* Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>> */struct Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref (*other)());
int Option_Tuple2_List_Tuple2_char_ref_List_char_ref_Tuple2_char_ref_List_char_ref_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>] */
/* List<ObjectType> */struct List_ObjectType List_ObjectType_addLast(/* ObjectType */struct ObjectType element);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>] */
/* Iterator<ObjectType> */struct Iterator_ObjectType Iterator_ObjectType_concat(/* Iterator<ObjectType> */struct Iterator_ObjectType other);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>] */
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_or(/* Option<ObjectType> */struct Option_ObjectType (*other)());
int Option_ObjectType_isPresent(/*  */);
/* Option<ObjectType> */struct Option_ObjectType Option_ObjectType_filter(int (*predicate)(/* ObjectType */struct ObjectType));
/* ObjectType */struct ObjectType Option_ObjectType_orElse(/* ObjectType */struct ObjectType other);
/* ObjectType */struct ObjectType Option_ObjectType_orElseGet(/* ObjectType */struct ObjectType (*other)());
int Option_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>] */
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_or(/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isPresent(/*  */);
/* Option<Tuple2<List<ObjectType>, ObjectType>> */struct Option_Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_filter(int (*predicate)(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType));
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElse(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType other);
/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType Option_Tuple2_List_ObjectType_ObjectType_orElseGet(/* Tuple2<List<ObjectType>, ObjectType> */struct Tuple2_List_ObjectType_ObjectType (*other)());
int Option_Tuple2_List_ObjectType_ObjectType_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>] */
/* Option<CompileState> */struct Option_CompileState Option_CompileState_or(/* Option<CompileState> */struct Option_CompileState (*other)());
int Option_CompileState_isPresent(/*  */);
/* Option<CompileState> */struct Option_CompileState Option_CompileState_filter(int (*predicate)(/* CompileState */struct CompileState));
/* CompileState */struct CompileState Option_CompileState_orElse(/* CompileState */struct CompileState other);
/* CompileState */struct CompileState Option_CompileState_orElseGet(/* CompileState */struct CompileState (*other)());
int Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>] */
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_or(/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState (*other)());
int Option_Func_List_Type_Option_CompileState_isPresent(/*  */);
/* Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)> */struct Option_Func_List_Type_Option_CompileState Option_Func_List_Type_Option_CompileState_filter(int (*predicate)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type)));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElse)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*other)(/* List<Type> */struct List_Type));
/* Option<CompileState> */struct Option_CompileState (*Option_Func_List_Type_Option_CompileState_orElseGet)(/* List<Type> */struct List_Type)(/* Option<CompileState> */struct Option_CompileState (*)(/* List<Type> */struct List_Type) (*other)());
int Option_Func_List_Type_Option_CompileState_isEmpty(/*  */);
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* Option<Definition> */struct Option_Definition Option_Definition_or(/* Option<Definition> */struct Option_Definition (*other)());
int Option_Definition_isPresent(/*  */);
/* Option<Definition> */struct Option_Definition Option_Definition_filter(int (*predicate)(/* Definition */struct Definition));
/* Definition */struct Definition Option_Definition_orElse(/* Definition */struct Definition other);
/* Definition */struct Definition Option_Definition_orElseGet(/* Definition */struct Definition (*other)());
int Option_Definition_isEmpty(/*  */);
/* private *//* Definition */struct Definition {/*  */
	/* List<char*> */struct List_char_ref annotations;
	char* afterAnnotations;
	/* Type */struct Type type;
	char* name;
	/* List<char*> */struct List_char_ref typeParams;
};
/* public */ /* Option<Definition> */struct Option_Definition Definition_toDefinition(/*  */);/*  {
            return new Some<>(this);
        } */
/* private */ char* Definition_generateAfterAnnotations(/*  */);/*  {
            return this.afterAnnotations.isEmpty() ? "" : (generatePlaceholder(this.afterAnnotations()) + " ");
        } */
/* public */ /* Definition */struct Definition Definition_mapType(/* Type */struct Type (*mapper)(/* Type */struct Type));/*  {
            return new Definition(this.annotations, this.afterAnnotations, mapper.apply(this.type), this.name, this.typeParams);
        } */
/* public */ /* Definition */struct Definition Definition_mapName(char* (*mapper)(char*));/*  {
            return new Definition(this.annotations, this.afterAnnotations, this.type, mapper.apply(this.name), this.typeParams);
        } */
/* private *//* TypeParam */struct TypeParam {/*  */
	char* input;
};
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder, Option<Definition>, Definition] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector, ArrayList] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* public */ char* TypeParam_stringify(/*  */);/*  {
            return this.input;
        } */
/* public */ char* TypeParam_generate(/*  */);/*  {
            return "template " + this.input;
        } */
/* public */ int TypeParam_equalsTo(/* Type */struct Type other);/*  {
            return other instanceof TypeParam param && this.input.equals(param.input);
        } */
/* public */ /* Type */struct Type TypeParam_strip(/*  */);/*  {
            return Primitive.Void;
        } */
/* public */ int TypeParam_isParameterized(/*  */);/*  {
            return true;
        } */
/* private *//* Functional */struct Functional {/*  */
	/* Type */struct Type returnType;
	/* List<Type> */struct List_Type typeParameters;
};
/* Symbols: [Type, Actual, Parameter, StandardLibrary, Lists, Option<char*>, Iterator<char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<char*>, DivideState, Option<Type>, Iterator<Type>, Option<Tuple2<List<Type>, Type>>, List<Type>, Frame, Option<Frame>, Iterator<Frame>, Option<Tuple2<List<Frame>, Frame>>, List<Frame>, Stack, ObjectType, Option<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<Tuple2<char*, List<char*>>>, Option<ObjectType>, Iterator<ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, List<ObjectType>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, CompileState, Joiner, Iterators, Ref, Placeholder, Option<Definition>, Definition, TypeParam] */
/* Expanding: [Tuple2, Map, Option, Iterator, Collector, List, Head, ListMap, Some, None, EmptyHead, HeadedIterator, RangeHead implements Head, Tuple2Impl, SingleHead, ListCollector, ArrayList, FlatMapHead] */
/* Expansions: [List<char*>, Iterator<char*>, Option<char*>, Tuple2<List<char*>, char*>, Option<Tuple2<List<char*>, char*>>, Option<int>, List<Type>, Iterator<Type>, Option<Type>, Tuple2<List<Type>, Type>, Option<Tuple2<List<Type>, Type>>, List<Frame>, Iterator<Frame>, Option<Frame>, Tuple2<List<Frame>, Frame>, Option<Tuple2<List<Frame>, Frame>>, Tuple2<char*, List<char*>>, List<Tuple2<char*, List<char*>>>, Iterator<Tuple2<char*, List<char*>>>, Option<Tuple2<char*, List<char*>>>, Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>, Option<Tuple2<List<Tuple2<char*, List<char*>>>, Tuple2<char*, List<char*>>>>, List<ObjectType>, Iterator<ObjectType>, Option<ObjectType>, Tuple2<List<ObjectType>, ObjectType>, Option<Tuple2<List<ObjectType>, ObjectType>>, Option<CompileState>, Option<content-startOption<CompileState> content-endstruct Option_CompileState (*)(content-startList<Type> content-endstruct List_Type)>, Option<Definition>] */
/* public */ char* Functional_stringify(/*  */);/*  {
            var joinedParameters = this.typeParameters.iterate()
                    .map(Type::stringify)
                    .collect(new Joiner("_"))
                    .map(inner -> inner + "_")
                    .orElse("");

            return "Func_" + joinedParameters + this.returnType.stringify();
        } */
/* public */ char* Functional_generate(/*  */);/*  {
            return this.generateWithName("");
        } */
/* public */ int Functional_equalsTo(/* Type */struct Type other);/*  {
            return other instanceof Functional functional
                    && this.returnType.equalsTo(functional.returnType)
                    && this.typeParameters.equalsTo(functional.typeParameters, Type::equalsTo);
        } */
/* public */ /* Type */struct Type Functional_strip(/*  */);/*  {
            return new Functional(this.returnType.strip(), this.typeParameters.iterate()
                    .map(Type::strip)
                    .collect(new ListCollector<>()));
        } */
/* public */ int Functional_isParameterized(/*  */);/*  {
            return this.returnType.isParameterized() || this.typeParameters.iterate().anyMatch(Type::isParameterized);
        } */
/* public */ char* Functional_generateWithName(char* name);/*  {
            var joined = this.typeParameters.iterate()
                    .map(Type::generate)
                    .collect(new Joiner(", "))
                    .orElse("");

            return this.returnType.generate() + " (*" +
                    name +
                    ")(" + joined + ")";
        } */
/* public *//* Main */struct Main {
	// Tuple2<A, B>
	// Map<K, V>
	// Option<T>
	// Iterator<T>
	// Collector<T, C>
	// List<T>
	// Head<T>
	// ListMap<K, V>
	// Some<T>
	// None<T>
	// EmptyHead<T> implements Head<T>
	// HeadedIterator<T>
	// RangeHead implements Head<Integer>
	// Tuple2Impl<A, B>
	// SingleHead<T> implements Head<T>
	// ListCollector<T> implements Collector<T, List<T>>
	// ArrayList<T> implements List<T>
	// FlatMapHead<T, R> implements Head<R>/* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* return appended; *//*  */
};
/* public static */ void Main_main(/*  */);/*  {
        try {
            var root = Paths.get(".", "src", "java", "magma");
            var source = root.resolve("main.java");
            var target = root.resolve("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang.exe", target.toAbsolutePath().toString(), "-o", "main.exe")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    } */
/* private static */ char* Main_compile(char* input);/*  {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var compiledState = compiled.left();

        var joined = compiledState.generations
                .iterate()
                .map(tuple -> tuple.left() + joinWithDelimiter(tuple.right(), ""))
                .collect(new Joiner())
                .orElse("");

        return joined + compiled.right() + "\nint main(){\n\treturn 0;\n}\n";
    } */
/* private static */ char* Main_joinWithDelimiter(/* List<char*> */struct List_char_ref items, char* delimiter);/*  {
        return items.iterate()
                .collect(new Joiner(delimiter))
                .orElse("");
    } */
/* private static */ /* Tuple2<CompileState, char*> */struct Tuple2_CompileState_char_ref Main_compileStatements(/* CompileState */struct CompileState initial, char* input, /* Tuple2<CompileState, char*> */struct Tuple2_CompileState_char_ref (*mapper)(/* CompileState */struct CompileState, char*));/*  {
        return compileAll(initial, input, Main::foldStatementChar, mapper, Main::merge);
    } */
/* private static */ /* Tuple2<CompileState, char*> */struct Tuple2_CompileState_char_ref Main_compileAll(/* CompileState */struct CompileState initial, char* input, /* DivideState */struct DivideState (*folder)(/* DivideState */struct DivideState, char), /* Tuple2<CompileState, char*> */struct Tuple2_CompileState_char_ref (*mapper)(/* CompileState */struct CompileState, char*), char* (*merger)(char*, char*));/*  {
        var tuple = parseAll(initial, input, folder, mapper);
        return new Tuple2Impl<>(tuple.left(), generateAll(tuple.right(), merger));
    } */
/* private static */ char* Main_generateAll(/* List<char*> */struct List_char_ref elements, char* (*merger)(char*, char*));/*  {
        return elements.iterate().fold("", merger);
    } */
/* private static */ char* Main_merge(char* buffer, char* element);/*  {
        return buffer + element;
    } */
/* private static */ /* List<char*> */struct List_char_ref Main_divide(char* input, /* DivideState */struct DivideState (*folder)(/* DivideState */struct DivideState, char));/*  {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } */
/* private static */ /* DivideState */struct DivideState Main_foldStatementChar(/* DivideState */struct DivideState state, char c);/*  {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} */

	/* private static Tuple2<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple2Impl<>(state, "");
        }

        return createStructureRule("class ").apply(state, input)
                .orElseGet(() -> new Tuple2Impl<>(state, "\n\t" + generatePlaceholder(stripped.strip())));
    } */
	/* private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureRule(String infix) {
        return (state, input) -> compileFirst(input, infix, (beforeKeyword, afterKeyword) -> {
            return compileFirst(afterKeyword, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", content1 -> {
                    return compileOr(state, beforeContent, Lists.of(
                            createStructWithParametersRule(beforeKeyword, content1),
                            createStructWithoutParametersRule(beforeKeyword, content1)
                    ));
                });
            });
        });
    } */
	/* private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructWithoutParametersRule(String beforeKeyword, String content1) {
        return (state1, s) -> {
            return createStructureWithMaybeTypeParametersRule(state1, beforeKeyword, s, content1, new ArrayList<>());
        };
    } */
	/* private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructWithParametersRule(String beforeKeyword, String content1) {
        return (state, input) -> {
            return compileInfix(input, ")", Main::findLast, (withParameters, afterParameters) -> {
                return compileFirst(withParameters, "(", (beforeParameters, parameters) -> {
                    var parametersTuple = parseParameters(state, parameters);
                    return createStructureWithMaybeTypeParametersRule(parametersTuple.left(), beforeKeyword, beforeParameters, content1, parametersTuple.right());
                });
            });
        };
    } */
	/* private static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, String parameters) {
        return parseValues(state, parameters, Main::compileParameter);
    } */
	/* private static Tuple2<CompileState, Parameter> compileParameter(CompileState state, String input) {
        return parseDefinition(state, input)
                .map(value -> new Tuple2Impl<CompileState, Parameter>(value.left(), value.right()))
                .orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(input)));
    } */
	/* private static Option<Tuple2<CompileState, String>> createStructureWithMaybeTypeParametersRule(
            CompileState state,
            String beforeKeyword,
            String beforeContent,
            String content1,
            List<Parameter> parameters
    ) {
        return compileOr(state, beforeContent, Lists.of(
                createStructureWithTypeParamsRule(beforeKeyword, content1, parameters),
                createStructureWithoutTypeParamsRule(beforeKeyword, content1, parameters)
        ));
    } */
	/* private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureWithTypeParamsRule(String beforeKeyword, String content, List<Parameter> parameters) {
        return (state, input) -> {
            return compileSuffix(input.strip(), ">", withoutEnd -> {
                return compileFirst(withoutEnd, "<", (name, typeParameters) -> {
                    var typeParams = divide(typeParameters, Main::foldValueChar)
                            .iterate()
                            .map(String::strip)
                            .collect(new ListCollector<>());

                    return new Some<>(new Tuple2Impl<CompileState, String>(state.addExpandable(name, (typeArguments) -> {
                        return assembleStructure(state, beforeKeyword, name, typeParams, typeArguments, parameters, content);
                    }), "\n\t// " + name + "<" + joinWithDelimiter(typeParams, ", ") + ">"));
                });
            });
        };
    } */
	/* private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }
        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    } */
	/* private static BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>> createStructureWithoutTypeParamsRule(
            String beforeKeyword,
            String content,
            List<Parameter> parameters
    ) {
        return (state, name) -> {
            return assembleStructure(state, beforeKeyword, name, new ArrayList<String>(), new ArrayList<Type>(), parameters, content).map(newState -> {
                return new Tuple2Impl<>(newState, "");
            });
        };
    } */
	/* private static Option<CompileState> assembleStructure(
            CompileState state,
            String beforeStruct,
            String rawName,
            List<String> typeParams,
            List<Type> typeArguments,
            List<Parameter> parameters,
            String content
    ) {
        var name = rawName.strip();
        if (!isSymbol(name)) {
            return new None<>();
        }

        var joinedExpansions = state.expansions
                .iterate()
                .map(ObjectType::generateAsTemplate)
                .collect(new Joiner(", "))
                .orElse("");

        var joinedExpandables = state.expandables
                .iterateKeys()
                .collect(new Joiner(", "))
                .orElse("");

        var joinedSymbols = state.structures
                .iterate()
                .map(ObjectType::generateAsTemplate)
                .collect(new Joiner(", "))
                .orElse("");

        var defined = state
                .addMethod(debug("Symbols", joinedSymbols))
                .addMethod(debug("Expanding", joinedExpandables))
                .addMethod(debug("Expansions", joinedExpansions))
                .mapStack(stack -> stack
                        .enter()
                        .defineStructPrototype(name)
                        .defineTypeParameters(typeParams)
                        .defineTypeArguments(typeArguments)
                );

        var statementsTuple = parseStatements(content, defined);
        var type = new ObjectType(name, typeArguments);

        var definitions = parameters.iterate()
                .map(Parameter::toDefinition)
                .flatMap(Iterators::fromOptional)
                .map(Main::generateDefinitionStatement)
                .collect(new ListCollector<>());

        var statements = statementsTuple.right()
                .addAllLast(definitions);

        var generated = generatePlaceholder(beforeStruct.strip()) + type.generate() + " {" + generateAll(statements, Main::merge) + "\n};\n";
        var added = statementsTuple.left().mapStack(Stack::exit)
                .addStruct(generated)
                .addStructure(type);

        return new Some<>(added);
    } */
	/* private static String debug(String name, String joined) {
        return generatePlaceholder(name + ": [" + joined + "]") + "\n";
    } */
	/* private static Tuple2<CompileState, List<String>> parseStatements(String content, CompileState defined) {
        return parseAll(defined, content, Main::foldStatementChar, Main::compileClassSegment);
    } */
	/* private static <T> Option<T> compileSymbol(String input, Function<String, Option<T>> mapper) {
        if (!isSymbol(input)) {
            return new None<>();
        }

        return mapper.apply(input);
    } */
	/* private static boolean isSymbol(String input) {
        if (input.equals("return") || input.equals("private")) {
            return false;
        }

        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(i))) {
                continue;
            }
            return false;
        }
        return true;
    } */
	/* private static Tuple2<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, Lists.of(
                createStructureRule("class "),
                createStructureRule("interface "),
                createStructureRule("record "),
                Main::compileDefinitionStatement,
                Main::compileMethod
        ));
    } */
	/* private static Option<Tuple2<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (definitionString, withParams) -> {
            return compileFirst(withParams, ")", (params, oldContent) -> {
                return parseDefinition(state, definitionString).flatMap(definitionPair -> {
                    var definitionState = definitionPair.left();
                    var definition = definitionPair.right();

                    var parametersTuple = parseParameters(definitionState, params);
                    return assembleMethod(parametersTuple.left(), definition, parametersTuple.right(), oldContent);
                });
            });
        });
    } */
	/* private static Option<Tuple2<CompileState, String>> assembleMethod(
            CompileState state,
            Definition definition,
            List<Parameter> parameters,
            String oldContent
    ) {
        if (!definition.typeParams.isEmpty()) {
            return new Some<>(new Tuple2Impl<CompileState, String>(state, ""));
        }

        Definition newDefinition;
        String newContent;
        if (definition.annotations.contains("Actual", String::equals)) {
            newDefinition = definition.mapType(Type::strip);
            newContent = ";";
        }
        else if (oldContent.equals(";")) {
            newDefinition = definition.mapType(Type::strip).mapName(name -> {
                return joinCurrentName(state, name);
            });

            newContent = ";";
        }
        else {
            newContent = ";" + generatePlaceholder(oldContent);
            newDefinition = definition.mapName(name -> {
                return joinCurrentName(state, name);
            });
        }

        var parametersString = parameters.iterate()
                .map(Parameter::generate)
                .collect(new Joiner(", "))
                .orElse("");

        var generatedHeader = newDefinition.generate() + "(" + parametersString + ")";
        var generated = generatedHeader + newContent + "\n";
        return new Some<>(new Tuple2Impl<CompileState, String>(state.addMethod(generated), ""));
    } */
	/* private static String joinCurrentName(CompileState state, String name) {
        return state.stack
                .findCurrentObjectType()
                .map(currentStructType -> currentStructType.stringify() + "_" + name)
                .orElse(name);
    } */
	/* private static Tuple2<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple2<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple2Impl<>(state, generatePlaceholder(input.strip())));
    } */
	/* private static <T> Option<Tuple2<CompileState, T>> compileOr(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>>> rules
    ) {
        return rules.iterate()
                .map(rule -> rule.apply(state, input))
                .flatMap(Iterators::fromOptional)
                .next();
    } */
	/* private static Option<Tuple2<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd)
                    .map(pair -> new Tuple2Impl<>(pair.left(), generateDefinitionStatement(pair.right())));
        });
    } */
	/* private static String generateDefinitionStatement(Definition definition) {
        return "\n\t" + definition.generate() + ";";
    } */
	/* private static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return compileInfix(input.strip(), " ", Main::findLast, (beforeName, rawName) -> {
            return compileOr(state, beforeName, Lists.of(
                    (state1, s) -> parseDefinitionWithTypeSeparator(beforeName, rawName, state1),
                    (state2, beforeName0) -> parseDefinitionWithoutTypeSeparator(state2, rawName, "", beforeName0)
            ));
        });
    } */
	/* private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeSeparator(String beforeName, String rawName, CompileState state1) {
        return compileInfix(
                () -> splitByTypeSeparator(beforeName),
                (beforeType, type) -> parseDefinitionWithoutTypeSeparator(state1, rawName, beforeType, type));
    } */
	/* private static Option<Tuple2<String, String>> splitByTypeSeparator(String beforeName) {
        var divisions = divide(beforeName, Main::foldTypeSeparator);

        if (divisions.size() >= 2) {
            var maybeRemoved = divisions.removeLast().<Tuple2<String, String>>map(removed -> {
                var joined = joinWithDelimiter(removed.left(), " ");
                return new Tuple2Impl<>(joined, removed.right());
            });

            if (maybeRemoved instanceof Some) {
                return maybeRemoved;
            }
        }

        return new None<>();
    } */
	/* private static Option<Tuple2<CompileState, Definition>> parseDefinitionWithoutTypeSeparator(
            CompileState state,
            String rawName,
            String beforeType,
            String type
    ) {
        var strippedBeforeType = beforeType.strip();
        return compileInfix(strippedBeforeType, "\n", Main::findLast, (annotationsString, afterAnnotations) -> {
            var annotations = divide(annotationsString, foldWithDelimiter('\n'))
                    .iterate()
                    .map(slice -> slice.substring(1))
                    .map(String::strip)
                    .collect(new ListCollector<>());

            return assembleDefinition(state, annotations, afterAnnotations.strip(), rawName, type);
        }).or(() -> {
            return assembleDefinition(state, new ArrayList<>(), strippedBeforeType, rawName, type);
        });
    } */
	/* private static DivideState foldTypeSeparator(DivideState state, Character c) {
        if (c == ' ' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    } */
	/* private static Option<Tuple2<CompileState, Definition>> assembleDefinition(CompileState state, List<String> annotations, String afterAnnotations, String rawName, String type) {
        return compileSuffix(afterAnnotations.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (beforeTypeParams, typeParams) -> {
                var typeParamsTuple = Main.parseValues(state, typeParams, (state1, s1) -> new Tuple2Impl<>(state1, s1.strip()));
                return getCompileStateDefinitionTuple(typeParamsTuple.left(), annotations, beforeTypeParams.strip(), typeParamsTuple.right(), rawName, type);
            });
        }).or(() -> {
            return getCompileStateDefinitionTuple(state, annotations, afterAnnotations, new ArrayList<>(), rawName, type);
        });
    } */
	/* private static Option<Tuple2<CompileState, Definition>> getCompileStateDefinitionTuple(
            CompileState state,
            List<String> annotations,
            String afterAnnotations,
            List<String> typeParams,
            String rawName,
            String type
    ) {
        return compileSymbol(rawName.strip(), name -> {
            CompileState state1 = state.mapStack(stack -> stack.defineTypeParameters(typeParams));
            return parseType(state1, type).flatMap(typePair -> {
                return new Some<>(new Tuple2Impl<CompileState, Definition>(typePair.left(), new Definition(annotations, afterAnnotations, typePair.right(), name, typeParams)));
            });
        });
    } */
	/* private static BiFunction<DivideState, Character, DivideState> foldWithDelimiter(char delimiter) {
        return (state, c) -> {
            if (c == delimiter) {
                return state.advance();
            }
            return state.append(c);
        };
    } */
	/* private static Tuple2<CompileState, Type> parseTypeOrPlaceholder(CompileState state, String input) {
        return parseType(state, input).orElseGet(() -> new Tuple2Impl<>(state, new Placeholder(input.strip())));
    } */
	/* private static Option<Tuple2<CompileState, Type>> parseType(CompileState state, String input) {
        return compileOr(state, input, Lists.of(
                typed(Main::parsePrimitive),
                typed(Main::parseTemplate),
                typed(Main::parseArray),
                typed(Main::parseTypeParam),
                typed(Main::parseStructureType)
        ));
    } */
	/* private static Option<Tuple2<CompileState, Type>> parseStructureType(CompileState state, String input) {
        return compileSymbol(input.strip(), symbol -> {
            if (state.isTypeDefined(symbol)) {
                return new Some<>(new Tuple2Impl<CompileState, Type>(state, new ObjectType(symbol, new ArrayList<>())));
            }
            else {
                return new None<>();
            }
        });
    } */
	/* private static Option<Tuple2<CompileState, Type>> parseTypeParam(CompileState state, String input) {
        var stripped = input.strip();

        if (state.stack.isTypeParamDefined(stripped)) {
            return new Some<>(state.stack.resolveTypeArgument(stripped).map(tuple -> {
                return new Tuple2Impl<>(state, tuple);
            }).orElseGet(() -> new Tuple2Impl<>(state, new TypeParam(stripped))));
        }

        return new None<>();
    } */
	/* private static Option<Tuple2<CompileState, Type>> parseArray(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith("[]")) {
            var slice = input.substring(0, stripped.length() - "[]".length());
            var childTuple = parseTypeOrPlaceholder(state, slice);
            return new Some<>(new Tuple2Impl<CompileState, Type>(childTuple.left(), new Ref(childTuple.right())));
        }

        return new None<>();
    } */
	/* private static <T extends R, R> BiFunction<CompileState, String, Option<Tuple2<CompileState, R>>> typed(BiFunction<CompileState, String, Option<Tuple2<CompileState, T>>> rule) {
        return (state, input) -> rule.apply(state, input).map(pair -> new Tuple2Impl<>(pair.left(), pair.right()));
    } */
	/* private static Option<Tuple2<CompileState, Type>> parsePrimitive(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("String")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, new Ref(Primitive.Char)));
        }

        if (stripped.equals("int")
                || stripped.equals("Integer")
                || stripped.equals("boolean")
                || stripped.equals("Boolean")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, Primitive.Int));
        }

        if (stripped.equals("char") || stripped.equals("Character")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, Primitive.Char));
        }

        if (stripped.equals("void")) {
            return new Some<>(new Tuple2Impl<CompileState, Type>(state, Primitive.Void));
        }

        return new None<>();
    } */
	/* private static Option<Tuple2<CompileState, Type>> parseTemplate(CompileState oldState, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return Main.compileFirst(withoutEnd, "<", (base, argumentsString) -> {
                var argumentsTuple = parseValues(oldState, argumentsString, Main::parseTypeOrPlaceholder);

                var argumentsState = argumentsTuple.left();
                var arguments = argumentsTuple.right();

                if (base.equals("Supplier")) {
                    var functional = new Functional(arguments.get(0), new ArrayList<>());
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("Predicate")) {
                    var functional = new Functional(Primitive.Int, Lists.of(arguments.get(0)));
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("Function")) {
                    var functional = new Functional(arguments.get(1), Lists.of(arguments.get(0)));
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                if (base.equals("BiFunction")) {
                    var functional = new Functional(arguments.get(2), Lists.of(arguments.get(0), arguments.get(1)));
                    return new Some<>(new Tuple2Impl<CompileState, Type>(argumentsState, functional));
                }

                var expansion = new ObjectType(base, arguments);
                CompileState withExpansion;
                if (expansion.isParameterized()) {
                    withExpansion = argumentsState;
                }
                else {
                    withExpansion = argumentsState.expand(expansion).orElse(argumentsState);
                }

                return new Some<>(new Tuple2Impl<CompileState, Type>(withExpansion, expansion));
            });
        });
    } */
	/* private static <T> Tuple2<CompileState, List<T>> parseValues(CompileState oldState, String argumentsString, BiFunction<CompileState, String, Tuple2<CompileState, T>> mapper) {
        return parseAll(oldState, argumentsString, Main::foldValueChar, mapper);
    } */
	/* private static Option<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    } */
	/* private static <T> Option<T> compileSuffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (input.endsWith(suffix)) {
            var content = input.substring(0, input.length() - suffix.length());
            return mapper.apply(content);
        }

        return new None<>();
    } */
	/* private static <T> Option<T> compileFirst(String stripped, String infix, BiFunction<String, String, Option<T>> mapper) {
        return compileInfix(stripped, infix, Main::findFirst, mapper);
    } */
	/* private static <T> Option<T> compileInfix(String input, String infix, BiFunction<String, String, Option<Integer>> locate, BiFunction<String, String, Option<T>> mapper) {
        return compileInfix(() -> split(input, infix, locate), mapper);
    } */
	/* private static <T> Option<T> compileInfix(Supplier<Option<Tuple2<String, String>>> supplier, BiFunction<String, String, Option<T>> mapper) {
        return supplier.get().flatMap(pair -> {
            return mapper.apply(pair.left(), pair.right());
        });
    } */
	/* private static Option<Tuple2<String, String>> split(String input, String infix, BiFunction<String, String, Option<Integer>> locate) {
        return locate.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple2Impl<>(left, right);
        });
    } */
	/* private static Option<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    } */
	/* private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end");

        return "content-start" + replaced + " content-end";
    } */
	/* private enum Primitive implements Type {
        Char("char"),
        Int("int"),
        Void("void");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String stringify() {
            return this.value;
        }

        @Override
        public String generate() {
            return this.value;
        }

        @Override
        public boolean equalsTo(Type other) {
            return this == other;
        }

        @Override
        public Type strip() {
            return this;
        }

        @Override
        public boolean isParameterized() {
            return false;
        }
    } */
	/* } */
int main(){
	return 0;
}
