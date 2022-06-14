package cn.eassen.mydynamicdatasource.controller;

import cn.eassen.mydynamicdatasource.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author eassen
 * @Create 2022/6/9 16:58
 */
@RestController
public class RouteController {

    @Autowired
    InitService initService;

    @RequestMapping(value = "/removeRouteCache", method = RequestMethod.GET)
    public void removeRouteCache(@RequestParam String centerId){
        if(StringUtils.isEmpty(centerId)){
            centerId = null;
        }
        initService.reloadShardingRouteMap(centerId);
    }
}
