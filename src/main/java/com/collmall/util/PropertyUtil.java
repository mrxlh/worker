package com.collmall.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * @author  xulihui
 * @date 2019-01-19
 */
public class PropertyUtil {

	/**
	 * 驼峰式 => 下划线
	 * @param name
	 * @return
	 */
    public static String camelToUnderline(String name)
    {
    	if (name == null || name.length() == 0)
    		return name;
    	
        int length = name.length();
        char buff[] = new char[length * 2];
        int count = 0;
        for(int i = 0; i < length; i++)
        {
            char c = name.charAt(i);
            if(i > 0 && Character.isUpperCase(c))
            {
                buff[count++] = '_';
                buff[count++] = Character.toLowerCase(c);
            } else
            {
                buff[count++] = Character.toLowerCase(c);
            }
        }

        return new String(buff, 0, count);
    }
    
    public static String underlineToCamel(String name) {
    	if (name == null || name.length() == 0)
    		return name;
    	
        int length = name.length();
        char buff[] = new char[length];
        int count = 0;
        for(int i = 0; i < length; i++)
        {
            char c = name.charAt(i);
            if(c == '_')
                buff[count++] = Character.toUpperCase(name.charAt(++i));
            else
                buff[count++] = Character.toLowerCase(c);
        }

        return new String(buff, 0, count);
    }

	public static Object getProperty(Object obj, String propertyName) {
		try {
			BeanInfo info = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
			for (PropertyDescriptor property : descriptors) {
				if (property.getName().equals(propertyName))
					return property.getReadMethod().invoke(obj, new Object[] {});
			}
			return null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void setProperty(Object obj, String propertyName, Object value) {
		try {
			BeanInfo info = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
			for (PropertyDescriptor property : descriptors) {
				if (property.getName().equals(propertyName)) {
					property.getWriteMethod().invoke(obj, new Object[] { value });
					return;
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void setProperties(Object obj, Map<String, Object> map) {
		try {
			BeanInfo info = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
			for (PropertyDescriptor property : descriptors) {
				String key = property.getName();
				Object value = map.get(camelToUnderline(key));
				if (value != null) {
					value = convertNumberType(value, property.getPropertyType());
					property.getWriteMethod().invoke(obj, new Object[] { value });
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Object convertNumberType(Object value, Class<?> cls) {
		if (cls == byte.class || cls == Byte.class) {
			value = value instanceof Number ? ((Number) value).byteValue() : Byte.parseByte(value.toString());
		}
		else if (cls == short.class || cls == Short.class) {
			value = value instanceof Number ? ((Number) value).shortValue() : Short.parseShort(value.toString());
		}
		else if (cls == int.class || cls == Integer.class) {
			value = value instanceof Number ? ((Number) value).intValue() : Integer.parseInt(value.toString());
		}
		else if (cls == long.class || cls == Long.class) {
			value = value instanceof Number ? ((Number) value).longValue() : Long.parseLong(value.toString());
		}
		else if (cls == float.class || cls == Float.class) {
			value = value instanceof Number ? ((Number) value).floatValue() : Float.parseFloat(value.toString());
		}
		else if (cls == double.class || cls == Double.class) {
			value = value instanceof Number ? ((Number) value).doubleValue() : Double.parseDouble(value.toString());
		}
		else if (cls == String.class){
			value = value.toString();
		}
		return value;
	}
    public static void setPropertiesCommon(Object obj, Map<String, Object> map) {
        try {
            BeanInfo info = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
            for (PropertyDescriptor property : descriptors) {
                String key = property.getName();
                Object value = map.get(key);
                if (value != null) {
                    value = convertNumberType(value, property.getPropertyType());
                    property.getWriteMethod().invoke(obj, new Object[] { value });
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
