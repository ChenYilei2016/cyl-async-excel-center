package cn.chenyilei.aec.spi.convert.impl;


import cn.chenyilei.aec.spi.convert.AbstractConverter;

/**
 * 字符串转换器MyCharUtils
 */
public class StringConverter extends AbstractConverter<String> {
    private static final long serialVersionUID = 1L;

    @Override
    protected String convertInternal(Object value) {
        return convertToStr(value);
    }

}
