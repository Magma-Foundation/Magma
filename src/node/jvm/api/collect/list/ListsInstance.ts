import { List } from "../../../../magma/api/collect/list/List";
export interface ListsInstance {static fromArray<T>(elements: T[]): List<T>;static empty<T>(): List<T>;static of<T>(...elements: T[]): List<T>;
}
