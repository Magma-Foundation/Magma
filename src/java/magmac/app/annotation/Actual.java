package magmac.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks code that has platform specific implementations.
 * Classes annotated with {@code @Actual} are excluded from the
 * TypeScript output.  When applied to static methods only the
 * signature is kept.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Actual {
}
