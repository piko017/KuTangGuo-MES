package com.ktg.mes.cal.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ktg.mes.cal.service.ICalPlanTeamService;
import com.ktg.mes.cal.service.ICalShiftService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ktg.common.annotation.Log;
import com.ktg.common.core.controller.BaseController;
import com.ktg.common.core.domain.AjaxResult;
import com.ktg.common.enums.BusinessType;
import com.ktg.mes.cal.domain.CalPlan;
import com.ktg.mes.cal.service.ICalPlanService;
import com.ktg.common.utils.poi.ExcelUtil;
import com.ktg.common.core.page.TableDataInfo;

/**
 * 排班计划Controller
 * 
 * @author yinjinlu
 * @date 2022-06-06
 */
@RestController
@RequestMapping("/mes/cal/calplan")
public class CalPlanController extends BaseController
{
    @Autowired
    private ICalPlanService calPlanService;

    @Autowired
    private ICalShiftService calShiftService;

    @Autowired
    private ICalPlanTeamService calPlanTeamService;

    /**
     * 查询排班计划列表
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(CalPlan calPlan)
    {
        startPage();
        List<CalPlan> list = calPlanService.selectCalPlanList(calPlan);
        return getDataTable(list);
    }

    /**
     * 导出排班计划列表
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:export')")
    @Log(title = "排班计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CalPlan calPlan)
    {
        List<CalPlan> list = calPlanService.selectCalPlanList(calPlan);
        ExcelUtil<CalPlan> util = new ExcelUtil<CalPlan>(CalPlan.class);
        util.exportExcel(response, list, "排班计划数据");
    }

    /**
     * 获取排班计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:query')")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return AjaxResult.success(calPlanService.selectCalPlanByPlanId(planId));
    }

    /**
     * 新增排班计划
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:add')")
    @Log(title = "排班计划", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody CalPlan calPlan)
    {
        int ret = calPlanService.insertCalPlan(calPlan);
        //根据选择的轮班方式生成默认的班次
        calShiftService.addDefaultShift(calPlan.getPlanId(),calPlan.getShiftType());
        return toAjax(ret);
    }

    /**
     * 修改排班计划
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:edit')")
    @Log(title = "排班计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CalPlan calPlan)
    {
        return toAjax(calPlanService.updateCalPlan(calPlan));
    }

    /**
     * 删除排班计划
     */
    @PreAuthorize("@ss.hasPermi('mes:cal:calplan:remove')")
    @Log(title = "排班计划", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        for (Long planId:planIds
             ) {
            calShiftService.deleteByPlanId(planId);
            calPlanTeamService.deleteByPlanId(planId);
        }
        return toAjax(calPlanService.deleteCalPlanByPlanIds(planIds));
    }
}
