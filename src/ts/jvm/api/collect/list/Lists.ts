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

export class NodeList<T> implements List<T> {
  private readonly elements: T[];

  constructor(elements: T[]) {
    this.elements = elements;
  }

  addLast(element: T): List<T> {
    return new NodeList([...this.elements, element]);
  }

  addFirst(element: T): List<T> {
    return new NodeList([element, ...this.elements]);
  }

  addAll(others: List<T>): List<T> {
    return others
      .query()
      .foldWithInitial(this as List<T>, (current, e) => current.addLast(e));
  }

  query(): Query<T> {
    return this.queryWithIndices().map(tuple => tuple.right());
  }

  queryWithIndices(): Query<Tuple2<number, T>> {
    return new HeadedQuery(new RangeHead(this.elements.length))
      .map(i => new Tuple2Impl(i, this.elements[i]));
  }

  queryReversed(): Query<T> {
    return new HeadedQuery(new RangeHead(this.elements.length))
      .map(i => this.elements.length - i - 1)
      .map(i => this.elements[i]);
  }

  size(): number {
    return this.elements.length;
  }

  isEmpty(): boolean {
    return this.elements.length === 0;
  }

  contains(element: T): boolean {
    return this.elements.indexOf(element) !== -1;
  }

  subList(startInclusive: number, endExclusive: number): Option<List<T>> {
    if (
      startInclusive < 0 ||
      endExclusive > this.elements.length ||
      startInclusive > endExclusive
    ) {
      return new None();
    }
    return new Some(
      new NodeList(this.elements.slice(startInclusive, endExclusive))
    );
  }

  findFirst(): Option<T> {
    return this.isEmpty()
      ? new None()
      : new Some(this.elements[0]);
  }

  findLast(): Option<T> {
    return this.isEmpty()
      ? new None()
      : new Some(this.elements[this.elements.length - 1]);
  }

  find(index: number): Option<T> {
    if (index < 0 || index >= this.elements.length) {
      return new None();
    }
    return new Some(this.elements[index]);
  }

  removeValue(element: T): List<T> {
    const idx = this.elements.indexOf(element);
    if (idx === -1) return this;
    return new NodeList([
      ...this.elements.slice(0, idx),
      ...this.elements.slice(idx + 1),
    ]);
  }

  removeLast(): Option<List<T>> {
    if (this.isEmpty()) return new None();
    return new Some(new NodeList(this.elements.slice(0, -1)));
  }

  equalsTo(other: List<T>): boolean {
    if (this.size() !== other.size()) return false;
    return this.query()
      .zip(other.query())
      .allMatch(tuple => tuple.left() === tuple.right());
  }
}

// And now update your ListsInstance to use the class:

export const Lists: ListsInstance = {
  empty: function <T>(): List<T> {
    return new NodeList<T>([]);
  },

  of: function <T>(...elements: T[]): List<T> {
    return new NodeList<T>(elements);
  },

  fromArray: function <T>(elements: T[]): List<T> {
    return new NodeList<T>(elements);
  },
};
