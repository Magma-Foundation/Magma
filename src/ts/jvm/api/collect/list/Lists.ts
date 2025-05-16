import { HeadedQuery } from "../../../../magma/api/collect/head/HeadedQuery";
import { RangeHead } from "../../../../magma/api/collect/head/RangeHead";
import { List } from "../../../../magma/api/collect/list/List";
import { Query } from "../../../../magma/api/collect/Query";
import { None } from "../../../../magma/api/option/None";
import { Option } from "../../../../magma/api/option/Option";
import { Some } from "../../../../magma/api/option/Some";
import { Tuple2 } from "../../../../magma/api/Tuple2";
import { Tuple2Impl } from "../../../../magma/api/Tuple2Impl";
import { ListsInstance } from "./ListsInstance";
export function NodeList<T>(elements: T[]): List<T> {
    return {
        addLast(element: T): List<T> {
            return NodeList([...elements, element]);
        },

        query(): Query<T> {
            return this.queryWithIndices().map(tuple => tuple.right());
        },

        size(): number {
            return elements.length;
        },

        subList(startInclusive: number, endExclusive: number): Option<List<T>> {
            if (
                startInclusive < 0 ||
                endExclusive > elements.length ||
                startInclusive > endExclusive
            ) {
                return new None();
            }
            return new Some(NodeList(elements.slice(startInclusive, endExclusive)));
        },

        findLast(): Option<T> {
            if (elements.length === 0) return new None();
            return new Some(elements[elements.length - 1]);
        },

        findFirst(): Option<T> {
            if (elements.length === 0) return new None();
            return new Some(elements[0]);
        },

        find(index: number): Option<T> {
            if (index < 0 || index >= elements.length) return new None();
            return new Some(elements[index]);
        },

        queryWithIndices(): Query<Tuple2<number, T>> {
            return new HeadedQuery(new RangeHead(elements.length))
                .map(index => new Tuple2Impl(index, elements[index]));
        },

        addAll(others: List<T>): List<T> {
            return others.query().foldWithInitial(this, (current, element) => current.addLast(element));
        },

        contains(element: T): boolean {
            return elements.indexOf(element) !== -1;
        },

        queryReversed(): Query<T> {
            return new HeadedQuery(new RangeHead(elements.length))
                .map(index => elements.length - index - 1)
                .map(index => elements[index]);
        },

        addFirst(element: T): List<T> {
            return NodeList([element, ...elements]);
        },

        isEmpty(): boolean {
            return elements.length === 0;
        },

        equalsTo(other: List<T>): boolean {
            if (this.size() !== other.size()) return false;

            return this.query().zip(other.query())
                .allMatch(tuple => tuple.left() === tuple.right());
        },

        removeValue(element: T): List<T> {
            const idx = elements.indexOf(element);
            if (idx === -1) return this;
            return NodeList([
                ...elements.slice(0, idx),
                ...elements.slice(idx + 1),
            ]);
        },

        removeLast(): Option<List<T>> {
            if (elements.length === 0) return new None();
            return new Some(NodeList(elements.slice(0, elements.length - 1)));
        },
    };
}


export const Lists: ListsInstance = {
    empty: function <T>(): List<T> {
        return this.fromArray([]);
    },
    of: function <T>(...elements: T[]): List<T> {
        return this.fromArray(elements);
    },
    fromArray: function <T>(elements: T[]): List<T> {
        return NodeList(elements);
    }
};