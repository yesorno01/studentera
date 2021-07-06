package com.mathematics.studentera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class StudenteraApplication {

    public static void main(String[] args) {
        String returnStr;

        SpringApplication.run(StudenteraApplication.class, args);

        returnStr = additionAndSubtractionWithinTwenty(100, 100, "\n");

//        String str = String.format("%02d", 3);
//        String str = String.format("%1$-7s", "hello");
//        System.out.println(str+"!!!");

        System.out.println(returnStr);
        System.out.println("finish！");
    }

    /**
     * @param count     输出次数（一共多少到题）
     * @param level     级别（多少以内运算）
     * @param lineBreak 换行符（网页还是后台的换行符）
     * @return
     */
    public static String additionAndSubtractionWithinTwenty(int count, int level, String lineBreak) {

        //可调变量
        double ratioOfComplexitySubtraction = 0.8;//减法中退位减法题目比例：0-1
        int columnNumber = 5;//列数

        int sum;//求和
        int dif;//求差
        int x;//公式x
        int y;//公式y
        String returnStr = "";//返回字符串
        String operationalSymbol;//运算符号
        String strX;//公式x格式化
        String strY;//公式y格式化
        String equation;//公式
        boolean isAdd;//加法

        for (int i = 0; i < count; ) {
            //加减法随机生成
            isAdd = ((int) (10 * Math.random())) % 2 == 1;
            x = (int) (Math.random() * level);
            y = (int) (Math.random() * level);
            operationalSymbol = "-";

            if (isAdd) {
                operationalSymbol = "+";
            }

            if (!isAdd && Math.random() <= ratioOfComplexitySubtraction) {
                //减法复杂度-退位减法
                while (!(x > 10 && y < 10 && x % 10 < y)) {
                    x = (int) (Math.random() * level);
                    y = (int) (Math.random() * level);
                }
            }

            sum = x + y;
            dif = x - y;

            //格式化
            strX = String.format("%1$-2d", x);
            strY = String.format("%1$-2d", y);
            equation = String.format("%1$-16s", strX + operationalSymbol + strY + "=");

            if (isAdd) {
                if (sum <= level && sum != 0 && !returnStr.contains(equation)) {
                    i = i + 1;
                    returnStr = returnStr + equation;
                    if (i % columnNumber == 0) {
                        returnStr = returnStr + lineBreak;
                    }
                }
            } else {
                if (dif <= level && dif > 0 && returnStr.indexOf(equation) == -1) {
                    i = i + 1;
                    returnStr = returnStr + equation;
                    if (i % columnNumber == 0) {
                        returnStr = returnStr + lineBreak;
                    }
                }
            }
        }
        return returnStr;
    }
}
