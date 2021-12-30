import function.BDD;
import function.Postfix;

import java.util.*;

/**
 * @author teamwork
 * case1: x1 * x2 + x3
 * case2: x1 * x2 + x3 * x4 + x5 * x6
 * case3: ! ( x1 * ! x2 * x3 )
 * case4: x1 * x4 + x2 * x5 + x3 * x6
 */

public class Start {

    public static void main(String[] args) {
        System.out.println(
                "Notice: 运算符包括与（*）、或（+）、非（！）、蕴含（->）、异或（^）及括号\"()\"\n" +
                "每一个字符都用空格隔开。\n各运算符的优先级如下：非> 蕴含 = 异或 > 与 > 或\n"
                + "如：! ( x1 * ! x2 * x3 )" + "\n输入字符串:");

        Scanner scanner = new Scanner(System.in);
        BDD.TransferToBDD(scanner.nextLine());
        scanner.close();
    }
}

