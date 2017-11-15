package com.premain.demo.controller;

import com.premain.demo.service.SayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhongjing on 2017/11/08.
 */
@RestController
@RequestMapping(value = "/")
public class SayController {

    @Autowired
    private SayService sayService;
    
    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    public void sayHello(){
        System.out.println("sayHello");
    }

    @RequestMapping(value = "/sayHello2/{name}", method = RequestMethod.GET)
    public void sayHello2(@PathVariable("name") String name){
        sayService.sayHello();
    }


    @RequestMapping(value = "/save/{name}", method = RequestMethod.GET)
    public void save(@PathVariable("name") String name){
        sayService.redisInsert(name);
    }


    @RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
    public String getName(@PathVariable("name") String name){
        return sayService.getName(name);
    }
}
