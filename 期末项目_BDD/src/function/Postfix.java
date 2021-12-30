package function;

import java.util.Iterator;
import java.util.LinkedList;

public class Postfix {

    private static LinkedList<String> operators=new LinkedList<>();

    public static LinkedList<String> output=new LinkedList<>();

    private static StringBuilder sb=new StringBuilder();

   public static String transferToPostfix(LinkedList<String> list){
        Iterator<String> it=list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (isOperator(s)) {
                if (operators.isEmpty()) {
                    operators.push(s);
                }
                else {
                    if (priority(operators.peek())<=priority(s)&&!s.equals(")")) {
                        operators.push(s);
                    }
                    else if(!s.equals(")")&&priority(operators.peek())>priority(s)){
                        while (operators.size()!=0&&priority(operators.peek())>=priority(s)
                                &&!operators.peek().equals("(")) {
                            if (!operators.peek().equals("(")) {
                                String operator=operators.pop();
                                //sb.append(operator).append(" ");
                                sb.append(operator);
                                output.push(operator);
                            }
                        }
                        operators.push(s);
                    }
                    else if (s.equals(")")) {
                        while (!operators.peek().equals("(")) {
                            String operator=operators.pop();
                          //sb.append(operator).append(" ");
                            sb.append(operator);
                            output.push(operator);
                        }
                        operators.pop();
                    }
                }
            }
            else {
                //sb.append(s).append(" ");
                sb.append(s);
                output.push(s);
            }
        }
        if (!operators.isEmpty()) {
            Iterator<String> iterator=operators.iterator();
            while (iterator.hasNext()) {
                String operator=iterator.next();
                sb.append(operator);
                output.push(operator);
                iterator.remove();
            }
        }
        return sb.toString();
    }


    private static boolean isOperator(String oper){
        if (oper.equals("+")||oper.equals("-")||oper.equals("/")||oper.equals("*")
                ||oper.equals("(")||oper.equals(")")||oper.equals("!")||oper.equals("->")||oper.equals("^")) {
            return true;
        }
        return false;
    }

    private static int priority(String s){
        switch (s) {
            case "+":return 1;
            case "-":return 1;
            case "*":return 2;
            case "/":return 2;
            case "->":return 3;
            case "^":return 3;
            case "!":return 4;
            case "(":return 5;
            case ")":return 5;
            default :return 0;
        }
    }
}