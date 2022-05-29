package com.zhuoxuanliu.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class InfoCard {
    private Integer id;
    private Integer status;
    private Integer currentBook;
    private Integer runTime;
    private String time;
}
