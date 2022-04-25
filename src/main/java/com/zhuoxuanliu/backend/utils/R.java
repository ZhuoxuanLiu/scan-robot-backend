package com.zhuoxuanliu.backend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {
    private Boolean flag;
    private Object data;
    private String msg;

    public R(Boolean flag){
        this.flag = flag;
    }

    public R(Boolean flag, Object data){
        this.flag = flag;
        this.data = data;
    }

    public R(String msg){
        this.flag = false;
        this.msg = msg;
    }

}
