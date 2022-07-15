package com.sudongzhao.server.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sudongzhao.server.pojo.MailConstants;
import com.sudongzhao.server.pojo.MailLog;
import com.sudongzhao.server.service.IMailLogService;
import lombok.experimental.PackagePrivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;



/**
 * RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {

    //日志配置
    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitMQConfig.class);

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    private IMailLogService iMailLogService;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        /**
         * 信息确认回调
         */
        rabbitTemplate.setConfirmCallback((data,ack,cause)->{
            String msgId=data.getId();
            if (ack){
                LOGGER.info("{}====>消息发送成功",msgId);
                iMailLogService.update(new UpdateWrapper<MailLog>().set("status",1).eq("msgId",msgId));
            }else{
                LOGGER.error("{}=====>消息发送失败",msgId);
            }
        });

        /**
         * 消息失败回调，比如router不到queue时回调
         * msg:消息主题
         * repCode:相应码
         * repText:相应描述
         * exchange:交换机
         * routingkey:路由键
         */
        rabbitTemplate.setReturnCallback(
                (msg,repCode,repText,exchanage,routingkey)->{
                    LOGGER.error("{}====>消息发送到request时失败",msg.getBody());
                }
        );
        return rabbitTemplate;
    }

    /**
     * 队列bean
     */
    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }

    /**
     * 交换机的名字
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
    }

    /**
     * 绑定队列到交换机上带有路由键
     */
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange())
                .with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }

}
