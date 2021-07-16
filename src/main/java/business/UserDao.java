package business;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

  private final Map<Long, User> db = new HashMap<Long, User>() {
    {
      put(1L, new User(1L, "zhangsan"));
      put(2L, new User(2L, "lisi"));
    }
  };

  public User findById (Long id) {
    return db.get(id);
  }
}
