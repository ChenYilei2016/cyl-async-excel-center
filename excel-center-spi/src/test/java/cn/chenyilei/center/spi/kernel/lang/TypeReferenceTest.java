package cn.chenyilei.center.spi.kernel.lang;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author chenyilei
 * @date 2023/09/25 10:46
 */
public class TypeReferenceTest {

    @Test
    public void getType() {
        TypeReference listTypeReference = new TypeReference<List<String>>(){};

        System.err.println(listTypeReference.getType().getClass());
        System.err.println(listTypeReference.getType());
        System.err.println(listTypeReference.getTypeName());
    }
}