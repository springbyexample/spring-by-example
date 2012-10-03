package ua.com.springbyexample.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.springbyexample.dao.UserDetailsDao;
import ua.com.springbyexample.domain.User;

/**
 * Created with IntelliJ IDEA.
 * User: orezchykov
 * Date: 25.09.12
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
@Repository("userDetailsDao")
@Transactional(readOnly = true)
public class UserDetailsDaoImpl extends AbstractGenericDaoImpl<User, Long> implements UserDetailsDao {

    @Override
    public User findByUsername(String email) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", email));
        User user = (User) criteria.uniqueResult();
        return user;
    }
}
