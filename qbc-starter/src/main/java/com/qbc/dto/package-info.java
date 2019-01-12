/**
 * 数据传输对象，Service 或 Manager 向外传输的对象。<br>
 * 不同于数据访问层的DO对象与DAO一一对应，DTO可能会重用于不同的Service 或 Manager <br>
 * 同时一个Service 或 Manager 可能会使用多个DTO，所以放在单独的一个包下面管理。
 * 
 * @author Ma
 */
package com.qbc.dto;