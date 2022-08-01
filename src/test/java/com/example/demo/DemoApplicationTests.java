package com.example.demo;

import com.example.demo.MsgSender.Msg;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.alibaba.fastjson.JSON;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = Controller.class)
@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@org.junit.Test
	public void contextLoads() throws Exception {
		MvcResult mvc_res = mockMvc.perform(MockMvcRequestBuilders.get("/getMsg")).andReturn();

		Msg http_msg = JSON.parseObject(mvc_res.getResponse().getContentAsString(), Msg.class);

		assertEquals("从Token Bucket中获取Token成功！", http_msg.getMsg());
	}

}