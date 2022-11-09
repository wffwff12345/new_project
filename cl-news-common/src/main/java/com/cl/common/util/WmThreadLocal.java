package com.cl.common.util;

import com.cl.common.dto.User;

public class WmThreadLocal {
    public static ThreadLocal<User> threadLocal=new ThreadLocal<>();
    public static void set(User user){
        threadLocal.set(user);
    }
    public static User get(){
        return threadLocal.get();
    }
    public static void remove(){
        threadLocal.remove();
    }
}
