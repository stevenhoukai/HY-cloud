package com.yyicbc.beans.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ListUtil {

    private ListUtil() {}

    public static boolean isPresent(Collection list) {
        return list != null && !list.isEmpty();
    }

    public static boolean isBlank(Collection list) {
        return list == null || list.isEmpty();
    }

    public static String join(Collection list, String sep) {
        if (isBlank(list)) {
            return "";
        }

        String prefix = "";
        StringBuffer buffer = new StringBuffer();
        for(Iterator<Object> iter = list.iterator(); iter.hasNext(); prefix = sep) {
            buffer.append(prefix).append(iter.next());
        }
        return buffer.toString();
    }

    public static <T> List<T> map(Collection list, String field) {
        List<T> result = new ArrayList<T>();
        try {
            String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
            for (Object value : list) {
                Method method = value.getClass().getMethod(methodName);
                T fieldValue = (T) method.invoke(value);
                result.add(fieldValue);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("对象没有定义" + field);
        }
        return result;
    }

    public interface IteratorTask<TargetType, SourceType> {
        TargetType getValue(SourceType item);
    }

    /**
     * 清除List中null值
     * */
    public static <T> List<T> removeNull(List<? extends T> oldList) {
        List<T> listTemp = new ArrayList();
        for (int i = 0;i < oldList.size(); i++) {
            if (oldList.get(i) != null) {
                listTemp.add(oldList.get(i));
            }
        }
        return listTemp;
    }


    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     * @param list
     * @param len
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        List<List<T>> result = new ArrayList<List<T>>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
}
