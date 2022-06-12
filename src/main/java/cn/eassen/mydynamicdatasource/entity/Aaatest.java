package cn.eassen.mydynamicdatasource.entity;


import lombok.Data;

import java.util.List;

/**
 * (Aaatest)实体类
 *
 * @author Auto
 * @since 2022-05-30 16:36:31
 */
@Data
public class Aaatest{

    private Integer id;
    
    private String name;

    private String centerId;

    List<AaaSubTest> subs;
}

