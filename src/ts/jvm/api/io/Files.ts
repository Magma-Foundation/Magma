import { List } from "../../../magma/api/collect/list/List";
import { Query } from "../../../magma/api/collect/Query";
import { IOError } from "../../../magma/api/io/IOError";
import { Path } from "../../../magma/api/io/Path";
import { None } from "../../../magma/api/option/None";
import { Option } from "../../../magma/api/option/Option";
import { Some } from "../../../magma/api/option/Some";
import { Result } from "../../../magma/api/result/Result";
import { FilesInstance } from "./FilesInstance";
import * as fs from "fs";
import * as paths from "path";
import { Lists } from "../collect/list/Lists";
import { HeadedQuery } from "../../../magma/api/collect/head/HeadedQuery";
import { RangeHead } from "../../../magma/api/collect/head/RangeHead";
import { Queries } from "../../../magma/api/collect/Queries";
import { Err } from "../../../magma/api/result/Err";
import { Ok } from "../../../magma/api/result/Ok";

class NodeIOError implements IOError {
    private readonly error: unknown;

    constructor(error: unknown) {
        this.error = error;
    }

    display(): string {
        return JSON.stringify(this.error);
    }
}

class NodePathImpl implements Path {
    constructor(private elements: List<string>) { }

    /** the real filesystem path */
    asString(): string {
        return this.elements.query()
            .foldWithMapper(first => first, (current, next) => paths.join(current, next))
            .orElse("");
    }

    writeString(output: string): Option<IOError> {
        try {
            fs.writeFileSync(this.asString(), output, "utf8");
            return new None();
        } catch (e) {
            return new Some(new NodeIOError(e));
        }
    }

    readString(): Result<string, IOError> {
        try {
            const content = fs.readFileSync(this.asString(), "utf8");
            return new Ok(content);
        } catch (e) {
            return new Err(new NodeIOError((e as Error).message));
        }
    }

    walk(): Result<List<Path>, IOError> {
        try {
            let entries: List<Path> = Lists.empty();

            const stats = fs.statSync(this.asString());
            if (stats.isDirectory()) {
                for (const name of fs.readdirSync(this.asString())) {
                    const child = new NodePathImpl(this.elements.addLast(name));
                    const subResult = child.walk();

                    const maybeError = subResult.findError();
                    if (maybeError.isPresent()) {
                        // bubble up the first error
                        return new Err(maybeError.orElse(new NodeIOError("")));
                    }

                    const maybeValue = subResult.findValue();
                    if (maybeValue.isPresent()) {
                        const value = maybeValue.orElse(Lists.empty());

                        // if directory, sub.value is a List<Path>; otherwise it’s [child]
                        entries = entries.addLast(child).addAll(value);
                    }
                }
            } else {
                // just this file
                entries = entries.addLast(this);
            }

            return new Ok(entries);
        } catch (e) {
            return new Err(new NodeIOError(e));
        }
    }

    findFileName(): string {
        return paths.basename(this.asString());
    }

    endsWith(suffix: string): boolean {
        return this.asString().endsWith(suffix);
    }

    relativize(source: Path): Path {
        const relative = paths.relative(this.asString(), source.asString());
        return new NodePathImpl(Lists.fromArray(relative.split(paths.sep)));
    }

    getParent(): Path {
        const parent = paths.dirname(this.asString());
        return new NodePathImpl(Lists.fromArray(parent.split(paths.sep)));
    }

    query(): Query<string> {
        return new HeadedQuery(new RangeHead(this.elements.size()))
            .map(this.elements.find.bind(this.elements))
            .flatMap(Queries.fromOption);
    }

    resolveChildSegments(children: List<string>): Path {
        return new NodePathImpl(this.elements.addAll(children));
    }

    resolveChild(name: string): Path {
        return new NodePathImpl(this.elements.addLast(name));
    }

    exists(): boolean {
        return fs.existsSync(this.asString());
    }

    createDirectories(): Option<IOError> {
        try {
            fs.mkdirSync(this.asString(), { recursive: true });
            return new None();
        } catch (e) {
            return new Some(new NodeIOError((e as Error).message));
        }
    }

    // handy for debugging
    toString(): string {
        return this.asString();
    }
}

export function NodePath(elements: string[]): Path {
    return new NodePathImpl(Lists.fromArray(elements));
}

export const Files: FilesInstance = {
    get: (first: string, ...more: string[]) => NodePath([first, ...more]),
};