package com.example.demo.MsgSender;

import org.springframework.stereotype.Service;

@Service
public class SenderImpl implements Sender {
	public Msg getMsg(){
		return new Msg("从Token Bucket中获取Token成功！");
	}
}
