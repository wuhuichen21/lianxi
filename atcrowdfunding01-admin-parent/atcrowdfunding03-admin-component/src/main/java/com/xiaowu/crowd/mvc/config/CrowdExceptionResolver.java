package com.xiaowu.crowd.mvc.config;

import com.google.gson.Gson;
import com.xiaowu.crowd.constant.CrowdConstant;
import com.xiaowu.crowd.exception.AccessForbiddenException;
import com.xiaowu.crowd.exception.LoginAcctAlreadyInUseException;
import com.xiaowu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.xiaowu.crowd.exception.LoginFailedException;
import com.xiaowu.crowd.util.CrowdUtil;
import com.xiaowu.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CrowdExceptionResolver {




//    算数运算异常
    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resoloveMathExecption(ArithmeticException exception, HttpServletRequest request
            , HttpServletResponse response) throws IOException {

        String viewName = "system-error";
        return commonResolve(viewName,exception,request,response);
    }

//    空指针异常
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception,HttpServletRequest request,
                                                    HttpServletResponse response
                                                    ) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName,exception,request,response);
    }

//   登录时异常
    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName =  "admin-login";
        return commonResolve(viewName,exception,request,response);
    }

//  访问受限异常
    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView resolveAccessForbiddenException(AccessForbiddenException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName,exception,request,response);
    }

//   账号已经存在异常
    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception,
                                                              HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        String viewName = "admin-add";
        return commonResolve(viewName,exception,request,response);
    }


    /**
     *  更新管理员对象异常
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException(LoginAcctAlreadyInUseException exception,
                                                              HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName,exception,request,response);
    }


    private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request
            , HttpServletResponse response
    ) throws IOException {
//    1，获取实际的异常类型

//    2，获取当前请求类型
        boolean b = CrowdUtil.judgeRequestType(request);
//        如果是ajax请求，返回全局异常对象
        if (b){
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());
//            创建gson对象转换
            Gson gson = new Gson();
            String s = gson.toJson(failed);

            response.getWriter().write(s);
            return null;
        }


//        如果不是json请求返回视图对象
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);

//        设置对应的视图名称
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

}
