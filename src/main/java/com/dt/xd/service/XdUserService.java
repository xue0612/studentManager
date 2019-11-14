package com.dt.xd.service;

import java.util.List;

import com.dt.xd.xdProduct.XdProduct;
import com.dt.xd.xdUser.XdUser;

public interface XdUserService {

	public List<XdUser> ope_login(String phone);
	
	List<XdUser> selectByPrimaryKey(String id);
	
	public int ope_repassword(XdUser user);

	int insert(XdUser record);

	List<XdProduct> selectByExample(int pageStart, int pageSize, String productName);

	public long getCount();

	int getCount(String productName);

	List<XdProduct> selectByName(int pageStart, int pageSize, String productName);
	
	int updateImg(XdProduct xdProduct);
	
	XdProduct selectByPrimaryKey(Integer id);
	
}
