package org.example.btp.featureflagsservice.core.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;

import static java.util.Objects.isNull;

public class FeatureFlagsPropertiesCondition implements Condition {

    private static final String PROPERTIES_PREFIX = "btp.services.feature-flags.";
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String MATCH_IF_MISSING = "matchIfMissing";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        var attributesMap = metadata.getAnnotationAttributes(ConditionalOnFeatureFlagsProperties.class.getName());
        var attributes = AnnotationAttributes.fromMap(attributesMap);
        if (isNull(attributes)) {
            return true;
        }

        var propertyName = attributes.getString(NAME);
        var requiredValue = attributes.getString(VALUE);
        var matchIfMissing = attributes.getBoolean(MATCH_IF_MISSING);

        var env = context.getEnvironment();
        var fullPropertyName = PROPERTIES_PREFIX.concat(propertyName);

        if (env.containsProperty(fullPropertyName)) {
            var actualValue = env.getProperty(fullPropertyName);
            return requiredValue.equals(actualValue);
        }
        return matchIfMissing;
    }
}
