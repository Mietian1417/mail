package com.xiyu.mail;

import com.xiyu.mail.util.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
class MailApplicationTests {

    @Test
    public void mailTest() {
        MailUtil.sendSimpleEmail("cczihao@yeah.net", "测试邮件", "这是一封来自 QQ 邮箱的测试邮件");
    }

    @Test
    public void testSendHtmlEmail() {
        String htmlContent = """
                <html>
                    <body>
                        <h1>测试 HTML 邮件</h1>
                        <p>这是一封来自 QQ 邮箱的 <b>HTML 测试邮件</b>。</p>
                    </body>
                </html>
                """;
        MailUtil.sendHtmlEmail("cczihao@yeah.net", "测试 HTML 邮件", htmlContent, null, null);
    }

    @Test
    public void testSendHtmlEmailWithAttachment() {
        String htmlContent = """
                <html>
                    <body>
                        <h1>测试 HTML 邮件（带附件）</h1>
                        <p>这是一封来自 QQ 邮箱的 <b>HTML 测试邮件</b>，包含一个附件。</p>
                    </body>
                </html>
                """;
        byte[] attachment = "这是一个测试附件内容".getBytes();
        String attachmentName = "test.txt";
        MailUtil.sendHtmlEmail("cczihao@yeah.net", "测试 HTML 邮件（带附件）", htmlContent, attachment, attachmentName);
    }

    @Test
    public void testSendHtmlEmailWithLocalFile() throws Exception {
        String htmlContent = """
                <html>
                    <body>
                        <h1>测试 HTML 邮件（本地文件附件）</h1>
                        <p>这是一封来自 QQ 邮箱的 <b>HTML 测试邮件</b>，包含本地图片附件。</p>
                    </body>
                </html>
                """;
        Path filePath = Path.of("F:\\电脑壁纸\\朝花夕拾.jpg");
        byte[] attachment = Files.readAllBytes(filePath);
        String attachmentName = "朝花夕拾.jpg";
        MailUtil.sendHtmlEmail("cczihao@yeah.net", "测试 HTML 邮件（本地文件）", htmlContent, attachment, attachmentName);
    }

}
