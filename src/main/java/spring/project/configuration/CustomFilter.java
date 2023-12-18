package spring.project.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if (
                path.equals("/login") ||
                path.equals("/registration") ||
                path.equals("/login?logout") ||
                path.equals("/login?expired")
        ) {
            filterChain.doFilter(request, response);
        } else {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                response.sendRedirect("/login");
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
