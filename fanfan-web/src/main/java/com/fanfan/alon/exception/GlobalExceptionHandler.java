package com.fanfan.alon.exception;

import com.fanfan.alon.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    // 打印log
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 功能描述:缺少请求参数异常
     * @param:  ex HttpMessageNotReadableException
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   14:24
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public JsonResult handleHttpMessageNotReadableException(
            MissingServletRequestParameterException ex) {
        logger.error("缺少请求参数，{}", ex.getMessage());
        return new JsonResult("400", "缺少必要的请求参数");
    }

    /**
     * 功能描述:空指针异常
     * @param: ex NullPointerException
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   14:24
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult handleTypeMismatchException(NullPointerException ex) {
        logger.error("空指针异常，{}", ex.getMessage());
        return new JsonResult("500", "空指针异常了");
    }

    /**
     * 功能描述:系统异常 预期以外异常
     * @param: ex
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   14:46
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult handleUnexpectedServer(Exception ex) {
        logger.error("系统异常：", ex);
        return new JsonResult("500", "系统发生异常，请联系管理员");
    }

    /**
     * 功能描述:拦截业务异常，返回业务异常信息
     * @param:  ex
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   16:22
     */
    @ExceptionHandler(BusinessErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult handleBusinessError(BusinessErrorException ex) {
        String code = ex.getCode();
        String message = ex.getMessage();
        return new JsonResult(code, message);
    }
}
