package com.collmall.service.impl;


import com.collmall.exception.ConstantUtil;
import com.collmall.exception.ServiceException;
import com.collmall.mapper.BaseMapper;
import com.collmall.model.BaseModel;
import com.collmall.query.QueryParam;
import com.collmall.result.Result;
import com.collmall.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 接口实现类的base类
 * @param <T> 实体类
 * @param <P> 查询参数
   @Author: xulihui
 * @Date: 2019/1/4 18:00
 */
public abstract class BaseServiceImpl<T extends BaseModel, P extends QueryParam>
		implements BaseService<T, P> {

	public static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	public abstract BaseMapper<T, P> getMapper();

	@Transactional
	@Override
	public Result<Integer> insert(T t) {
		check(t);
		return new Result<>(this.getMapper().insert(t));
	}

	protected void check(T t) {
	}

	@Transactional
	@Override
	public Result<Integer> delete(T t) {
		return new Result<Integer>(this.getMapper().delete(t));
	}

	@Transactional
	@Override
	public Result<Integer> update(T t) {
		check(t);
		return new Result<Integer>(this.getMapper().update(t));
	}

	@Override
	public Result<T> selectByPrimaryKey(long id) {
		return new Result<T>(this.getMapper().selectByPrimaryKey(id));
	}

	@Override
	public Result<Long> count(P p) {
		return new Result<Long>(this.getMapper().count(p));
	}

	@Override
	public Result<List<T>> selectByColumn(P p) {
		List<T>  list= this.getMapper().selectByColumn(p);
		afterSelectByColumn(list);
		return new Result<List<T>>(list);
	}

	@Override
	public Result<List<T>> queryList(P p) {
		try {
			checkQueryParam(p);
			List<T> list = this.getMapper().queryList(p);
			afterQueryList(list);
			Long count = 0L;
			if (list.size() < p.getPageSize() && p.getPage() == 1) {
				count = (long) list.size();
			} else {
				count = this.getMapper().count(p);
			}
			return new Result<List<T>>(count.intValue(), p.getPageSize(),
					p.getPage(), list);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			return new Result<>(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new Result<>(ConstantUtil.OpCode.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	protected void afterQueryList(List<T> list) {

	}
	
	protected void  afterQueryList() {
		
	}
	
    protected void afterSelectByColumn(List<T> list){

	}

	protected void checkQueryParam(P p){
		
	}

	@Transactional
	@Override
	public Result<Integer> batchInsert(List<T> list) {
		return new Result<Integer>(this.getMapper().batchInsert(list));
	}

	@Transactional
	@Override
	public Result<Integer> batchDelete(P p) {
		return new Result<Integer>(this.getMapper().batchDelete(p));
	}

	@Transactional
	@Override
	public Result<Integer> batchUpdate(List<T> list) {
		return new Result<Integer>(this.getMapper().batchUpdate(list));
	}
	
	@Override
	public Result<T> sum(P p) {
		return new Result<T>(this.getMapper().sum(p));
	}


}
