package com.quickwebapp.core.entity;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * 对TreeMap的一个封装，未添加任何属性，只是添加了很多辅助方法。
 * 
 * @author 袁进勇
 *
 */
public class MapEntity extends TreeMap<String, Object> {
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final Integer MAX_PAGE_SIZE = 10000;
    public static final String PAGE_SIZE = "pageSize";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String TOTAL_COUNT = "totalCount";
    public static final String ORDER_BY = "orderBy";

    private static final long serialVersionUID = 1L;

    public MapEntity() {
        super();
    }

    public MapEntity(Map<String, Object> map) {
        super();
        this.putAll(map);
    }

    public MapEntity setPageSize(Integer pageSize) {
        this.put(PAGE_SIZE, pageSize);
        return this;
    }

    public MapEntity setPageSizeWithDefault() {
        this.put(PAGE_SIZE, DEFAULT_PAGE_SIZE);
        return this;
    }

    public MapEntity setPageSizeWithMax() {
        this.put(PAGE_SIZE, MAX_PAGE_SIZE);
        return this;
    }

    public Integer getPageSize() {
        return this.getInteger(PAGE_SIZE, null);
    }

    public MapEntity setCurrentPage(Integer currentPage) {
        this.put(CURRENT_PAGE, currentPage);
        return this;
    }

    public Integer getCurrentPage() {
        return this.getInteger(CURRENT_PAGE, null);
    }

    public MapEntity setTotalCount(Integer totalCount) {
        this.put(TOTAL_COUNT, totalCount);
        return this;
    }

    public Integer getTotalCount() {
        return this.getInteger(TOTAL_COUNT, null);
    }

    public MapEntity setOrderBy(String orderBy) {
        this.put(ORDER_BY, orderBy);
        return this;
    }

    public String getOrderBy() {
        return this.getString(ORDER_BY, null);
    }

    public String getString(final Object key) {
        Object answer = get(key);
        if (answer != null) {
            return answer.toString();
        }
        return null;
    }

    public String getString(final Object key, String defaultValue) {
        String answer = getString(key);
        if (answer != null) {
            return answer;
        }
        return defaultValue;
    }

    public Boolean getBoolean(final Object key) {
        Object answer = get(key);
        if (answer != null) {
            if (answer instanceof Boolean) {
                return (Boolean) answer;
            } else if (answer instanceof String) {
                return new Boolean((String) answer);
            } else if (answer instanceof Number) {
                return (((Number) answer).intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
            }
        }
        return null;
    }

    public Boolean getBoolean(final Object key, Boolean defaultValue) {
        Boolean answer = getBoolean(key);
        if (answer != null) {
            return answer;
        }
        return defaultValue;
    }

    public Number getNumber(final Object key) throws ParseException {
        Object answer = get(key);
        if (answer != null) {
            if (answer instanceof Number) {
                return (Number) answer;
            } else if (answer instanceof String) {
                return NumberFormat.getInstance().parse((String) answer);
            }
            throw new ParseException("对象" + answer + "不能解析成为数字！", 0);
        }
        return null;
    }

    public Number getNumber(final Object key, Number defaultValue) {
        try {
            Number answer = getNumber(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Byte getByte(final Object key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Byte) {
                return (Byte) answer;
            }
            return new Byte(answer.byteValue());
        }
        return null;
    }

    public Byte getByte(final Object key, Byte defaultValue) {
        try {
            Byte answer = getByte(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Short getShort(final Object key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Short) {
                return (Short) answer;
            }
            return new Short(answer.shortValue());
        }
        return null;
    }

    public Short getShort(final Object key, Short defaultValue) {
        try {
            Short answer = getShort(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Integer getInteger(final Object key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Integer) {
                return (Integer) answer;
            }
            return new Integer(answer.intValue());
        }
        return null;
    }

    public Integer getInteger(final Object key, Integer defaultValue) {
        try {
            Integer answer = getInteger(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Long getLong(final Object key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Long) {
                return (Long) answer;
            }
            return new Long(answer.longValue());
        }
        return null;
    }

    public Long getLong(final Object key, Long defaultValue) {
        try {
            Long answer = getLong(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Float getFloat(final Object key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Float) {
                return (Float) answer;
            }
            return new Float(answer.floatValue());
        }
        return null;
    }

    public Float getFloat(final Object key, Float defaultValue) {
        try {
            Float answer = getFloat(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Double getDouble(final Object key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof Double) {
                return (Double) answer;
            }
            return new Double(answer.doubleValue());
        }
        return null;
    }

    public Double getDouble(final Object key, Double defaultValue) {
        try {
            Double answer = getDouble(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public BigDecimal getBigDecimal(final Object key) throws ParseException {
        Number answer = getNumber(key);
        if (answer != null) {
            if (answer instanceof BigDecimal) {
                return (BigDecimal) answer;
            }
            return new BigDecimal(answer.toString());
        }

        return null;
    }

    public BigDecimal getBigDecimal(final Object key, BigDecimal defaultValue) {
        try {
            BigDecimal answer = getBigDecimal(key);
            if (answer != null) {
                return answer;
            }
        } catch (ParseException e) {
        }
        return defaultValue;
    }

    public Map<?, ?> getMap(final Object key) {
        Object answer = get(key);
        if (answer != null && answer instanceof Map) {
            return (Map<?, ?>) answer;
        }
        return null;
    }

    public Map<?, ?> getMap(Object key, Map<?, ?> defaultValue) {
        Map<?, ?> answer = getMap(key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    public Properties toProperties() {
        Properties answer = new Properties();
        for (Iterator<Map.Entry<String, Object>> iter = entrySet().iterator(); iter.hasNext();) {
            Map.Entry<String, Object> entry = iter.next();
            answer.put(entry.getKey(), entry.getValue());
        }
        return answer;
    }

    public void putAll(final ResourceBundle resourceBundle) {
        Enumeration<String> enumeration = resourceBundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            put(key, resourceBundle.getObject(key));
        }
    }

    public void safePut(String key, Object value) {
        if (value != null) {
            put(key, value);
        }
    }

    public void safePut(String key, Object value, Object defaultValue) {
        if (value == null) {
            put(key, defaultValue);
        } else {
            put(key, value);
        }
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}