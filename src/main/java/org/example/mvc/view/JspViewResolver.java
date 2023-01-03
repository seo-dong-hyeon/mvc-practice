package org.example.mvc.view;

public class JspViewResolver implements ViewResolver{

    @Override
    public View resolveView(String viewName) {
        if (viewName.startsWith(RedirectView.DEFAULT_REDIRECT_PREFIX)){
            return new RedirectView(viewName);
        }
        // Controller 리턴값에서 jsp 필요없음
        return new JspView(viewName + ".jsp");
    }
}
