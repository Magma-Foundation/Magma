import { List } from "../../../magma/api/collect/list/List";
import { Query } from "../../../magma/api/collect/Query";
import { IOError } from "../../../magma/api/io/IOError";
import { Path } from "../../../magma/api/io/Path";
import { Option } from "../../../magma/api/option/Option";
import { Result } from "../../../magma/api/result/Result";
import { FilesInstance } from "./FilesInstance";

export function NodePath(elements: string[]): Path {
    return {
        writeString: function (output: string): Option<IOError> {
            throw new Error("Function not implemented.");
        },
        readString: function (): Result<string, IOError> {
            throw new Error("Function not implemented.");
        },
        walk: function (): Result<List<Path>, IOError> {
            throw new Error("Function not implemented.");
        },
        findFileName: function (): string {
            throw new Error("Function not implemented.");
        },
        endsWith: function (suffix: string): boolean {
            throw new Error("Function not implemented.");
        },
        relativize: function (source: Path): Path {
            throw new Error("Function not implemented.");
        },
        getParent: function (): Path {
            throw new Error("Function not implemented.");
        },
        query: function (): Query<string> {
            throw new Error("Function not implemented.");
        },
        resolveChildSegments: function (children: List<string>): Path {
            throw new Error("Function not implemented.");
        },
        resolveChild: function (name: string): Path {
            throw new Error("Function not implemented.");
        },
        exists: function (): boolean {
            throw new Error("Function not implemented.");
        },
        createDirectories: function (): Option<IOError> {
            throw new Error("Function not implemented.");
        }
    }
}

export const Files: FilesInstance = {
    get: function (first: string, ...more: string[]): Path {
        return NodePath([first, ...more]);
    }
};