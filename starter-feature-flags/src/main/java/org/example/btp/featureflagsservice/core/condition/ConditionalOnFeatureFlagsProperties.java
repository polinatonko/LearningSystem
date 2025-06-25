package org.example.btp.featureflagsservice.core.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Documented
@Conditional(FeatureFlagsPropertiesCondition.class)
public @interface ConditionalOnFeatureFlagsProperties {

    /**
     * The name of the property to test, without the "btp.services.feature-flags." prefix.
     */
    String name();

    /**
     * The value the property must have.
     */
    String value() default "";

    /**
     * Specify if the condition should match if the property is not set.
     */
    boolean matchIfMissing() default true;
}
