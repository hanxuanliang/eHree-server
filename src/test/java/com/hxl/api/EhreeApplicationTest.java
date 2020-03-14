package com.hxl.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 基本 测试类
 * @Author: hanxuanliang
 * @Date: 2020/2/16 19:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EhreeApplicationTest {

    @Test
    public void test() {
        List<String> list = new ArrayList<>(2);

        System.out.println(list.size());

        list.set(0, "kk");

        System.out.println(list.size());
    }
}
