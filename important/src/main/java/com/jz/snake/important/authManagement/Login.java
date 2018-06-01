package com.jz.snake.important.authManagement;

import com.jz.snake.important.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.nutz.dao.impl.NutDao;
import org.nutz.integration.shiro.SimpleShiroToken;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

/**
 * Created by jzshi on 2018/5/31.
 */
public class Login {
    // 映射 /user/login , 与shiro.ini对应.
    @POST
    @Ok("json")
    @At
    public Object login(@Param("username")String username,
                        @Param("password")String password,
                        @Param("rememberMe")boolean rememberMe,
                        @Param("captcha")String captcha) {
        NutMap re = new NutMap().setv("ok", false);
        // 如果已经登陆过,直接返回真
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated())
            return re.setv("ok", true);
        // 检查用户名密码验证码是否正确,这里就不写出来了.
        // ............
        // 检查用户名密码
        User user = null;//dao.fetch(User.class, username);
        if (user == null) {
            return re.setv("msg", "用户不存在");
        }
        // 比对密码, 严重建议用hash和加盐!!
        String face = new Sha256Hash(password, user.getSalt()).toHex();
        if (!face.equalsIgnoreCase(user.getPassword())) {
            return re.setv("msg", "密码错误");
        }
        subject.login(new SimpleShiroToken(user.getId()));
        // 需要放点东西进session,如果配置了ShiroSessionProvider,下面两种代码等价
        // req.getSession().setAttribute("me", user);
        // subject.getSession().setAttribute("me", user);
        return re.setv("ok", true);
    }
}
