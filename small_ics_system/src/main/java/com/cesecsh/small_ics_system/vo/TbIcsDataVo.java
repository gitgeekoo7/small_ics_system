package com.cesecsh.small_ics_system.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class TbIcsDataVo {
    private String id;
    private String icsDataId;
    private String reciveValue;
    private String checkValue;


    private MultipartFile file;
}
