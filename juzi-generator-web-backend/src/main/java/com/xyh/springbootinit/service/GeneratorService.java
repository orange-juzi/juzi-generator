package com.xyh.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.springbootinit.model.dto.generator.GeneratorQueryRequest;
import com.xyh.springbootinit.model.entity.Generator;
import com.xyh.springbootinit.model.vo.GeneratorVO;

/**
 * 生成器服务
 *
 */
public interface GeneratorService extends IService<Generator> {

    /**
     * 校验
     *
     * @param Generator
     * @param add
     */
    void validGenerator(Generator Generator, boolean add);

    /**
     * 获取查询条件
     *
     * @param GeneratorQueryRequest
     * @return
     */
    QueryWrapper<Generator> getQueryWrapper(GeneratorQueryRequest GeneratorQueryRequest);

    /**
     * 获取生成器封装
     *
     * @param Generator
     * @return
     */
    GeneratorVO getGeneratorVO(Generator Generator);

    /**
     * 分页获取生成器封装
     *
     * @param GeneratorPage
     * @return
     */
    Page<GeneratorVO> getGeneratorVOPage(Page<Generator> GeneratorPage);
}
