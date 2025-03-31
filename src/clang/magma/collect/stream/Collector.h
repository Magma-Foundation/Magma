#ifndef magma_collect_stream_Collector
#define magma_collect_stream_Collector
struct Collector<T, C>{
};
C createInitial();
C fold(C c, T t);
#endif

