package com.quickwebapp.weixin.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quickwebapp.framework.core.exception.BusinessException;
import com.quickwebapp.framework.core.utils.BeanUtil;
import com.quickwebapp.weixin.autoconfigure.WeixinProperties;
import com.quickwebapp.weixin.entity.Message;
import com.quickwebapp.weixin.exception.ServiceNotFoundException;
import com.quickwebapp.weixin.service.MessageService;
import com.quickwebapp.weixin.service.UserInfoService;

/**
 * 提供给微信调用的http服务基类
 * 
 * @author 袁进勇
 *
 */
@RestController
@RequestMapping(value = "/api/weixin")
public class WeixinController {
    private static final Logger LOG = LoggerFactory.getLogger(WeixinController.class);

    @Autowired
    private WeixinProperties properties;
    @Resource
    private UserInfoService userInfoService;

    /**
     * 处理微信服务器发来的消息
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping(method = RequestMethod.POST)
    public void doMessage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DocumentException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 获取请求消息
        Message reqMsg = $message(request);
        LOG.debug("收到消息：{}", reqMsg.toString());

        try {
            Message respMsg = processMessage(reqMsg);
            LOG.debug("返回消息：{}", respMsg);

            // 返回响应消息
            response(response, respMsg);
            return;
        } catch (ServiceNotFoundException e) {
            LOG.warn(e.getFormattedMessage());
        } catch (BusinessException e) {
            LOG.error(e.getFormattedMessage(), e);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        response(response, (Message) null);
    }

    private Message $message(HttpServletRequest request) throws IOException, DocumentException {
        // 获取微信发过来的XML文档
        InputStream inputStream = request.getInputStream();
        Document document = new SAXReader().read(inputStream);
        // 释放资源
        inputStream.close();
        inputStream = null;

        // 把文档解析成消息对象
        Message message = new Message();
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            message.put(element.getName(), element.getText());
        }

        return message;
    }

    private Message processMessage(Message reqMsg) {
        MessageService messageService = BeanUtil.getBean(MessageService.class, false);
        if (messageService == null) {
            LOG.warn("未配置消息的Service处理类，忽略收到的消息。如需处理，请添加一个实现了{}接口的Java Bean，并注册到Spring Bean容器中。",
                    MessageService.class.getName());
            throw new ServiceNotFoundException(
                    "未配置消息的Service处理类，忽略收到的消息。如需处理，请添加一个实现了{}接口的Java Bean，并注册到Spring Bean容器中。",
                    MessageService.class.getName());
        }

        return messageService.oMessage(reqMsg);
    }

    private void response(HttpServletResponse response, Message message) throws IOException {
        response(response, message == null ? null : message.toXML());
    }

    private void response(HttpServletResponse response, String text) throws IOException {
        PrintWriter out = response.getWriter();
        if (text != null) {
            out.println(text);
            out.flush();
        }
        out.close();
        out = null;
    }

    /**
     * 确认请求来自微信服务器
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    public void doSignature(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature"); // 微信加密签名
        String timestamp = request.getParameter("timestamp"); // 时间戳
        String nonce = request.getParameter("nonce"); // 随机数
        String echostr = request.getParameter("echostr"); // 随机字符串

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        String result = checkSignature(signature, timestamp, nonce, echostr);
        response(response, result);
    }

    /**
     * 验证签名
     * 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
     * 
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    private String checkSignature(String signature, String timestamp, String nonce, String echostr) {
        LOG.debug("检验signature：{signature:\"{}\", timestamp:{}, nonce:{}, echostr:\"{}\"}。", signature, timestamp,
                nonce, echostr);

        // 将token、timestamp、nonce三个参数进行字典序排序后拼接成一个字符串进行sha1加密
        String[] strs = new String[] { properties.getToken(), timestamp, nonce };
        Arrays.sort(strs);
        StringBuilder content = new StringBuilder();
        for (String str : strs) {
            content.append(str);
        }

        try {
            String digest = byteToStr(MessageDigest.getInstance("SHA-1").digest(content.toString().getBytes()));
            if (signature.toUpperCase().equals(digest)) {
                return echostr;
            }
        } catch (NoSuchAlgorithmException e) {
            LOG.error("检验signature异常：", e);
        }

        return null;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * 
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] bytes) {
        StringBuilder digest = new StringBuilder();
        for (byte b : bytes) {
            digest.append(byteToHexStr(b));
        }
        return digest.toString();
    }

    /**
     * 将字节转换为十六进制字符串
     * 
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte b) {
        char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] arr = new char[2];
        arr[0] = digit[(b >>> 4) & 0X0F];
        arr[1] = digit[b & 0X0F];

        return new String(arr);
    }
}
