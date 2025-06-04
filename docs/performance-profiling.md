# Performance Profiling

This document summarises practical ways to profile the Magmac compiler. The suggestions below focus on measuring execution time and memory usage for both the Java and TypeScript builds.

## 1. Java Flight Recorder

Java Flight Recorder (JFR) is bundled with modern JDKs and can capture CPU, memory and thread data.

- Compile the project as normal using `javac`.
- Run the compiler with a recording enabled:
  ```bash
  java -XX:StartFlightRecording=filename=magmac.jfr,duration=60s -cp out magmac.Main
  ```
- Open the resulting `magmac.jfr` file in Java Mission Control (`jmc`) to explore hotspots and allocation trends.

For longer runs omit `duration` to record until the process exits. Recording settings can also be adjusted at runtime using `jcmd`.

## 2. Microbenchmarks with JMH

The [Java Microbenchmark Harness](https://openjdk.org/projects/code-tools/jmh/) helps measure small pieces of code in isolation. Add a benchmark module that depends on JMH and implement `@Benchmark` methods for critical functions. Running `mvn test` or `gradle jmh` (depending on the build tool) will produce scores in operations per second.

JMH avoids common benchmarking pitfalls such as JIT warm‑up and dead code elimination.

## 3. Profiling the TypeScript Build

When running the TypeScript build of the compiler through Node.js you can collect V8 profiling data.

- Execute `ts-node` with the `--cpu-prof` flag:
  ```bash
  ts-node --cpu-prof ./src/web/magmac/Main.ts
  ```
  This writes a `isolate-0x*.cpuprofile` file that can be loaded in Chrome DevTools.
- Pass `--cpu-prof-dir <dir>` or `--cpu-prof-name <name>` to control where the
  profile is written.
- For memory analysis use `--heap-prof` in a similar manner.

Source maps generated during the TypeScript compilation allow you to map hot paths back to the original source files when examining the profile.

## 4. Linux `perf`

On Linux systems the `perf` tool can record low level events such as CPU cycles and cache misses.

```bash
perf record java -cp out magmac.Main
perf report
```

`perf` works with both the JVM and the Node.js runtimes and can reveal expensive system calls or tight loops.

## 5. In‑code Timing Helpers

For quick checks add lightweight timing code around phases of the compiler. Java's `System.nanoTime()` and Node's `console.time()`/`console.timeEnd()` can highlight which stages contribute most to total runtime.

Profiling results should be committed to the `docs` folder if they help track performance over time. Large raw data files (like `.jfr` or `.cpuprofile` dumps) are best kept out of version control.

