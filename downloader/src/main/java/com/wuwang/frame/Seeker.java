package com.wuwang.frame;

/**
 * Description: 输入输出接口
 */
public interface Seeker<I,O> {

    /**
     * 通过T的实体index得到一个O的实体
     * */
    O seek(I index);
}
