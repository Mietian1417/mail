package com.xiyu.mail.util;

import com.xiyu.mail.config.MailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailUtil {

    private static JavaMailSender javaMailSender;
    private static MailProperties mailProperties;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        MailUtil.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setMailProperties(MailProperties mailProperties) {
        MailUtil.mailProperties = mailProperties;
    }

    /**
     * 发送简单文本邮件
     * @param to      收件人邮箱地址
     * @param subject 邮件主题
     * @param text    邮件正文
     */
    public static void sendSimpleEmail(String to, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false); // false 表示纯文本

            javaMailSender.send(message);
            log.info("发送文本邮件成功 - Host: {}, Port: {}, From: {}, To: {}",
                    mailProperties.getHost(), mailProperties.getPort(), mailProperties.getUsername(), to);
        } catch (MessagingException e) {
            log.error("发送文本邮件失败 - Host: {}, Port: {}, From: {}, To: {}, 异常信息: {}",
                    mailProperties.getHost(), mailProperties.getPort(), mailProperties.getUsername(), to, e.getMessage());
            throw new RuntimeException("邮件发送失败: " + e.getMessage(), e);
        }
    }

    /**
     * 发送 HTML 邮件，可带附件
     * @param to             收件人邮箱地址
     * @param subject       邮件主题
     * @param htmlContent   HTML 邮件内容
     * @param attachment    附件内容（字节数组，可为 null）
     * @param attachmentName 附件文件名（可为 null）
     */
    public static void sendHtmlEmail(String to, String subject, String htmlContent, byte[] attachment, String attachmentName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true 表示 HTML 内容

            if (attachment != null && attachmentName != null) {
                helper.addAttachment(attachmentName, () -> new java.io.ByteArrayInputStream(attachment));
            }

            javaMailSender.send(message);
            log.info("发送 HTML 邮件成功 - Host: {}, Port: {}, From: {}, To: {}, Attachment: {}",
                    mailProperties.getHost(), mailProperties.getPort(), mailProperties.getUsername(), to,
                    attachmentName != null ? attachmentName : "无");
        } catch (MessagingException e) {
            log.error("发送 HTML 邮件失败 - Host: {}, Port: {}, From: {}, To: {}, Attachment: {}, 异常信息: {}",
                    mailProperties.getHost(), mailProperties.getPort(), mailProperties.getUsername(), to,
                    attachmentName != null ? attachmentName : "无", e.getMessage());
            throw new RuntimeException("邮件发送失败: " + e.getMessage(), e);
        }
    }
}