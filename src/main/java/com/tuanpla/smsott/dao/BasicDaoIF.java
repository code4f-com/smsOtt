/*
 *  Copyright 2022 by Tuanpla
 *  https://tuanpla.com
 */
package com.tuanpla.smsott.dao;

import com.tuanpla.smsott.db.model.ext.ResponseResult;
import com.tuanpla.utils.common.Nullable;

/**
 *
 * @author tuanp
 * @param <T>
 */
public interface BasicDaoIF<T> {

    public T findById(long id);

    public ResponseResult create(T one, @Nullable Object... opParam);

    public ResponseResult update(T one, @Nullable Object... opParam);

    public ResponseResult delete(long id);

}
