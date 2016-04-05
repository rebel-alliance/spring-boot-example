/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.alliance.rebel.tomcat.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alliance.rebel.tomcat.service.HelloWorldService;
import org.alliance.rebel.tomcat.util.RequestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

  @Autowired
  private HelloWorldService helloWorldService;

  private Log log = LogFactory.getLog(getClass());

  @RequestMapping("/hello")
  @ResponseBody
  public String helloWorld() {
    log.info("hello world!");
    return this.helloWorldService.getHelloMessage();
  }

  @RequestMapping(value = "/setSession/{key}/{value}")
  @ResponseBody
  public String setSession(@PathVariable("key") String key, @PathVariable("value") String value, HttpSession session) {
    session.setAttribute(key, value);
    return session.getId();
  }

  @RequestMapping(value = "/getSession/{key}")
  @ResponseBody
  public String setSession(@PathVariable("key") String key, HttpSession session) {
    return (String) session.getAttribute(key);
  }

  /**
   * @param request
   * @return
   */
  @RequestMapping(value = "/showenvironment")
  @ResponseBody
  public Map<String, Object> showEnvironment(HttpServletRequest request) {
    return new RequestUtils().buildRequestDataMap(request);
  }

}
