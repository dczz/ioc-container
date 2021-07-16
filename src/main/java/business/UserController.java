package business;

import javax.annotation.Resource;

public class UserController {

  @Resource
  private UserService userService;

  public void sayHello () {
    System.err.println("hello");
  }

  public User getUserById (long id) {
    return userService.getUserById(id);
  }
}
