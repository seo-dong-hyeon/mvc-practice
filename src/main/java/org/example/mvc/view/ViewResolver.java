package org.example.mvc.view;

// ViewResolver
// view name -> view를 결정
public interface ViewResolver {
    View resolveView(String viewName);
}
