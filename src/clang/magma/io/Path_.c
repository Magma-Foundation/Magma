package magma.io;class package magma.io;{package magma.io;

import magma.collect.set.Set_;class import magma.collect.set.Set_;{

import magma.collect.set.Set_;
import magma.collect.stream.Stream;class import magma.collect.stream.Stream;{
import magma.collect.stream.Stream;
import magma.option.Option;class import magma.option.Option;{
import magma.option.Option;
import magma.result.Result;class import magma.result.Result;{
import magma.result.Result;

public interface Path_ {
    boolean exists();

    Result<Set_<Path_>, IOError> walk();

    Option<IOError> writeString(String output);

    Option<IOError> createAsDirectories();

    Path_ resolve(String child);

    Stream<String> stream();

    Path_ relativize(Path_ child);

    boolean isRegularFile();

    String asString();

    Path_ getParent();

    Path_ getFileName();

    Result<String, IOError> readString();
}
class public interface Path_ {
    boolean exists();

    Result<Set_<Path_>, IOError> walk();

    Option<IOError> writeString(String output);

    Option<IOError> createAsDirectories();

    Path_ resolve(String child);

    Stream<String> stream();

    Path_ relativize(Path_ child);

    boolean isRegularFile();

    String asString();

    Path_ getParent();

    Path_ getFileName();

    Result<String, IOError> readString();
}{

public interface Path_ {
    boolean exists();

    Result<Set_<Path_>, IOError> walk();

    Option<IOError> writeString(String output);

    Option<IOError> createAsDirectories();

    Path_ resolve(String child);

    Stream<String> stream();

    Path_ relativize(Path_ child);

    boolean isRegularFile();

    String asString();

    Path_ getParent();

    Path_ getFileName();

    Result<String, IOError> readString();
}
