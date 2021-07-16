package business;

import javax.annotation.Resource;

public class UserService {

  @Resource
  UserDao userDao;

  public User getUserById (Long id) {
    return userDao.findById(id);
  }
}
