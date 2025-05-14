package org.example.learningsystem.btp.destination.converter;

import org.example.learningsystem.btp.destination.annotation.DestinationProperty;
import org.example.learningsystem.btp.destination.dto.DestinationDto;
import org.example.learningsystem.btp.destination.exception.MissingNoArgsConstructorException;
import org.example.learningsystem.btp.destination.exception.MissingPropertyException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class DestinationConverter {

    public <T> T tryConvert(DestinationDto destination, Class<T> desiredType) {
        var destinationProperties = destination.getProperties();
        var requiredFields = desiredType.getDeclaredFields();
        var converted = createObject(desiredType);

        for (var requiredField : requiredFields) {
            var destinationPropertyName = requiredField.getAnnotation(DestinationProperty.class);
            if (destinationPropertyName != null) {
                var propertyValue = destinationPropertyName.value();
                if (!destinationProperties.containsKey(propertyValue)) {
                    throw new MissingPropertyException(desiredType.getSimpleName(), propertyValue);
                }

                setValueOfField(requiredField, converted, destinationProperties.get(propertyValue));
            }
        }
        return converted;
    }

    private void setValueOfField(Field field, Object object, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T createObject(Class<T> objectClass) {
        try {
            return objectClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new MissingNoArgsConstructorException(objectClass.getName());
        }
    }
}
