package filter;

import javax.servlet.*;
import java.io.IOException;

public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig var1) throws ServletException{
        System.out.println();
    };

    @Override
    public void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException {
        var3.doFilter(var1,var2);
        System.out.println("TestFilter");
    };

    @Override
    public void destroy(){

    };


}
