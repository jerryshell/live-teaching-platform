package cn.jerryshell.liveteaching.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loginUserId = session.getAttribute("loginUserId");
        if (loginUserId == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
