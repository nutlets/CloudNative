package com.example.demo.MsgSender;

public class Msg {
	private String msg = "从Token Bucket中获取Token成功！目前访问上限设置为每秒100次。";

	//两种声明方式，便于测试
	public Msg(String msg0) {
		this.msg = msg0;
	}

	public Msg() {}

	public String getMsg() {
		return msg;
	}
}
