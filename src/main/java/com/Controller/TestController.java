package com.Controller;

import com.dao.SheepMapper;
import com.entity.Sheep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 */
@Controller
public class TestController {
    @Autowired
    SheepMapper sheepMapper;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "ok";
    }

    @RequestMapping("/get")
    @ResponseBody
    public List<Sheep> get(int age) {
        List<Sheep> list = sheepMapper.getByAge(age);

        return list;
    }

    @RequestMapping("/goto")
    @ResponseBody
    public int geto(int age) {
        List<Sheep> list = sheepMapper.getByAge(age);

        return list.size();
    }

}
