package com.collmall.mapper;

import com.collmall.model.ScheduleTableFix;

import java.util.Map;

/**
 * worker切库配置信息
 * @author  xulihui
 * @date 2019-01-25
 */
public interface ScheduleTableFixMapper {

	ScheduleTableFix queryOne(Map<String, Object> map);

}
