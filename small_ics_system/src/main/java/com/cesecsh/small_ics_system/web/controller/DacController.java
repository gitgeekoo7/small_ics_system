package com.cesecsh.small_ics_system.web.controller;

import com.cesecsh.small_ics_system.dto.TbDacDto;
import com.cesecsh.small_ics_system.query.DacQueryObject;
import com.cesecsh.small_ics_system.service.IDacService;
import com.cesecsh.small_ics_system.util.Result;
import com.cesecsh.small_ics_system.vo.TbDacChannelVo;
import com.cesecsh.small_ics_system.vo.TbDacVo;
import com.github.pagehelper.PageInfo;
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

//    @RequestMapping("/save")
//    public Result saveDac(@RequestBody TbDac dac) {
//        dacService.saveDac(dac);
//        return Result.success();
//    }

    @RequestMapping("/delete")
    public Result deleteDac(@RequestBody TbDacVo dac) {
        dacService.deleteDac(dac.getId());
        return Result.success();
    }

    @RequestMapping("/update")
    public Result updateDac(@RequestBody TbDacVo dac) throws Exception {
        dacService.updateDac(dac);
        return Result.success();
    }

    @RequestMapping("/list")
    public Result listDac(@RequestBody DacQueryObject queryObject) {
        PageInfo<TbDacChannelVo> pageInfo = dacService.listDac(queryObject);
        return Result.success(pageInfo);
    }

    @RequestMapping("/get")
    public Result getIcs(@RequestBody DacQueryObject queryObject) throws Exception {
        TbDacDto dac = dacService.getDac(queryObject.getId());
        return Result.success(dac);
    }
}
