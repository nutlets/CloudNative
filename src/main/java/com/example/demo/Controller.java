package com.example.demo;
import com.example.demo.Strategy.Strategy;
import com.example.demo.MsgSender.Msg;
import com.example.demo.MsgSender.Sender;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Controller {
	private final Sender msg_sender;

	public Controller(Sender msg_sender) {
		this.msg_sender = msg_sender;
	}

	@GetMapping("/getMsg")
	@Strategy(token_num = 100, strategy_name = "limiting")
	public Msg hello() {
		return msg_sender.getMsg();
	}
}