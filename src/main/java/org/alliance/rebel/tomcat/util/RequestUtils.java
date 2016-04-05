package org.alliance.rebel.tomcat.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestUtils {
  private ObjectMapper mapper = new ObjectMapper(new JsonFactory());

  public Map<String, Object> buildRequestDataMap(HttpServletRequest request) {
    Map<String, Object> result = new TreeMap<String, Object>();

    Map<String, Object> requestAttributes = new HashMap<String, Object>();
    result.put("requestAttributes", requestAttributes);
    Enumeration<String> attributeNames = request.getAttributeNames();
    while (attributeNames.hasMoreElements()) {
      String name = attributeNames.nextElement();
      if (name.startsWith("org.springframework")) {
        continue;
      }
      Object attrValue = request.getAttribute(name);
      if (attrValue != null) {
        requestAttributes.put(name, attrValue.toString());
      }

    }

    Map<String, Object> systemProperties = new TreeMap<String, Object>();
    result.put("systemProperties", systemProperties);
    for (Object object : System.getProperties().keySet()) {
      systemProperties.put((String) object, System.getProperty((String) object));
    }

    Map<String, Object> environmentVariables = new TreeMap<String, Object>();
    result.put("environmentVariables", environmentVariables);
    for (Entry<String, String> entry : System.getenv().entrySet()) {
      environmentVariables.put(entry.getKey(), parseJson(entry.getValue()));
    }

    return result;
  }

  private Object parseJson(String input) {
    if (!input.trim().startsWith("{")) {
      return input;
    }
    try {
      return mapper.readValue(input, new TypeReference<HashMap<String, Object>>() {
      });
    } catch (Exception e) {
      e.printStackTrace();
      return input;
    }
  }
}
