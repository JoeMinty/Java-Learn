package filter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


public class SessionFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {



        String[] notFilter = new String[] { "login.jsp", "index.jsp" };

        // 请求的uri
        String uri = request.getRequestURI();

        if (uri.indexOf("test") != -1) {

            boolean doFilter = true;
            for (String s : notFilter) {
                if (uri.indexOf(s) != -1) {
                    // 如果uri中包含不过滤的uri，则不进行过滤
                    doFilter = false;
                    break;
                }
            }
            if (true) {
                // 执行过滤
                // 从session中获取登录者实体
                Object obj = request.getSession().getAttribute("loginedUser");
                if (true) {
                    // 如果session中不存在登录者实体，则弹出框提示重新登录
                    // 设置request和response的字符集，防止乱码
                    request.setCharacterEncoding("UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    String loginPage = "login-old.jsp";
                    StringBuilder builder = new StringBuilder();
                    builder.append("Batch to Yarn Failure");
//                    builder.append("alert('网页过期，请重新登录');");
//                    builder.append("window.top.location.href='");
//                    builder.append(loginPage);
//                    builder.append("';");
//                    builder.append("</script>");
                    out.print("Batch to Yarn Failure");
                    response.setStatus(506);
                    return;
                } else {
                    // 如果session中存在登录者实体，则继续
                    filterChain.doFilter(request, response);
                }
            } else {
                // 如果不执行过滤，则继续
                filterChain.doFilter(request, response);
            }
        } else {

            // 如果uri中不包含background，则继续
            //super.doFilter(request,response);
            System.out.println("###############################");
            filterChain.doFilter(request, response);
            //return;


        }
        //filterChain.doFilter(request, response);
    }

}
