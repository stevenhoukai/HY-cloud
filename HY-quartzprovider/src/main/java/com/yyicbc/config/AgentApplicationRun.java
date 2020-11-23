//package com.yyicbc.config;
//
//import com.netflix.discovery.converters.Auto;
//import com.yyicbc.job.Job;
//import com.yyicbc.service.QuartzService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import java.util.Date;
//import java.util.HashMap;
//
//
///**
// * 测试类
// */
//@Component
//@Order(value = 1)
//public class AgentApplicationRun implements CommandLineRunner {
//
//	@Autowired
//	QuartzService quartzService;
//
//	@Override
//	public void run(String... strings) throws Exception {
////		HashMap<String,Object> map = new HashMap<>();
////		map.put("name",3);
////		quartzService.deleteJob("job3", "icbc");
////		quartzService.addJob(Job.class, "job3", "icbc", "15 * * * * ?","job任务3", map, new Date(),new Date(new Date().getTime()+1000000));
//	}
//}
