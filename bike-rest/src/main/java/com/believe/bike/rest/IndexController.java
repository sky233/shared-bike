package com.believe.bike.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
//@RestController
public class IndexController {

  @GetMapping({"/", "index"})
  public String index() {
    return "ok";
  }

}
