package cn.chenyilei.center.spi.utils.tmp;

import lombok.Data;

/**
 * @author chenyilei
 * @date 2023/09/25 15:24
 */
@Data
public class TestGenericType<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
