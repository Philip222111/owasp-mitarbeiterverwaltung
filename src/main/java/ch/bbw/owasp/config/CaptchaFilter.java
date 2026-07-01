package ch.bbw.owasp.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        boolean loginPost = request.getRequestURI().equals("/login") && request.getMethod().equalsIgnoreCase("POST");

        if (loginPost) {
            String captcha = request.getParameter("captcha");
            if (!"7".equals(captcha)) {
                response.sendRedirect("/login?captchaError");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
