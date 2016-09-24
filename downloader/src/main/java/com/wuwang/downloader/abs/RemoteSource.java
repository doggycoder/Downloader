/*
 *
 * RemoteSource.java
 * 
 * Created by Wuwang on 2016/9/24
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.wuwang.downloader.abs;

import com.wuwang.exception.SourceException;
import com.wuwang.frame.Source;

/**
 * Description:
 */
public interface RemoteSource extends Source{

    String name() throws SourceException;

}
