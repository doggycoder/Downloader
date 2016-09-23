package com.wuwang.frame;

/**
 * Description:转换器接口
 */
public interface Changer<B,T>{

    /**
     * 对B的实体t进行转换，变为一个T的实体
     * */
    T change(B t);

}
