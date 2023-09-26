package cn.chenyilei.aec.spi.convert.impl;


import cn.chenyilei.aec.spi.convert.AbstractConverter;
import cn.chenyilei.aec.spi.utils.MyBooleanUtils;
import cn.chenyilei.aec.spi.utils.MyStrUtils;

/**
 * 字符转换器
 */
public class CharacterConverter extends AbstractConverter<Character> {
    private static final long serialVersionUID = 1L;

    @Override
    protected Character convertInternal(Object value) {
        if (value == null) {
            return null;
        }

        if (Character.class == value.getClass()) {
            return (Character) value;
        } else if (value instanceof Boolean) {
            return MyBooleanUtils.toCharacter((Boolean) value);
        } else {
            final String valueStr = convertToStr(value);
            if (MyStrUtils.isNotBlank(valueStr)) {
                return valueStr.charAt(0);
            }
        }
        return null;
    }

}
