package com.yyicbc.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.yyicbc.beans.JsonUtils;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/*
 * @author stevenHou
 * @date 2020/10/12 : 15:13
 * @class MyZuulFilter 
 * @description 
 */
//@Component
public class MyZuulFilter extends ZuulFilter {


//    private static Logger log = LoggerFactory.getLogger(MyZuulFilter.class);

    @Autowired
    JwtUtil jwtUtil;


    @Override
    public String filterType() {
        //表示在之前过滤，一般都是之前，之后是post
        return "pre";
    }

    @Override
    public int filterOrder() {
        //过滤器执行顺序，0优先级最高
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //当前过滤器是否开启，true开启，false不开启
        return true;
    }

   /* 执行的内容
   返回任何值都继续执行
   加上
   RequestContext requestContext = RequestContext.getCurrentContext();
   requestContext.setSendZuulResponse(false);
   表示不再往下执行
    */
    @Override
    public Object run() throws ZuulException {
        {
            RequestContext requestContext=RequestContext.getCurrentContext();
            HttpServletRequest request = requestContext.getRequest();
            // 1.通过request获取请求token信息
            String authorization = request.getHeader("Authorization");
            //判断请求头信息是否为空，或者是否已Bearer开头
            if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
                //获取token数据
                String token = authorization.replace("Bearer ", "");
                //解析token获取claims

                Claims claims = jwtUtil.parseJWT(token);
                if (claims != null) {
                    //通过claims获取到当前用户的可访问API权限字符串
                    String menus = (String) claims.get("menus");
//                    List<String> menulist = JsonUtils.jsonToList(menus, String.class);
                    String role = (String) claims.get("role");
                    //判断当前用户是否具有响应的请求权限
                    if("admin".equals(role)){
                        request.setAttribute("user_claims", claims);
                        return null;
                    }else{
                        request.setAttribute("user_claims", claims);
                        return null;
                    }
                }
            }
            {
                requestContext.setSendZuulResponse(false);  //进行拦截
                requestContext.setResponseStatusCode(401);
                try {
                    RetData build = RetData.build(StatusCode.TOKENEXPIRE, "token已过期，请重新登录");
                    requestContext.getResponse().getWriter().write(build.toString());
                } catch (Exception e) {
                }
                return null;
            }

        }
    }
}