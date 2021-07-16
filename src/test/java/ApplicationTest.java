import java.util.HashMap;

import business.AService;
import business.BService;
import business.User;
import business.UserController;
import business.UserDao;
import business.UserService;
import context.Application;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApplicationTest {

  Application application;

  @BeforeEach
  void settings(){
    final HashMap<String, Class<?>> packageClassMap = new HashMap<String, Class<?>>() {
      {
        put("userController", UserController.class);
        put("userService", UserService.class);
        put("userDao", UserDao.class);
        put("aService", AService.class);
        put("bService", BService.class);
      }
    };
    application = new Application(packageClassMap);
  }

  @Test
  void should_get_all_bean () {
    final UserController userController = application.getBean("userController", UserController.class);
    final User userById = userController.getUserById(1L);
    Assertions.assertNotNull(userById);
  }

  @Test
  void should_get_service () {
    final UserService userService = application.getBean("userService", UserService.class);
    final User userById = userService.getUserById(1L);
    Assertions.assertNotNull(userById);
  }

  @Test
  void should_execute_service_a(){
    final AService aService = application.getBean("aService", AService.class);
    aService.say();
    final BService bService = application.getBean("bService", BService.class);
    bService.say();
  }

}
