package com.yyicbc.beans.utils.distribute.method;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MapUtil {
    /**
     * 实现键值对集合中将对应的键值,赋值给目标对象的属性
     * 键的值必须和目标对象的属性名一致,基本数据类型必须是字符串格式
     * 可以实现任意类型的赋值
     * @param map 键值对集合
     * @param o   要赋值的目标对象
     */
    public static void copyValue(HashMap<String, Object> map, Object o) {
        Class c = o.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            if (map.get(f.getName()) != null) {
                String methodName = "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                try {
                    Method m = c.getDeclaredMethod(methodName, f.getType());
                    Object par = map.get(f.getName());
                    Class typeClass = f.getType();
                    if (typeClass.isPrimitive()) {
                        String p = (String) par;
                        if ("int".equals(typeClass.getName())) {
                            par = Integer.parseInt(p);
                        } else if ("char".equals(typeClass.getName())) {
                            par = p.charAt(0);
                        } else {
                            String className = typeClass.getName().substring(0, 1).toUpperCase() + typeClass.getName().substring(1);
                            Class packageClass = Class.forName("java.lang." + className);
                            Method parseMethod = packageClass.getDeclaredMethod("parse" + className, String.class);
                            par = parseMethod.invoke(par, p);
                        }
                    } else
                        par = typeClass.cast(par);
                    m.invoke(o, par);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}