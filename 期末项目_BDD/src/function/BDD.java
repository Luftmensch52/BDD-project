package function;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class BDD {
    public static void TransferToBDD(String s){

        LinkedList<String> list = exp(s);
        Postfix postfix = new Postfix();
        String string =  postfix.transferToPostfix(list);
        string.replaceAll(" ","");
        System.out.println(string);

        Apply apply = new Apply();
        Vertex root = apply.scanString(string);

        Traverse traverse = new Traverse();
        traverse.doTraverse(root);
        List<Vertex> vertexList = traverse.vertexList;
        Iterator<Vertex> iterator = vertexList.iterator();
        BufferedWriter simu=null;

        int count = 0;
        try {
            simu=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bdd.dot", false)));

            simu.write("digraph G{");
            simu.newLine();
            if(vertexList.size()!=1){
                while (iterator.hasNext()){
                    Vertex v = iterator.next();

                    String first=null;
                    String symbol=null;
                    String next1=null;
                    String next2=null;
                    String action1=null;
                    String action2=null;
                    if ((v.getLow() != null) && (v.getHigh() != null)){
                        first = "{\"Index = "+v.getIndex()+";Id="+v.getId()+"\"}";
                        symbol = " -> ";
                        if ((v.getLow().getLow() != null) && (v.getLow().getHigh() != null)) {
                            next1 = "{\"Index = "+v.getLow().getIndex()+";Id="+v.getLow().getId()+"\"}";
                        }else {
                            //没有左孩子
                            if(v.getLow().getVal()==0)
                            {next1 = "{\""+"False"+"\"}";}
                            else next1 = "{\""+"True"+"\"}";
                        }
                        if ((v.getHigh().getLow() != null) && (v.getHigh().getHigh() != null)) {
                            next2 = "{\"Index = "+v.getHigh().getIndex()+";Id="+v.getHigh().getId()+"\"}";
                        }else {
                            if(v.getHigh().getVal()==0)
                            {next2 = "{\""+"False"+"\"}";}
                            else next2 = "{\""+"True"+"\"}";
                        }
                        action1 = " [label = \"" + "0" + "\"]";
                        action2 = " [label = \"" + "1" + "\"]";
                        simu.write(first+symbol+next1+action1);
                        int level = (Apply.maxIndex- v.getIndex());
                        simu.write("[weight =\""+returnLevel(level)+"\"]");
                        simu.newLine();
                        simu.write(first+symbol+next2+action2);
                        simu.write("[weight =\""+returnLevel(level)+"\"]");
                        simu.newLine();
                    }else {
                        if (count==0) {
                            count++;
                        }
                    }
                }
            }else
            {
                Vertex v = iterator.next();
                if(v.getVal()==0) simu.write("{\""+"False"+"\"}");
                else simu.write("{\""+"True"+"\"}");
            }
            simu.write("}");
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally {
            try {
                simu.flush();
                simu.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("dot "+"bdd.dot" + " -T png -o "+"bdd"+".png");
            System.out.println("输出 bdd.png 成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  LinkedList<String> exp(String s){
        LinkedList<String> list = new LinkedList<>();
        String[] varspl = s.split(" ");
        Map<String,String> map = new HashMap<>();
        for (String str: varspl
        ) {
            if(map.keySet().contains(str)) list.add(map.get(str));
            else if(str.length()>1){
                if(str.charAt(0)=='-'&& str.charAt(1)=='>'){
                    list.add(str);
                    continue;
                }
                char[] tmp = str.toCharArray();
                int it = 'A' + tmp[1]-'1';
                String ch = new String(""+(char)it);
                map.put(str,ch);
                list.add(ch);
            }else
                list.add(str);
        }
        return list;
    }
    public static int returnLevel(int level){
        int base = 5 ;
        while(level>0){
            base = base * 5 ;
            level--;
        }
        return base;
    }
}

