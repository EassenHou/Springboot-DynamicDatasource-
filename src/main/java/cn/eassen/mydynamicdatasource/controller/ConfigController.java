package cn.eassen.mydynamicdatasource.controller;

import cn.eassen.mydynamicdatasource.entity.AaaConfig;
import cn.eassen.mydynamicdatasource.service.AaaConfigService;
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
@RequestMapping("/config")
public class ConfigController {


    @Autowired
    private AaaConfigService aaaConfigService;

    @RequestMapping(value = "/select/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<AaaConfig>> select (@PathVariable String centerId){
        System.out.println("请求中");
        return ResponseEntity.ok(aaaConfigService.queryByCenterId(centerId));
    }

    @RequestMapping(value = "/delete/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> delete (@PathVariable String centerId){
        boolean aaaConfig = aaaConfigService.deleteByCenterId(centerId);
        return ResponseEntity.ok(aaaConfig);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<AaaConfig> update (@RequestBody AaaConfig bean){
        AaaConfig aaaConfig = aaaConfigService.update(bean);
        return ResponseEntity.ok(aaaConfig);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<AaaConfig> insert (@RequestBody AaaConfig bean){
        AaaConfig aaaConfig = aaaConfigService.insert(bean);
        return ResponseEntity.ok(aaaConfig);
    }
}
