package com.youngsun.authority.service;


import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by 国平 on 2016/10/22.
 */
public interface BasicService<T> {
    /**
     * 获取所有记录。
     * @return
     */
    List<T> getAll();

    /**
     * 根据分页信息获取所有记录.
     * @param page
     * @param rows
     * @return
     */
    List<T> getAll(int page, int rows);

    /**
     * 保存单条记录.
     * @param t
     * @return
     */
    Integer save(T t);

    /**
     * 保存多条记录.
     * @param t
     * @return
     */
    Integer save(List<T> t);

    /**
     * 根据Id查询单条记录.
     * @param id
     * @return
     */
    T getById(String id);

    /**
     * 根据传入的对象查询一条记录，SQL语句where条件只能是"="
     * @param t
     * @return
     */
    T getOne(T t);

    /**
     * 根据传入的条件查询记录，SQL语句where条件可以是"=","like"等等.
     * @param example
     * @return
     */
    List<T> getAllByExample(Example example);

    /**
     * 根据传入的对象查询多条记录，SQL语句where条件只能是"="
     * @param t
     * @return
     */
    List<T> getAllByObject(T t);

    /**
     * 删除传入参数对应的记录.
     * @param t
     */
    void delete(T t);

    /**
     * 删除传入条件对应的记录.
     * @param example
     */
    void delete(Example example);

}
