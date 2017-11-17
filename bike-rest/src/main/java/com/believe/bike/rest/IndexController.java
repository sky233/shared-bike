package com.believe.bike.rest;

import com.believe.bike.core.help.MessagesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@RestController
public class IndexController {

  @Autowired
  private MessagesHelper messagesHelper;

  @GetMapping({"/", "index"})
  public String index() {
    return messagesHelper.get("com.believe.bike.info.app");
  }

}
