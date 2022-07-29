package com.example.demo;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RequestMapping("/json")
@RestController
public class Controller {

	//设置每秒100次最大访问限制
	private final RateLimiter limiter = RateLimiter.create(1.0);

	@RequestMapping("/getMsg")
	@ResponseBody
	public Msg getMsg() {
		//500毫秒内，没拿到令牌，就直接进入服务降级
		boolean tryAcquire = limiter.tryAcquire(500, TimeUnit.MILLISECONDS);
		Msg msg = new Msg();

		if (!tryAcquire)  msg.setMsg("当前访问数过多，获取令牌失败!");
		else msg.setMsg("获取令牌成功!");

		return msg;
	}

	@RequestMapping("/hello")
	public String hello() {
		return "Hello!";
	}

}

