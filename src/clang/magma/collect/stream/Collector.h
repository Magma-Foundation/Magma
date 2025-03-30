#ifndef magma_collect_stream_Collector
#define magma_collect_stream_Collector
struct Collector<T, C>{
};
struct C createInitial();
struct C fold(struct C c, struct T t);
#endif

