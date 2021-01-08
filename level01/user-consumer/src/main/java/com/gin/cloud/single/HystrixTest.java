package com.gin.cloud.single;

import java.util.concurrent.Future;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author gin
 * Hystrix 通过信号量或者线程模式进行限流控制
 * 通过包装feign或者rest template来提供降级服务(最终就是cglib动态代理的http请求)
 */
public class HystrixTest extends HystrixCommand {

    private HystrixTest(HystrixCommandGroupKey group) {
        super(group);
    }

    public static void main(String[] args) {


        //	HystrixTest hystrixTest = new HystrixTest(HystrixCommandGroupKey.Factory.asKey("ext"));
        /*
         * execute()：以同步阻塞方式执行run()。以demo为例，调用execute()后，
         * hystrix先创建一个新线程运行run()，
         * 	接着调用程序要在execute()调用处一直阻塞着，直到run()运行完成
         */
        //	System.out.println("result:" + hystrixTest.execute());

        /*
         * queue()：以异步非阻塞方式执行run()。以demo为例，
         * 	一调用queue()就直接返回一个Future对象，
         * 	同时hystrix创建一个新线程运行run()，
         * 	调用程序通过Future.get()拿到run()的返回结果，
         * 	而Future.get()是阻塞执行的
         */
        Future<String> futureResult = new HystrixTest(HystrixCommandGroupKey.Factory.asKey("ext")).queue();
        String result = "";
        try {
            result = futureResult.get();
        } catch (Exception e) {
            System.out.println("业务执行异常: " + e);
        }

        System.out.println("程序结果：" + result);
    }

    @Override
    protected Object run() throws Exception {
        // 类似 try 操作
        System.out.println("执行逻辑");

        // 正常执行
        //int i = 1/1;

        // 异常执行
        int i = 1 / 0;
        return "ok";
    }

    @Override
    protected Object getFallback() {
        // 类似 catch 操作(备用逻辑)
        return "getFallbackgetFallback";
    }


}