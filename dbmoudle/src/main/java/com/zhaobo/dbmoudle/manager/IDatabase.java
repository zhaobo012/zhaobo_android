package com.zhaobo.dbmoudle.manager;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by zhaobo on 17/4/12.
 * 只定义一些通用的查询，如果是某些特殊的查询需在在其单独的类中实现
 */

public interface IDatabase<M,K> {
    boolean insert(@NonNull M m);
    boolean deleteByKey(@NonNull K k);
    List<M> findAll();
    boolean insertInTx(M... entities);
    boolean insertInTx(List<M> entities);
    boolean insertOrReplace(M entity);
    boolean insertOrReplaceInTx(M... entities);
    boolean insertOrReplaceInTx(List<M> entities);
    boolean deleteByKeyInTx(K... entities);
    boolean deleteByKeyInTx(List<K> entities);
    boolean deleteAll();
    boolean delete(M entity);
    boolean deleteInTx(M... entities);
    boolean deleteInTx(List<M> entities);
    boolean update(M entity);
    boolean updateInx(M... entities);
    boolean updateInx(List<M> entities);
    M findById(@NonNull K k);
    List<M> findRaw(String where,String... selectionArgs);
    QueryBuilder<M> queryBuilder();
}
