package com.test;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Lyndon.Li on 2018/7/30.
 */

public class FormatTest {
    double f = 9.9/100;
    public void m1() {
        BigDecimal bg = new BigDecimal(f);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }
    /**
     * DecimalFormat转换最简便
     */
    public void m2() {
        DecimalFormat df = new DecimalFormat("#####.00");
        System.out.println(df.format(f));
    }
    /**
     * String.format打印最简便
     */
    public void m3() {
        System.out.println(String.format("%.2f", f));
    }
    public void m4() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(f));
    }
    @Test
    public  void main() {
        System.out.println("f= "+f);
        FormatTest f = new FormatTest();
        f.m1();
        f.m2();
        f.m3();
        f.m4();
    }
}
