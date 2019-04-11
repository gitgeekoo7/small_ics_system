package com.cesecsh.small_ics_system.web.controller;

import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.service.IDacService;
import com.cesecsh.small_ics_system.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 采控器设置Controller
 */
@RestController
@RequestMapping("/dac")
public class DacController {
    @Autowired
    private IDacService dacService;

    @RequestMapping("/save")
    public Result saveDac(@RequestBody TbDac dac) {
        dacService.saveDac(dac);
        return Result.success();
    }

   /* @RequestMapping("delete")
    public Result deleteIcs(@RequestBody TbIcsVo ics) {
        icsService.deleteIcs(ics.getId());
        return Result.success();
    }

    @RequestMapping("update")
    public Result updateIcs(@RequestBody TbIcsVo ics) throws Exception {
        icsService.updateIcs(ics);
        return Result.success();
    }

    @RequestMapping("list")
    @Transactional(readOnly = true)
    public Result listIcs(@RequestBody IcsQueryObject queryObject) {
        PageInfo<TbIcs> pageInfo = icsService.listIcs(queryObject);
        return Result.success(pageInfo);
    }

    @RequestMapping("get")
    @Transactional(readOnly = true)
    public Result getIcs(@RequestBody IcsQueryObject queryObject) {
        TbIcs ics = icsService.getIcs(queryObject.getId());
        return Result.success(ics);
    }*/
}
