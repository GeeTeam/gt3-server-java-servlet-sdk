package com.geetest.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.geetest.sdk.GeetestLib;
import com.geetest.sdk.GeetestLibResult;

/**
 * 二次验证接口，POST请求
 */
public class SecondValidateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        GeetestLib gtLib = new GeetestLib(GeetestConfig.GEETEST_ID, GeetestConfig.GEETEST_KEY);
        String challenge = request.getParameter(GeetestLib.GEETEST_CHALLENGE);
        String validate = request.getParameter(GeetestLib.GEETEST_VALIDATE);
        String seccode = request.getParameter(GeetestLib.GEETEST_SECCODE);
        PrintWriter out = response.getWriter();
        int status = 0;
        String userId = "";
        try {
            // session必须取出值，若取不出值，直接当做异常退出
            status = (Integer) request.getSession().getAttribute(GeetestLib.GEETEST_SERVER_STATUS_SESSION_KEY);
            userId = (String) request.getSession().getAttribute("userId");
        } catch (Exception e) {
            out.println(String.format("{\"result\":\"fail\",\"version\":\"%s\",\"msg\":\"session取key发生异常\"}", GeetestLib.VERSION));
            return;
        }
        GeetestLibResult result = null;
        if (status == 1) {
            /*
            自定义参数,可选择添加
                user_id 客户端用户的唯一标识，确定用户的唯一性；作用于提供进阶数据分析服务，可在register和validate接口传入，不传入也不影响验证服务的使用；若担心用户信息风险，可作预处理(如哈希处理)再提供到极验
                client_type 客户端类型，web：电脑上的浏览器；h5：手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生sdk植入app应用的方式；unknown：未知
                ip_address 客户端请求sdk服务器的ip地址
            */
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("user_id", userId);
            paramMap.put("client_type", "web");
            paramMap.put("ip_address", "127.0.0.1");
            result = gtLib.successValidate(challenge, validate, seccode, paramMap);
        } else {
            result = gtLib.failValidate(challenge, validate, seccode);
        }
        // 注意，不要更改返回的结构和值类型
        if (result.getStatus() == 1) {
            out.println(String.format("{\"result\":\"success\",\"version\":\"%s\"}", GeetestLib.VERSION));
        } else {
            out.println(String.format("{\"result\":\"fail\",\"version\":\"%s\",\"msg\":\"%s\"}", GeetestLib.VERSION, result.getMsg()));
        }
    }
}
