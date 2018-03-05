package com.epam.spring.hometask.utils;

import java.util.HashMap;
import java.util.Map;

public class GenIdUtil
{
    private static Map<Class, Long> classMap = new HashMap<>();

    public static long getId(Class cl)
    {
        long id = classMap.containsKey(cl) ? classMap.get(cl) + 1 : 1;
        classMap.put(cl, id);
        return id;
    }
}
