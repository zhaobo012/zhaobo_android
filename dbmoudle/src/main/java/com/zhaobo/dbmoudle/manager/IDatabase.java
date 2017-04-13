package com.zhaobo.dbmoudle.manager;

import android.support.annotation.NonNull;

/**
 * Created by zhaobo on 17/4/12.
 */

public interface IDatabase<M,K> {
    boolean insert(@NonNull M m);
    boolean deleteByKey(@NonNull K k);
}
