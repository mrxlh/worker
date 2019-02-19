package com.collmall.controller;

import com.collmall.query.WorkerQuery;
import com.collmall.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * worker 监控
 * @author xulihui
 * @date 2019-02-19
 */
@RestController
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	private WorkerService workerService;



	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("worker.html");
	}

	@RequestMapping(value = "/queryList")
	public Map<String, ?> queryList(@ModelAttribute WorkerQuery query) {
		return workerService.queryList(query).toPagination();
	}

	/**
	 * 重置 /resetTask?task_type=write_back_sol&fingerprint=20618&status=0&execute_count=0
	 * @param taskType
	 * @param fingerprint
	 * @param status
	 * @param executeCount
	 * @return
	 */
	@RequestMapping("/resetTask")
	public Map<String, ?> resetTask(@RequestParam(value = "task_type", required = true) String taskType,
			@RequestParam(value = "fingerprint", required = true) String fingerprint,
			@RequestParam(value = "status", required = true) Integer status,
			@RequestParam(value = "execute_count", required = true) Integer executeCount) {
		return workerService.resetTask(taskType, fingerprint, status, executeCount).toMap();
	}

	/**
	 * 手动执行 executeTask?task_type=write_back_sol&fingerprint=3
	 * @param taskType
	 * @param fingerprint
	 * @return
	 */
	@RequestMapping("/executeTask")
	public Map<String, ?> executeTask(@RequestParam(value = "task_type", required = true) String taskType,
			@RequestParam(value = "fingerprint", required = true) String fingerprint) {
		return workerService.executeTask(taskType, fingerprint).toMap();
	}

}
