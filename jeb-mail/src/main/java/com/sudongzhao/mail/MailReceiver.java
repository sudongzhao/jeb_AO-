package com.sudongzhao.mail;

import com.rabbitmq.client.Channel;
import com.sudongzhao.server.pojo.Employee;
import com.sudongzhao.server.pojo.MailConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * 邮件接收者
 */
@Component
public class MailReceiver {

    private static final Logger LOGGER= LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel) {
//        MimeMessage msg = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(msg);
//        try {
//            //发件人
//            mimeMessageHelper.setFrom(mailProperties.getUsername());
//            //收件人
//            mimeMessageHelper.setTo(employee.getEmail());
//            //主题
//            mimeMessageHelper.setSubject("入职欢迎邮件");
//            //发送日期
//            mimeMessageHelper.setSentDate(new Date());
//            //邮件内容
//            Context context = new Context();
//            context.setVariable("name",employee.getName());
//            context.setVariable("posName",employee.getPosition().getName());
//            context.setVariable("jobLevelName",employee.getJoblevel().getName());
//            context.setVariable("departmentName",employee.getDepartment().getName());
//            String mail = templateEngine.process("mail", context);
//            mimeMessageHelper.setText(mail,true);
//            //发送
//            javaMailSender.send(msg);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
        Employee employee = (Employee) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        //消息序号
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String) headers.get("spring_returned_message_correlation");

        HashOperations hashOperations = redisTemplate.opsForHash();


        try {
            if (hashOperations.entries("mail_log").containsKey(msgId)){
                LOGGER.error("消息已经被消费====>{}",msgId);
                /**
                 * 手动确认消息
                 */
                channel.basicAck(tag,false);
                return;
            }
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            //发件人
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            //收件人
            mimeMessageHelper.setTo(employee.getEmail());
            //主题
            mimeMessageHelper.setSubject("入职文件");
            //发送日期
            mimeMessageHelper.setSentDate(new Date());

            //邮件内容
            Context context = new Context();
            context.setVariable("name", employee.getName());
            context.setVariable("posName", employee.getPosition().getName());
            context.setVariable("joblevelName", employee.getJoblevel().getName());
            context.setVariable("departmentName", employee.getDepartment().getName());
            //将数据导入模板
            String mail = templateEngine.process("mail", context);
            //设置发送文本
            mimeMessageHelper.setText(mail,true);
            //发送邮件
            javaMailSender.send(mimeMessage);
            //将id存入redis
            hashOperations.put("mail_log",msgId,"ok");
            //手动确认信息
            channel.basicAck(tag,false);
        } catch (Exception e) {
            try {
                channel.basicNack(tag,false,true);
            } catch (IOException ex) {
                ex.printStackTrace();
                LOGGER.error("邮件发送失败=======>{}",e.getMessage());
            }
            e.printStackTrace();
            LOGGER.error("邮件发送失败========>{}",e.getMessage());
        }
    }

}
