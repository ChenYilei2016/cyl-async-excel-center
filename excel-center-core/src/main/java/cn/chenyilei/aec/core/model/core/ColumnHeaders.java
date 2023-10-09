package cn.chenyilei.aec.core.model.core;

import cn.chenyilei.aec.core.model.core.impl.ColumnHeadersImpl;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * excel headers
 */
public interface ColumnHeaders extends Serializable {

    ColumnHeadersImpl EMPTY = new ColumnHeadersImpl(Lists.newArrayList());

    List<ColumnHeader> getColumnHeaders();

    ColumnHeader getColumnHeaderByFieldName(String fieldName);

    ColumnHeader getColumnHeaderByHeaderName(List<String> headerName);

    ColumnHeader getColumnHeaderByHeaderNameKey(String headerNameKey);

    ColumnHeader getHeaderByIndex(Integer index);

    Integer getHeaderRowCount(Integer groupIndex);

    List<ColumnHeader> getHeadersByGroup(Integer groupIndex);

    ColumnHeader getHeaderByGroupAndColumn(Integer groupIndex, Integer columnIndex);

    void fromJson(String json);

    String toJson();

}
