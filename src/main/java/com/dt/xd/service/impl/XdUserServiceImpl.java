package com.dt.xd.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dt.xd.dao.mapper.XdProductMapper;
import com.dt.xd.dao.mapper.XdUserMapper;
import com.dt.xd.service.XdUserService;
import com.dt.xd.xdProduct.XdProduct;
import com.dt.xd.xdProduct.XdProductExample;
import com.dt.xd.xdUser.XdUser;
import com.dt.xd.xdUser.XdUserExample;

@Service
public class XdUserServiceImpl implements XdUserService{
	@Resource
	XdUserMapper xdUserMapper;
	@Resource
	XdProductMapper xdProductMapper;
	
	@Override
	public List<XdUser> ope_login(String phone) {
		XdUserExample xdUserExample=new XdUserExample();
		XdUserExample.Criteria criteria=xdUserExample.createCriteria();
		criteria.andPhoneEqualTo(phone);
		return xdUserMapper.selectByExample(xdUserExample);
	}
	@Override
	public int ope_repassword(XdUser user) {
		XdUserExample xdUserExample=new XdUserExample();
		XdUserExample.Criteria criteria=xdUserExample.createCriteria();
		return xdUserMapper.updateBy(user.getPassword(), user.getPhone());
	}
	@Override
	public int insert(XdUser record) {
		return xdUserMapper.insert(record);
	}
	@Override
	public List<XdProduct> selectByExample(int pageStart, int pageSize, String productName) {
		XdProductExample xdProductExample=new XdProductExample();
		xdProductExample.setDistinct(true);
		xdProductExample.setPageSize(pageSize);
		xdProductExample.setPageStart(pageStart);
		return xdProductMapper.selectByExample(xdProductExample,productName);
	}
	@Override
	public long getCount() {
		XdProductExample xdProductExample=new XdProductExample();
		return xdProductMapper.countByExample();
	}
	@Override
	public int getCount(String productName) {
		return xdProductMapper.getCount(productName);
	}
	@Override
	public List<XdProduct> selectByName(int pageStart, int pageSize, String productName) {
		XdProductExample xdProductExample=new XdProductExample();
		xdProductExample.setProductName(productName);
		xdProductExample.setPageSize(pageSize);
		xdProductExample.setPageStart(pageStart);
		return xdProductMapper.selectByName(xdProductExample);
	}
	@Override
	public int updateImg(XdProduct xdProduct) {
		return xdProductMapper.updateByPrimaryKey(xdProduct);
	}
	@Override
	public XdProduct selectByPrimaryKey(Integer id) {
		return xdProductMapper.selectByPrimaryKey(id);
	}
	@Override
	public List<XdUser> selectByPrimaryKey(String id) {
		XdUserExample xdUserExample=new XdUserExample();
		XdUserExample.Criteria criteria=xdUserExample.createCriteria();
		criteria.andIdEqualTo(id);
		return xdUserMapper.selectByExample(xdUserExample);
	}
	
}
