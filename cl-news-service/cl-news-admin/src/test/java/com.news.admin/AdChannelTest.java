package com.news.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cl.common.dto.PageResponseResult;
import com.cl.common.dto.ResponseResult;
import com.news.admin.entity.AdChannel;
import com.news.admin.entity.AdUser;
import com.news.admin.entity.person;
import com.news.admin.service.IAdChannelService;
import com.news.admin.service.IAdUserService;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.sql.SQLOutput;
import java.util.*;

@SpringBootTest
public class AdChannelTest {

    @Autowired
    private IAdChannelService channelService;
    @Autowired
    private IAdUserService adUserService;
    @Test
    public void findall(){
        List<AdChannel> channels = channelService.list();

        for (AdChannel channel:channels) {
            System.out.println(channel);

        }
    }
    @Test
    public void getById(){
        AdChannel channel = channelService.getById(1);
        System.out.println(channel);

    }
    @Test
    public void l(){
        ResponseResult responseResult = new ResponseResult();
        System.out.println(responseResult);
    }

    /*public static void main(String[] args) {
        Data data = new Data(''2022-4-9:);
        System.out.println(System.currentTimeMillis());
    }*/
    @Test
    public void l1(){
        person person = new person();
        String[] d={"1"};
        char t='2';
        int[] ints = new int[8];
        person.setName("jack");
        person.setAge(12);
        Map<String, Object> map = new HashMap<>();
        map.put("user",person);
        map.put("token","yy");
        Set<String> strings = map.keySet();
        for (String string : strings) {
            System.out.println(map.get(string));
        }
        System.out.println(map);
        List<String> b = new ArrayList<>();
        b.add("1");
        b.add("2");
        System.out.println(b);
       List<Object> c=new ArrayList<>();
       c.add(person);
       c.add(map);
        System.out.println(c);
        System.out.println(d);
        }

        /*
        {user=person(name=jack, age=12), token=yy}
        [1, 2]
        [person(name=jack, age=12), {user=person(name=jack, age=12), token=yy}]
        [Ljava.lang.String;@2ef041bb
        */

    @Test
    public void list(){

        LambdaQueryWrapper<AdChannel> query=new LambdaQueryWrapper<>();
        query.eq(AdChannel::getName,"java++");
        List<AdChannel> list = channelService.list(query);
        for (AdChannel object : list) {

            System.out.println(object.getName());
        }
    }
    @Test
    public void list2(){
        int[] ints = {1, 2, 2};
        char[] a={1,1,1};
        String[] b={"1"};
        String c="d";
        c="d";
        StringBuffer d=new StringBuffer();
        d.append("d");
        System.out.println(ints);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }
    @Test
    public void t(){
        char t='f';
        System.out.println(t);
    }
}
