package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.RequestMethod;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.View;
import org.example.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

// 프런트 컨트롤러 패턴
// 모든 요청을 단일 handler(처리기)에서 처리
// 모든 요청 -> DispatcherServlet -> controller1, controller2, ...
@WebServlet("/") // 어떠한 경로로 요청이 오더라도 해당 서블릿이 실행
// Servlet -> 톰캣이 싱글톤으로 객체로 만들어 실행
public class DispatcherServlet extends HttpServlet {

    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;

    // 톰캣이 서블릿 객체를 만들 때 호출
    @Override
    public void init() throws ServletException {
        requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.init();

        handlerAdapters = List.of(new SimpleControllerHandlerAdapter());
        // 일단 하나만 추가
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("[DispatcherServlet] service started.");

        try {
            // 요청된 request Method와 경로에서 컨트롤러(handler)를 가져옴
            Controller handler = requestMappingHandlerMapping.findHandler(new HandlerKey(RequestMethod.valueOf(request.getMethod()), request.getRequestURI()));

            // handlerAdapter로 해당 handler를 지원하는 adapter를 찾아서 handler 전달
            // adpater 내부에서 해당 handler 실행
            // controller에서 viewName을 가져옴
            // 최종 결과 model and view를 리턴 받음
            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(ha -> ha.supports(handler))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No adapter for handler [" + handler + "]"));

            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);

            // rendering
            // UserCreateController -> return viewName = redirect:/users
            // forward vs redirect 구분 필요
            for (ViewResolver viewResolver : viewResolvers){
                View view = viewResolver.resolveView(modelAndView.getViewName());
                view.render(modelAndView.getModel(), request, response);
            }
        } catch (Exception e) {
            logger.error("exception occurred: [{}]", e.getMessage(), e);
            throw new ServletException();
        }
    }
}
