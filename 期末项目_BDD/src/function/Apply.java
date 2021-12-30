package function;

import java.util.*;

public class Apply {

    static int maxIndex = -1;

    Stack<Vertex> stack = new Stack<>();

    public Vertex scanString(String profix){

        LinkedList<String> strList = Postfix.output;
        LinkedList<String> str = new LinkedList<>();

        Iterator itemp = strList.iterator();
        while (itemp.hasNext()){
            String s = itemp.next().toString();
            str.addFirst(s);
            if (!isOperator(s)){
                char curchar = s.charAt(0);
                int currindex = (int)curchar - 64;
                if (currindex > maxIndex){
                    maxIndex = currindex;
                }
            }
        }

        Iterator it = str.iterator();
        while (it.hasNext()){
            String s = it.next().toString();
            if (!isOperator(s)){
                Vertex newVertex = createSimpleVertex(s);
                stack.push(newVertex);
            }
            else{
                if (s.equals("!")){
                    Vertex negationVertex = stack.pop();
                    Traverse traverse = new Traverse();
                    Vertex vertex = traverse.negationTraverse(negationVertex);
                    //vertex = traverse.initIsChanged(vertex);
                    stack.push(vertex);

                }
                if (s.equals("+") || s.equals("*")||s.equals("->")||s.equals("^")){
                    Vertex sec = stack.pop();
                    Vertex fir = stack.pop();
                    Vertex rootVertex = doApply(fir,s,sec);
                    stack.push(rootVertex);
                }

            }
        }

        Vertex root = stack.peek();
        return root;
    }

    private Vertex doApply(Vertex fir, String operator, Vertex sec){
        Traverse traverse1 = new Traverse();
        traverse1.doTraverse(fir);
        List<Vertex> firVertex = traverse1.vertexList;
        int numOfFir = firVertex.size();

        Traverse traverse2 = new Traverse();
        traverse2.doTraverse(sec);
        List<Vertex> secVertex = traverse2.vertexList;
        int numOfSec = secVertex.size();

        for (int i = 0; i < firVertex.size(); i++) {
            Vertex tmp = firVertex.get(i);
            tmp.setId(i);
            firVertex.set(i,tmp);
        }
        for (int i = 0; i < secVertex.size(); i++) {
            Vertex tmp = secVertex.get(i);
            tmp.setId(i);
            secVertex.set(i,tmp);
        }

        Vertex firroot = firVertex.get(0);
        Vertex secroot = secVertex.get(0);

         Vertex[][] metrix = new Vertex[numOfFir][numOfSec];
         Vertex vertex = applyStep(firroot,secroot,operator,metrix);
         Reduce reduce = new Reduce();
         Vertex redVertex =  reduce.doReduce(vertex);

        return redVertex;
    }

    private Vertex applyStep(Vertex v1, Vertex v2, String operator, Vertex[][] metrix){
        Vertex u = metrix[v1.getId()][v2.getId()];
        if (u != null){
            return u;
        }
        u = new Vertex();
        u.setMark(v1.getMark());
        metrix[v1.getId()][v2.getId()] = u;
        u.setVal(compute(v1.getVal(),operator,v2.getVal()));
        if (u.getVal() != 2){ //若为终端节点
            u.setIndex(maxIndex + 1);
            u.setLow(null);
            u.setHigh(null);
        }
        else { //非终端节点
            u.setIndex(min(v1.getIndex(),v2.getIndex()));
            Vertex vlow1,vhigh1,vlow2,vhigh2;
            if (v1.getIndex() == u.getIndex()){
                vlow1 = v1.getLow();
                vhigh1 = v1.getHigh();

            }
            else {
                vlow1 = v1;
                vhigh1 = v1;
            }

            if (v2.getIndex() == u.getIndex()){
                vlow2 = v2.getLow();
                vhigh2 = v2.getHigh();
            }
            else {
                vlow2 = v2;
                vhigh2 = v2;
            }
            u.setLow(applyStep(vlow1,vlow2,operator,metrix));
            u.setHigh((applyStep(vhigh1,vhigh2,operator,metrix)));
        }

        return u;
    }

    private int min(int num1,int num2){
        if (num1 > num2){
            return num2;
        }
        else {
            return num1;
        }
    }

    private int compute(int val1,String operator,int val2){
        if (operator.equals("+")){
            if (val1 == 1 || val2 == 1){
                return 1;
            }
            else if (val1 ==0 && val2 == 0){
                return 0;
            }
            else {
                return 2;
            }
        }

        if (operator.equals("*")){
            if (val1 == 0 || val2 == 0){
                return 0;
            }
            else if (val1 == 1 && val2 == 1){
                return 1;
            }
            else {
                return 2;
            }
        }

        if (operator.equals("->")){
            if (val1 == 1 && val2 == 0){
                return 0;
            }
            if (val1 == 0 || ( val1 == 1 && val2 ==1)){
                return 1;
            }
            else {
                return 2;
            }
        }
        if (operator.equals("^")){
            if ((val1 == 1 && val2 == 0)||(val1 == 0 && val2 == 1)){
                return 1;
            }
            if ((val1 == 1 && val2 == 1)||(val1 == 0 && val2 == 0)){
                return 0;
            }
            else {
                return 2;
            }
        }
        return 0;
    }

    private static boolean isOperator(String oper){
        if (oper.equals("+")||oper.equals("-")||oper.equals("/")||oper.equals("*")
                ||oper.equals("(")||oper.equals(")")||oper.equals("!")||oper.equals("->")||oper.equals("^")) {
            return true;
        }
        return false;
    }

    private Vertex createSimpleVertex(String s){
        char[] c = s.toCharArray();
        Vertex vertex = new Vertex();
        vertex.setMark(0);
        vertex.setIndex((int)c[0] - 64);
        vertex.setVal(2);

        Vertex vertexLeft = new Vertex();
        vertexLeft.setMark(0);
        vertexLeft.setIndex(maxIndex + 1);
        vertexLeft.setVal(0);
        vertexLeft.setLow(null);
        vertexLeft.setHigh(null);

        Vertex vertexRight = new Vertex();
        vertexRight.setMark(0);
        vertexRight.setIndex(maxIndex + 1);
        vertexRight.setVal(1);
        vertexRight.setLow(null);
        vertexRight.setHigh(null);

        vertex.setLow(vertexLeft);
        vertex.setHigh(vertexRight);
        return vertex;
    }

}
