package com.hlju.mall.service.impl;

import java.util.List;

import com.hlju.mall.dao.UserDAO;
import com.hlju.mall.domain.User;
import com.hlju.mall.domain.UserExample;
import com.hlju.mall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlju.mall.commom.Encryption;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDao;

	@Override
	public int insertSelective(User record) {
		String password = record.getPwd();
		record.setPwd(Encryption.str2MD5(password));
		return userDao.insertSelective(record);
	}

	@Override
	public boolean hasPassword(User user) {
		List<User> users = selectByExample(user);
		if (users==null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<User> selectByExample(User user) {
		UserExample uExample = new UserExample();
		UserExample.Criteria criteria = uExample.createCriteria();
		if (StringUtils.isNotBlank(user.getName())) {
			criteria.andNameEqualTo(user.getName());
			if (StringUtils.isNotBlank(user.getPwd())) {
				String password = Encryption.str2MD5(user.getPwd());
				criteria.andPwdEqualTo(password);
				List<User> users=userDao.selectByExample(uExample);
				if (users!=null&&users.size()!=0){
					return userDao.selectByExample(uExample);
				}else {
					return null;
				}
			}
		}
		return null;
	}

	public boolean hasUserName(User user) {
		List<User> users = selectUserNameByExamle(user);
		if (users==null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<User> selectUserNameByExamle(User record) {
		UserExample uExample = new UserExample();
		UserExample.Criteria criteria = uExample.createCriteria();
		String userName = StringUtils.isNotEmpty(record.getName()) ?  record.getName():null;
		if (userName!=null&&!userName.isEmpty()) {
			criteria.andNameEqualTo(userName);
			List<User> users=userDao.selectByExample(uExample);
			if (users!=null&&users.size()!=0){
				return  users;
			}else {
				return null;
			}
		}
		return null;
	}

	@Override
	public List<User> selectUserListByIds(List<Integer> ids) {
		List<User> users;
		UserExample uExample = new UserExample();
		UserExample.Criteria criteria = uExample.createCriteria();
		criteria.andIdIn(ids);
		users=userDao.selectByExample(uExample);

		return users;
	}

	@Override
	public boolean saveUserMsg(User user,String userName) {
		UserExample userExample=new UserExample();
		UserExample.Criteria userExampleCriteria=userExample.createCriteria();
		userExampleCriteria.andNameEqualTo(userName);
		int var=userDao.updateByExampleSelective(user,userExample);
		if (var==1){
			return true;
		}
		return false;
	}

	@Override
	public Integer showUserBalance(String userName) {
		UserExample userExample=new UserExample();
		UserExample.Criteria userExampleCriteria=userExample.createCriteria();
		userExampleCriteria.andNameEqualTo(userName);

		List<User> users=userDao.selectByExample(userExample);

		Integer balance=users.get(0).getCredit();

		return balance;
	}

	@Override
	public boolean incrUserCash(User user,Integer totalPrice) {
		if (totalPrice!=null&&user.getId()!=null){
			Integer balance=user.getCredit()-totalPrice;
			user.setCredit(balance);
			userDao.updateByPrimaryKeySelective(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean backUserCash(Integer userId, Integer cash) {
		if (cash!=null&&userId!=null){
			User user=userDao.selectByPrimaryKey(userId);
			Integer curCash=user.getCredit()+cash;
			user.setCredit(curCash);
			userDao.updateByPrimaryKeySelective(user);
			return true;
		}
		return false;
	}


}
