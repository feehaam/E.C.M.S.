package com.ecm.productsquery.utilities;

import com.ecm.productsquery.exceptions.CustomException;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SuppressWarnings("unchecked")
public interface MapperUtil {
    @SuppressWarnings("unchecked")
    public default <T> T read(Map<String, Object> mappedObject, String key, Class<T> expectedClass,
                               String errorMessage) {
        Object object = mappedObject.getOrDefault(key, null);
        if (object == null) {
            throw new CustomException(errorMessage, HttpStatus.BAD_REQUEST);
        }
        if (!expectedClass.isInstance(object)) {
            return null;
        }
        return (T) object;
    }

    public default <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        try {
            T object = clazz.newInstance();
            // For each of the properties of the expected class, try to assign value.
            for (Field field : clazz.getDeclaredFields()) {
                setValue(map, clazz, field, object);
            }
            return object;
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to create object from map", e);
        }
    }

    private <T> void setValue(Map<String, Object> map, Class<T> clazz, Field field, Object object) throws IllegalAccessException {
        field.setAccessible(true);
        String fieldName = field.getName();
        Object value = map.get(fieldName);

        // Set the value based on its type, ignore if the filed doesn't exist in JSON
        if (!map.containsKey(fieldName)) {
            return;
        }
        else if (isPrimitive(field)) {
            setPrimitiveValue(field, object, value);
        }
        else if (field.getType().isEnum()) {
            setEnumValue(field, object, value);
        }
        else if (value instanceof Map) {
            setMapValue(value, field, object);
        }
        else if (value instanceof List<?>) {
            setListValue(value, field, object);
        }
        else {
            field.set(object, value);
        }
    }

    private boolean isPrimitive(Field field){
        return field.getType().isPrimitive() ||
                field.getType().equals(Integer.class) ||
                field.getType().equals(Double.class) ||
                field.getType().equals(Boolean.class) ||
                field.getType().equals(Character.class) ||
                field.getType().equals(Long.class);
    }

    // Recursively calls the parent method to set up the nested object first.
    private <T> void setMapValue(Object value, Field field, Object object) throws IllegalAccessException {
        Object nestedObject = mapToObject((Map) value, field.getType());
        field.set(object, nestedObject);
    }

    // Checks whether the items inside the list are objects of another class or not, and sets them accordingly.
    private <T> void setListValue(Object value, Field field, Object object) throws IllegalAccessException {
        List<?> listValue = (List<?>) value;
        List<Object> mappedList = new ArrayList<>();

        if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Class<?> listElementType = (Class<?>) parameterizedType.getActualTypeArguments()[0];

            for (Object listItem : listValue) {
                if (listItem instanceof Map) {
                    mappedList.add(mapToObject((Map) listItem, listElementType));
                } else {
                    mappedList.add(listItem);
                }
            }
        }

        field.set(object, mappedList);
    }

    // Responsible for putting the enum values.
    private <T> void setEnumValue(Field field, T object, Object value) throws IllegalAccessException {
        if (value instanceof String) {
            Enum<?> enumValue = Enum.valueOf((Class<Enum>) field.getType(), (String) value);
            field.set(object, enumValue);
        }
    }

    // Responsible for putting the primitive values.
    private <T> void setPrimitiveValue(Field field, T object, Object value) throws IllegalAccessException {
        if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
            field.setInt(object, Integer.parseInt(String.valueOf(value)));
        }
        else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
            field.setDouble(object, Double.parseDouble(String.valueOf(value)));
        }
        else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
            field.setBoolean(object, Boolean.parseBoolean(String.valueOf(value)));
        }
        else if (field.getType().equals(char.class) || field.getType().equals(Character.class)) {
            field.setChar(object, String.valueOf(value).charAt(0));
        }
        else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
            field.setLong(object, Long.parseLong(String.valueOf(value)));
        }
    }
}