package com.jose.api.restServices;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandlerController implements ErrorController {

	@Override
	@RequestMapping("/error")
	@ResponseBody
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "<center><h1>Something went wrong</h1></center>";
	}

}
