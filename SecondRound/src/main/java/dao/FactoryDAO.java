package dao;

/**
 * @author LiuJiaHui
 */
public class FactoryDAO {
      public  static  UserDAO getUserDaoInstance(){
          return UserDAO.getInstance();
      }
}
