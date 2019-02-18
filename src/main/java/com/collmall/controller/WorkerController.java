package com.collmall.controller;

import com.collmall.query.WorkerQuery;
import com.collmall.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	private WorkerService workerService;


	@RequestMapping(value = "/queryList")
	public Map<String, ?> queryList(@ModelAttribute WorkerQuery query) {
		return workerService.queryList(query).toPagination();
	}

	@RequestMapping("/resetTask")
	public Map<String, ?> resetTask(@RequestParam(value = "task_type", required = true) String taskType,
			@RequestParam(value = "fingerprint", required = true) String fingerprint,
			@RequestParam(value = "status", required = true) Integer status,
			@RequestParam(value = "execute_count", required = true) Integer executeCount) {
		return workerService.resetTask(taskType, fingerprint, status, executeCount).toMap();
	}

	@RequestMapping("/executeTask")
	public Map<String, ?> executeTask(@RequestParam(value = "task_type", required = true) String taskType,
			@RequestParam(value = "fingerprint", required = true) String fingerprint) {
		return workerService.executeTask(taskType, fingerprint).toMap();
	}

}
