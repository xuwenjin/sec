package com.xwj.sec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwj.sec.result.CodeMsg;
import com.xwj.sec.result.Result;

@Controller
public class IndexController {
	
	@GetMapping("/hello")
	@ResponseBody
	public Result<String> hello() {
		return Result.success("成功");
	}

	@GetMapping("/helloError")
	@ResponseBody
	public Result<String> helloError() {
		return Result.error(CodeMsg.SERVER_ERROR);
	}

	@GetMapping("/index")
	public String index(Model model) {
		model.addAttribute("name", "xuwj");
		return "index";
	}
	
//	@GetMapping("/mq")
//	@ResponseBody
//	public Result<String> mq() {
//		sender.send("Hello world");
//		return Result.success("OK");
//	}
//	
//	@GetMapping("/mq/topic")
//	@ResponseBody
//	public Result<String> topic() {
//		sender.sendTopic("Hello world");
//		return Result.success("OK");
//	}
//	
//	@GetMapping("/mq/fanout")
//	@ResponseBody
//	public Result<String> fanout() {
//		sender.sendFanout("Hello world");
//		return Result.success("OK");
//	}

}
