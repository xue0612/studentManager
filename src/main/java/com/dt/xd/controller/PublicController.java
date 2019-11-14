package com.dt.xd.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dt.xd.service.XdShoppingService;
import com.dt.xd.service.XdUserService;
import com.dt.xd.xdProduct.XdProduct;
import com.dt.xd.xdShopping.XdShopping;
import com.dt.xd.xdUser.XdUser;

@Controller
public class PublicController {
	@Resource
	XdUserService xdUserService;
	@Resource
	XdShoppingService xdShoppingService;
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("code");
		String imgcode = request.getParameter("imgcode");
		System.out.println("getCommodity====" + request.getParameter("phone"));
		System.out.println("getCommodity====" + request.getParameter("password"));
		int code1 = 0;
		String phone = request.getParameter("phone");
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(imgcode + "=====" + code + "===");
		if (imgcode.toUpperCase().equals(code)) {
			List<XdUser> loginList = xdUserService.ope_login(phone);
			XdUser password = loginList.get(0);
			if (password.getPassword().equals(request.getParameter("password"))) {
				code1 = 1;
				map.put("code", code1);
			} else {
				map.put("code", code1);
			}
		} else {
			map.put("code", code1);
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/repassword", method = RequestMethod.POST)
	public Map<String, Object> repassword(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("code");
		String imgcode = request.getParameter("imgcode");
		
		System.out.println("getCommodity====" + request.getParameter("phone"));
		String phone = request.getParameter("phone");
		int code1 = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		if (imgcode.toUpperCase().equals(code)) {
			List<XdUser> loginList = xdUserService.ope_login(phone);
			XdUser password = loginList.get(0);
			XdUser user = new XdUser();
			user.setId(password.getId());
			user.setPhone(password.getPhone());
			user.setPassword(request.getParameter("password"));
			user.setHeadImg(password.getHeadImg());
			user.setEmail(password.getEmail());
			user.setName(password.getName());
			user.setStatus(password.getStatus());
			user.setTs(password.getTs());
			if (request.getParameter("password").equals(request.getParameter("password1"))) {
				xdUserService.ope_repassword(user);
				code1 = 1;
				map.put("code", code1);
			} else {
				map.put("code", code1);
			}
		} else {
			map.put("code", code1);
		}
		System.out.println(imgcode + "=====" + code + "===");
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Map<String, Object> register(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("code");
		String imgcode = request.getParameter("imgcode");
		String id=request.getParameter("id");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		int code1=0;
		Map<String, Object> map = new HashMap<String, Object>();
		if (imgcode.toUpperCase().equals(code)) {
		XdUser user = new XdUser();
		user.setId(id);
		user.setPhone(phone);
		user.setPassword(password);
		xdUserService.insert(user);
			code1 = 1;
			map.put("code", code1);
		} else {
			map.put("code", code1);
		}
		System.out.println(imgcode + "=====" + code + "===");
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/shoping", method = RequestMethod.POST)
	public Map<String, Object> shoping() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<XdShopping> xdShoppingList = xdShoppingService.selectAll();
		map.put("xdShoppingList", xdShoppingList);
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/shop", method = RequestMethod.POST)
	public Map<String, Object> shop(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<XdShopping> xdShoppingList = xdShoppingService.selectAll();
		int str = Integer.parseInt(request.getParameter("coco"))+1;
		String st = Integer.toString(str);
		XdShopping user = new XdShopping();
		user.setId(st);
		user.setCount(2);
		user.setProId(Integer.parseInt(request.getParameter("id")));
		int i = xdShoppingService.insertShopping(user);
		map.put("code", i);
		map.put("xdShoppingList", xdShoppingList);
		return map;
	}

	@RequestMapping("/fenyelike")
	//@RequestParam 参数是基本数据类型，不赋初始置，容易报错，用此注解赋默认值
	public String fenyelike(Map<String,Object> map,
			@RequestParam(defaultValue="0")int pageStart,
			@RequestParam(defaultValue="3") int pageSize,
			@RequestParam(defaultValue="")String productName) {
		
		System.out.println(productName);
		List<XdProduct> xdProductList = xdUserService.selectByName(pageStart,pageSize,productName);
		long count=0;
		if(productName.equals("")) {
			count=xdUserService.getCount();
		}
		else {
			count=xdUserService.getCount(productName);
		}
		System.out.println("productName==return"+productName);
		map.put("count", count);
		map.put("xdProductList", xdProductList);
		map.put("pageStart", pageStart);
		map.put("pageSize",pageSize);
		map.put("productName", productName);
		return  "e-commerce_product";
	}
	@RequestMapping("/loadimg")
	public String loadimg() {
		return "upfile";
	}
	@RequestMapping(value="/upfile")
	public ModelAndView saveUserImg(MultipartFile file,Integer id) {
		Map<Object,Object> result = new HashMap<Object,Object>();
//		HttpSession session = request.getSession();
//		Integer id = (Integer) session.getAttribute("id");
		try {
		// 获取客户端传图图片的输入流
		InputStream ins = file.getInputStream();
		byte[] buffer=new byte[1024];//bit---byte---1k---1m
		int len=0;
		 // 字节输出流
		 ByteArrayOutputStream bos=new ByteArrayOutputStream();
		while((len=ins.read(buffer))!=-1){
			bos.write(buffer,0,len);
		 }
		 bos.flush();
		byte data[] = bos.toByteArray();
		// 调用接口对图片进行存储
		XdProduct xdProduct = new XdProduct();
		xdProduct.setId(id);
		xdProduct.setImg(data);
		xdUserService.updateImg(xdProduct);
				            
		result.put("code",1);
		} catch (Exception e) {
			//return "error";
		 }				
		return new ModelAndView("redirect:/fenyelike");	
	}

	@RequestMapping(value = "/headImg", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> headImg(Integer id) throws Exception{
		byte[] imageContent ;
		// 根据id获取当前用户的信息
		XdProduct xdUser = xdUserService.selectByPrimaryKey(id);
				        
		imageContent = xdUser.getImg();
				        
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}
	@RequestMapping("/e_shoppingcar")
	//@RequestParam 参数是基本数据类型，不赋初始置，容易报错，用此注解赋默认值
	public String e_shoppingcar(Map<String,Object> map,
			@RequestParam(defaultValue="0")int pageStart,
			@RequestParam(defaultValue="3") Integer pageSize,
			@RequestParam(defaultValue="")String id) {
		List<XdShopping> xdShoppingList = xdShoppingService.selectById(pageStart,pageSize,id);
		long count=0;
		if(id.equals("")||id==null)
			count=xdShoppingService.getCount();
		else 
			count=xdShoppingService.getCount(id);
		map.put("count", count);
		map.put("xdShoppingList", xdShoppingList);
		map.put("pageStart", pageStart);
		map.put("pageSize",pageSize);
		map.put("id", id);
		return  "e-commerce_shoping-car";
		}
	@ResponseBody
	@RequestMapping(value = "/e_delete", method = RequestMethod.POST)
	public Map<String, Object> e_delete(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("id"+request.getParameter("id"));
		int i=xdShoppingService.deleteByPrimaryKey(request.getParameter("id"));
		System.out.println("======"+i);
		if(i==1)
			map.put("code", "success");
		else
			map.put("code", "false");
		return map;
	}

}
