export interface ApiObj {
    /**
     * 接口名
     */
    apiName?: string;
    /**
     * 接口头像
     */
    avatar?: string;
    /**
     * 费用
     */
    cost?: number;
    /**
     * 创建时间
     */
    createTime?: string;
    /**
     * 创建用户ID
     */
    createUserId?: number;
    /**
     * 描述
     */
    description?: string;
    /**
     * API-ID
     */
    id?: number;
    /**
     * 逻辑删除
     */
    isDelete?: number;
    /**
     * 接口类型
     */
    method?: string;
    /**
     * 请求案例
     */
    requestExample?: string;
    /**
     * 请求头
     */
    requestHeader?: string;
    /**
     * 请求体
     */
    requestParams?: string;
    /**
     * 响应格式
     */
    responseFormat?: string;
    /**
     * 响应体
     */
    responseParams?: string;
    /**
     * 状态 0-下线 1-上线
     */
    status?: number;
    /**
     * 调用次数
     */
    totInvoke?: number;
    /**
     * 更新时间
     */
    updateTime?: string;
    /**
     * 接口地址
     */
    url?: string;
    [property: string]: any;
}