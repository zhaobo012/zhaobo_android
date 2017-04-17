package com.zhaobo.dbmoudle.manager;

import com.zhaobo.dbmoudle.model.User;
import com.zhaobo.dbmoudle.model.UserDao;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

/**
 * Created by zhaobo on 17/4/12.
 */

public class UserDBManager extends AbstractDatabaseManager<User, Long> {
    @Override
    protected AbstractDao getAbstractDao() {
        if (daoSession != null) {
            return daoSession.getUserDao();
        }
        return null;
    }

    /**
     * 定制查询
     *
     * @param userName
     * @return
     */
    public User findUserByName(String userName) {
//        findRaw("where NICKNAME = ?",new String[]{userName});
        List<User> users = queryBuilder().where(UserDao.Properties.Nickname.eq(userName)).list();
        if (users != null && users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
