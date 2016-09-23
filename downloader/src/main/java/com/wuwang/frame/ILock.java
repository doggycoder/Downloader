/*
 *
 * ILock.java
 * 
 * Created by Wuwang on 2016/9/23
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.frame;

/**
 * Description:对象锁
 */
public interface ILock<B> {

    void lock(B b);
    void unlock(B b);
    boolean isLock(B b);

}
