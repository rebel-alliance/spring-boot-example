package org.alliance.rebel.tomcat.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alliance.rebel.tomcat.util.RequestUtils;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppErrorController implements ErrorController {

  @Override
  public String getErrorPath() {
    // TODO Auto-generated method stub
    return "/error";
  }

  @RequestMapping("/error")
  @ResponseBody
  public Map<String, Object> error(HttpServletRequest request) {
    return new RequestUtils().buildRequestDataMap(request);
  }

}
