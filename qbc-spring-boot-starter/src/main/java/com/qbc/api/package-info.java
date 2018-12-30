/**
 * 开放接口层：<br>
 * 可直接封装 Service 方法暴露成 RPC 接口；<br>
 * 通过 Web 封装成 http 接口；<br>
 * 进行 网关安全控制、流量控制等。<br>
 * 1.所有接口都必须返回ResultBean。<br>
 * 2.一开始就要考虑成功和失败二种场景。<br>
 * 3.ResultBean只允许出现在controller，不允许到处传。<br>
 * 4.不要出现 map ，json 等复杂对象做为输入和输出。<br>
 * 5.不要出现 local ，messagesource 等和业务无关的参数。<br>
 * 
 * @author Ma
 */
package com.qbc.api;