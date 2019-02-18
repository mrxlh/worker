package com.collmall.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Mapper类实现该接口
 * @param <T>
 * @param <P>
 * @Author: xulihui
 * @Date: 2019/1/4 18:00
 */
public interface BaseMapper<T, P> {
    /**
     * 增加
     * @param t
     *            要增加的对象
     * @return 0:失败;1:成功
     */
    @Transactional
    public int insert(T t);

    /**
     * 删除
     * @param t
     *            要删除的对象
     * @return 0:失败;1:成功
     */
    @Transactional
    public int delete(T t);
    
    
    /**根据id删除
     * @param id
     * @return
     */
    @Transactional
    public int deleteByKey(@Param("id") long id);

    /**
     * 更新
     * @param t
     *            要更新的对象
     * @return 0:失败;1:成功
     */
    @Transactional
    public int update(T t);


    /**
     * 按主键查询
     * @param id
     *            过虑参数
     * @return 0:失败;1:成功
     */
    public T selectByPrimaryKey(long id);

    /**
     * 按对象字段信息查询对象记录
     * @param t
     * @return
     */
    T selectBean(T t);
    
    /**
     * 查询条数
     * @param p
     * @return
     */
    public long count(P p);

    /**
     * 按表XXX列查询集合
     * @param p
     *            参数内必需字段：columnName(数据库表的列名);list(要查询的数据数组 )
     * @return 集合
     */
    public List<T> selectByColumn(P p);

    /**
     * 查询集合
     * @param p
     *            查询时的过虑条件
     * @return 集合
     */
    public List<T> queryList(P p);

    /**
     * 批量增加
     * @param list
     *            要增加的对象集合
     * @return 成功增加的行数
     */
    @Transactional
    public int batchInsert(List<T> list);

    /**
     * 批量删除
     * @param p
     *            参数内必需：要删除的对象主键的集合
     * @return 0:失败;1:成功
     */
    @Transactional
    public int batchDelete(P p);


    /**
     * 批量更新
     * @param list
     *            要新的对象集合
     * @return 0:失败;1:成功
     */
    @Transactional
    public int batchUpdate(List<T> list);
    
    /**合计
     * @param p
     * @return
     */
    public T sum(P p);

}
