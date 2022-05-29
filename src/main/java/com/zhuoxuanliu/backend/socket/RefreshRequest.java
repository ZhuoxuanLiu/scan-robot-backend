package com.zhuoxuanliu.backend.socket;

import lombok.Data;

@Data
public class RefreshRequest {
    /**
     * 是否需要刷新请求
     */
    private Integer refresh;

    public RefreshRequest (Integer in) {
        refresh = in;
    }
}
