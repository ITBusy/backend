package com.poly.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Convert {

    public static String CapitalAllFirstLetter(String name) {
        if (name != null) {
            String[] split = name.split(" ");
            StringBuilder sb = new StringBuilder();
            for (String s : split) {
                sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1).toLowerCase()).append(" ");
            }
            return sb.toString().trim();
        }
        return null;
    }

    public static String CapitalFirstLetter(String name) {
        if (!Objects.equals(name, "")) {
            String[] split = name.split(" ");
            StringBuilder sb = new StringBuilder();
            System.out.println(split.length);
            if (split.length > 1) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < split.length; i++) {
                    stringBuilder.append(split[i].substring(0).toLowerCase()).append(" ");
                }
                sb.append(Character.toUpperCase(split[0].charAt(0))).append(split[0].substring(1).toLowerCase()).append(" ").append(stringBuilder);
            } else {
                sb.append(Character.toUpperCase(split[0].charAt(0))).append(split[0].substring(1).toLowerCase());
            }
            return sb.toString().trim();
        }
        return null;
    }

    public static <E> E objectToClass(@NotNull Object[] obj, @NotNull Class<E> clazz) {
        Map<String, Object> stringObjectMap = new HashMap<>();
//        Class<?> aClass = clazz.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            stringObjectMap.put(fields[i].getName(), obj[i]);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(stringObjectMap, clazz);
    }
}
