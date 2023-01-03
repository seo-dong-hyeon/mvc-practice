package org.example.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

// redirect 방식
public class RedirectView implements View{
    public static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private final String name;

    public RedirectView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // redirect: 이후 경로만 리턴
        response.sendRedirect(name.substring(DEFAULT_REDIRECT_PREFIX.length()));
    }
}
