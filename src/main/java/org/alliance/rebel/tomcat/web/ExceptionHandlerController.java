package org.alliance.rebel.tomcat.web;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alliance.rebel.tomcat.util.RequestUtils;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Controller
public class ExceptionHandlerController implements ErrorController {

  @ExceptionHandler(value = { Exception.class, RuntimeException.class })
  @ResponseBody
  public Map<String, Object> defaultErrorHandler(HttpServletRequest request, Exception e) {
    Map<String, Object> result = new RequestUtils().buildRequestDataMap(request);
    result.put("exception", e.getClass().getName());
    return result;
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }

  @RequestMapping("/error")
  @ResponseBody
  public Map<String, Object> error(HttpServletRequest request) {

    return Collections.singletonMap("error", (Object) "true");
  }

}