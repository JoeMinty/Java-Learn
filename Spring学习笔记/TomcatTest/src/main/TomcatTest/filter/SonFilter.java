package filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SonFilter extends TestFilter {


    @Override
    public void doFilter(ServletRequest var1, ServletResponse var2,FilterChain var3) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) var1;
        String uri = request.getRequestURI();
        System.out.println("========================"+uri);
        if (uri.equals("/test")) {
            System.out.println("sadf");
            var3.doFilter(var1,var2);
        }else {
            super.doFilter(var1,var2,var3);
        }
    };

}
