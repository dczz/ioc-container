package business;

import javax.annotation.Resource;

public class BService {

  @Resource
  AService aService;

  public void say(){
    System.err.println("im B,i execute aService");
    aService.say();
  }
}
