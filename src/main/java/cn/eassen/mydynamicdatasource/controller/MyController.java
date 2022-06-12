package cn.eassen.mydynamicdatasource.controller;

import cn.eassen.mydynamicdatasource.entity.AaaSubTest;
import cn.eassen.mydynamicdatasource.entity.Aaatest;
import cn.eassen.mydynamicdatasource.service.AaatestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author eassen
 * @Create 2022/5/30 16:23
 */
@RestController
public class MyController {


    @Autowired
    private AaatestService aaatestService;

    @RequestMapping(value = "/select/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<Aaatest>> select (@PathVariable String centerId){
        System.out.println("请求中");
        return ResponseEntity.ok(aaatestService.queryByCenterId(centerId));
    }

    @RequestMapping(value = "/select/list", method = RequestMethod.GET)
    public ResponseEntity<List<AaaSubTest>> selectByQueryInfo (@ModelAttribute Aaatest queryInfo){
        return ResponseEntity.ok(aaatestService.queryList(queryInfo));
    }

    @RequestMapping(value = "/select/query", method = RequestMethod.GET)
    public ResponseEntity<List<Aaatest>> query (@ModelAttribute Aaatest queryInfo){
        return ResponseEntity.ok(aaatestService.query(queryInfo));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<Boolean> delete (@RequestBody Aaatest queryInfo){
        boolean aaatest = aaatestService.delete(queryInfo);
        return ResponseEntity.ok(aaatest);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Aaatest> update (@RequestBody Aaatest bean){
        Aaatest aaatest = aaatestService.update(bean);
        return ResponseEntity.ok(aaatest);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<Aaatest> insert (@RequestBody Aaatest bean){
        Aaatest aaatest = aaatestService.insert(bean);
        return ResponseEntity.ok(aaatest);
    }

    @RequestMapping(value = "/login/{token}", method = RequestMethod.GET)
    public ResponseEntity<String> login (@PathVariable String token, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("centerId", token);
        return ResponseEntity.ok("登录成功");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<String> logout (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.removeAttribute("centerId");
        return ResponseEntity.ok("注销成功");
    }
}
