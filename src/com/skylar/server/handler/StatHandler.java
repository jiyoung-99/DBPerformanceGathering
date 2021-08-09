package com.exem.server.handler;


import com.exem.util.vo.message.ValueObject;

import java.util.List;

/**
 * Stat abstract class
 */
public abstract class StatHandler<T extends ValueObject> {

    //핸들러 실행
    public abstract void apply(List<T> ValueObject);

}
