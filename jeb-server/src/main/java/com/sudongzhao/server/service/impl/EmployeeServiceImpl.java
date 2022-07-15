package com.sudongzhao.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sudongzhao.server.mapper.EmployeeMapper;
import com.sudongzhao.server.mapper.MailLogMapper;
import com.sudongzhao.server.pojo.*;
import com.sudongzhao.server.service.IEmployeeService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.PrinterAbortException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sudongzhao
 * @since 2022-07-04
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailLogMapper mailLogMapper;

    @Override
    public RPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        RPageBean rPageBean = new RPageBean(employeeByPage.getTotal(), employeeByPage.getRecords());
        return rPageBean;
    }

    @Override
    public ResponseBean getMaxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        //取出工号
        int i = Integer.parseInt(maps.get(0).get("max(workID)").toString())+1;
        return ResponseBean.success(null
        ,String.format("%08d",i));
    }

    @Override
    public ResponseBean addEmp(Employee employee) {
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        //格式化数据
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        //设置合同期限
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365)));
        if (1==employeeMapper.insert(employee)){
            //发送邮件
//            List<Employee> employee1 = employeeMapper.getEmployee(employee.getId());
//            rabbitTemplate.convertAndSend("mail.welcome",employee1);
            //向mailLog表插入数据
            List<Employee> employee1 = employeeMapper.getEmployee(employee.getId());
            Employee employee2 = employee1.get(0);
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEId(employee2.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);

            //发送数据
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,employee2,new CorrelationData(msgId));
            return ResponseBean.success("添加成功！");
        }
        return ResponseBean.error("添加失败！");
    }

    @Override
    public List<Employee> getEmployee(Integer eid) {
        return employeeMapper.getEmployee(eid);
    }
}
