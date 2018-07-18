package com.xwj.sec.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwj.sec.dao.SeckillUserDao;
import com.xwj.sec.entity.SeckillUser;
import com.xwj.sec.exception.GlobalException;
import com.xwj.sec.redis.RedisService;
import com.xwj.sec.redis.SeckillUserKey;
import com.xwj.sec.result.CodeMsg;
import com.xwj.sec.util.MD5Util;
import com.xwj.sec.util.UUIDUtil;
import com.xwj.sec.vo.LoginVo;

@Service
public class SeckillUserService {

	public static final String COOKI_NAME_TOKEN = "token";

	@Autowired
	private SeckillUserDao seckillUserDao;

	@Autowired
	private RedisService redisService;

	/**
	 * 通过用户id获取秒杀用户
	 */
	public SeckillUser getById(long id) {
		// 取缓存
		SeckillUser user = redisService.get(SeckillUserKey.getById, "" + id, SeckillUser.class);
		if (user != null) {
			return user;
		}
		// 取数据库
		user = seckillUserDao.getById(id);
		if (user != null) {
			redisService.set(SeckillUserKey.getById, "" + id, user);
		}
		return user;
	}

	/**
	 * 通过token获取用户信息
	 */
	public SeckillUser getByToken(HttpServletResponse response, String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		SeckillUser user = redisService.get(SeckillUserKey.token, token, SeckillUser.class);
		// 延长有效期
		if (user != null) {
			addCookie(response, token, user);
		}
		return user;
	}

	/**
	 * 登录
	 */
	public boolean login(HttpServletResponse response, LoginVo loginVo) {
		if (loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		// 判断手机号是否存在
		SeckillUser user = getById(Long.parseLong(mobile));
		if (user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		// 验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if (!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		// 生成cookie
		String token = UUIDUtil.uuid();
		addCookie(response, token, user);
		return true;
	}

	/**
	 * 增加cookie，同时将用户信息放在redis中,这样就可以通过token获取用户信息
	 */
	private void addCookie(HttpServletResponse response, String token, SeckillUser user) {
		redisService.set(SeckillUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(SeckillUserKey.token.expireSeconds()); // 设置cookie有效期
		cookie.setPath("/"); // 设置根目录
		response.addCookie(cookie);
	}

}
