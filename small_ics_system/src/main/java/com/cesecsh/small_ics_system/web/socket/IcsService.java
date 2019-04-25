package com.cesecsh.small_ics_system.web.socket;

import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.service.IIcsService;
import com.cesecsh.small_ics_system.vo.TbIcsSocketVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IcsService {
    private static IIcsService service;

    @Autowired
    public IcsService(IIcsService service) {
        IcsService.service = service;
    }

    public static boolean saveIcs(TbIcsSocketVo vo) {
        TbIcs ics = new TbIcs();
        ics.setName("新ICS");
        try {
            BeanUtils.copyProperties(ics, vo);
            service.saveIcs(ics);
            return true;
        } catch (Exception e) {
            log.error("ICS信息上传失败", e);
            return false;
        }
    }

    public static boolean saveData(String serial, String value) {
        try {
            service.saveData(serial, value);
            return true;
        } catch (Exception e) {
            log.error("ICS信息上传失败", e);
            return false;
        }
    }
}
