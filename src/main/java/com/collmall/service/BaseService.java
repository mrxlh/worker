package com.collmall.service;


import com.collmall.result.Result;

import java.util.List;

/**
 * Service继承该接口
 * @param <T>
 * @param <P>
 * @Author: xulihui
 * @Date: 2019/1/4 18:00
 */
public interface BaseService<T, P> {

	/**
     * 增加
     * @param t
     *            要增加的对象
     * @return 0:失败;1:成功
     */
    public Result<Integer> insert(T t);

    /**
     * 删除
     * @param t
     *            要删除的对象
     * @return 0:失败;1:成功
     */
    public Result<Integer> delete(T t);

    /**
     * 更新
     * @param t
     *            要更新的对象
     * @return 0:失败;1:成功
     */
    public Result<Integer> update(T t);

    /**
     * 按主键查询
     * @param id
     *            过虑参数
     * @return 对象
     */

    public Result<T> selectByPrimaryKey(long id);

    /**
     * 查询条数
     * @param p
     * @return
     */
    public Result<Long> count(P p);

    /**
     * 按表XXX列查询集合
     * @param p
     *            参数内必需字段：columnName(数据库表的列名);list(要查询的数据数组 )
     * @return 集合
     */
    public Result<List<T>> selectByColumn(P p);

    /**
     * 查询集合
     * @param p
     *            查询时的过虑条件
     * @return 集合
     */
    public Result<List<T>> queryList(P p);

    /**
     * 批量增加
     * @param list
     *            要增加的对象集合
     * @return 成功增加的行数
     */
    public Result<Integer> batchInsert(List<T> list);


    /**
     * 批量删除
     * @param p
     *            要删除的对象主键的集合
     * @return 0:失败;1:成功
     */
    public Result<Integer> batchDelete(P p);

    /**
     * 批量更新
     * @param list
     *            要新的对象集合
     * @return 0:失败;1:成功
     */
    public Result<Integer> batchUpdate(List<T> list);
    
    /**合计
     * @param p
     * @return
     */
    public Result<T> sum(P p);
}
