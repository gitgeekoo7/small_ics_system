package com.cesecsh.small_ics_system.web.controller;

import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.query.IcsQueryObject;
import com.cesecsh.small_ics_system.service.IIcsService;
import com.cesecsh.small_ics_system.util.Result;
import com.cesecsh.small_ics_system.vo.TbIcsVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ics")
public class IcsController {
    @Autowired
    private IIcsService icsService;

    @RequestMapping("save")
    public Result saveIcs(@RequestBody TbIcs ics) {
        icsService.saveIcs(ics);
        return Result.success();
    }

    @RequestMapping("delete")
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
    }
}
