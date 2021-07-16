package business;

import javax.annotation.Resource;

public class AService {

  @Resource
  BService bService;

  public void say () {
    System.err.println("im A,i execute b do say");
    //bService.say();
  }
}
