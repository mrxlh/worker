package com.collmall.controller;


import com.collmall.constant.TaskType;
import com.collmall.constant.WorkerStatus;
import com.collmall.model.EnumModel;
import com.collmall.result.Result;
import com.collmall.util.EnumUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/enum")
public class EnumController {

	@RequestMapping(value = "/queryList")
	public Map<String, ?> queryList() {
		Map<String, Object> map = new HashMap<>();
		map.put("task_type", enumToList(TaskType.values()));
		map.put("worker_status", enumToList(WorkerStatus.values()));
		return new Result<>(map).toMap();
	}

	private List<Map<String, Object>> enumToList(Enum<?>[] ens) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Enum<?> en : ens) {
			Map<String, Object> map = enumToMap(en);
			list.add(map);
		}
		return list;
	}

	private Map<String, Object> enumToMap(Enum<?> en) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo info = Introspector.getBeanInfo(en.getClass());
			PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
			for (PropertyDescriptor property : descriptors) {
				if (property.getPropertyType().getName().equals("java.lang.Class"))
					continue;

				Object value = property.getReadMethod().invoke(en, new Object[] {});
				map.put(property.getName(), value);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return map;
	}


	@RequestMapping(value = "/getByName")
	public Result getByName(@RequestParam(value = "name",required = false) String shortName) {
		List<EnumModel> list= EnumUtil.getByName(shortName);
		return new Result<>(list);
	}

}
