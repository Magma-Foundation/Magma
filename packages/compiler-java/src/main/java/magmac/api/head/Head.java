package magmac.api.head;

import magmac.api.Option;

/**
 * Source of elements for an iteration.
 */

interface Head<T> {
    /**
     * Produces the next element or an empty option when done.
     */
    Option<T> next();
}
