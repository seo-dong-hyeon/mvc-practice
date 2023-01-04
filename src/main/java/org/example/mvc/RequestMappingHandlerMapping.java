package org.example.mvc;

import org.example.mvc.controller.*;

import java.util.HashMap;
import java.util.Map;

// URL path -> Controller
// /user    -> UserController
public class RequestMappingHandlerMapping implements HandlerMapping{
    private Map<HandlerKey, Controller> mappings = new HashMap<>();

    void init(){
        // 요청 -> 처리 -> 이동
//        mappings.put(new HandlerKey(RequestMethod.GET, "/"), new HomeController());
        mappings.put(new HandlerKey(RequestMethod.GET, "/users"), new UserListController());
        mappings.put(new HandlerKey(RequestMethod.POST, "/users"), new UserCreateController());
        // forward -> 해당 요청이 들어온 경로(jsp)로 단순히 이동해라 -> 해당 화면 노출
        mappings.put(new HandlerKey(RequestMethod.POST, "/users/form"), new ForwardController("/user/form"));
    }

    public Controller findHandler(HandlerKey handlerKey){
        return mappings.get(handlerKey);
    }
}
