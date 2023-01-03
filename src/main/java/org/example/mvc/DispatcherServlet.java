package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 프런트 컨트롤러 패턴
// 모든 요청을 단일 handler(처리기)에서 처리
// 모든 요청 -> DispatcherServlet -> controller1, controller2, ...
@WebServlet("/") // 어떠한 경로로 요청이 오더라도 해당 서블릿이 실행
// Servlet -> 톰캣이 싱글톤으로 객체로 만들어 실행
public class DispatcherServlet extends HttpServlet {

    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    // 톰캣이 서블릿 객체를 만들 때 호출
    @Override
    public void init() throws ServletException {
        requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("[DispatcherServlet] service started.");

        try {
            // 요청된 request Method와 경로에서 컨트롤러를 가져옴
            Controller handler = requestMappingHandlerMapping.findHandler(new HandlerKey(RequestMethod.valueOf(request.getMethod()), request.getRequestURI()));
            String viewName = handler.handleRequest(request, response);

            // forward 요청 -> 해당 뷰로 전달
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            logger.error("exception occurred: [{}]", e.getMessage(), e);
            throw new ServletException();
        }
    }
}
