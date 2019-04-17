package cn.jerryshell.liveteaching.interceptor;

import cn.jerryshell.liveteaching.dao.LiveDao;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class LiveCountInterceptor implements HandlerInterceptor {
    private LiveDao liveDao;

    public LiveCountInterceptor(LiveDao liveDao) {
        this.liveDao = liveDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Integer liveCount = liveDao.countByDateAfter(new Date(System.currentTimeMillis() - 86400000));
        session.setAttribute("liveCount", liveCount);
        return true;
    }
}
