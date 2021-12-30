package function;

import java.util.*;

public class Reduce {

    private Map<Integer,List<Vertex>> doMap(List<Vertex> vertexList){
        Map<Integer,List<Vertex>> map = new HashMap<>();
        for (int i = 0; i < vertexList.size(); i++) {
            Vertex vertex = vertexList.get(i);
            int index = vertex.getIndex();
            if (!map.containsKey(index)){
                List<Vertex> vt = new ArrayList<>();
                map.put(index,vt);
            }
            List<Vertex> vertices = map.get(index);
            vertices.add(vertex);
            map.put(index,vertices);
        }
        return map;
    }


    public Vertex doReduce(Vertex v){

        Traverse traverse = new Traverse();
        traverse.doTraverse(v);
        List<Vertex> vertexList = traverse.vertexList;
        int length = vertexList.size();
        Vertex[] subgraph = new Vertex[length+1];
        Map<Integer,List<Vertex>> vlist = doMap(vertexList);
        int maxIndex = getMaxKey(vlist);
        int nextid = 0;

        for (int i = maxIndex; i > 0; i--) {
            if (vlist.containsKey(i)){
                Map<Key, Vertex> Q = new HashMap<>();

                List<Vertex> vertices = vlist.get(i);
                for (int j = 0; j < vertices.size(); j++) {
                    Vertex u = vertices.get(j);
                    if (u.getIndex() == maxIndex){
                        Key key = new Key();
                        key.setA(u.getVal());
                        key.setB(0);
                        Q.put(key,u);
                    }
                    else if (u.getLow().getId() == u.getHigh().getId()){
                        u.setId(u.getLow().getId());
                    }
                    else {
                        Key key = new Key();
                        key.setA(u.getLow().getId());
                        key.setB(u.getHigh().getId());
                        Q.put(key,u);
                    }
                }
                List<Key> keyList = new ArrayList<>();
                Set<Key> set = Q.keySet();
                Iterator<Key> keyIterator = set.iterator();
                while (keyIterator.hasNext()){
                    keyList.add(keyIterator.next());
                }

                Collections.sort(keyList);
                Key oldkey = new Key();
                oldkey.setA(-1);
                oldkey.setB(-1);
                Iterator<Key> iterator = keyList.iterator();
                while (iterator.hasNext()){
                    Key key = iterator.next();
                    if ((key.getA() == oldkey.getA()) && (key.getB() == oldkey.getB())){
                        Vertex vertex = Q.get(key);
                        vertex.setId(nextid);
                    }
                    else {
                        nextid += 1;
                        Vertex vertex = Q.get(key);
                        vertex.setId(nextid);
                        subgraph[nextid] = vertex;
                        if (vertex.getLow() == null){
                            vertex.setLow(null);
                        }
                        else {
                            vertex.setLow(subgraph[vertex.getLow().getId()]);
                        }
                        if (vertex.getHigh() == null){
                            vertex.setHigh(null);
                        }
                        else {
                            vertex.setHigh(subgraph[vertex.getHigh().getId()]);
                        }
                        oldkey.setA(key.getA());
                        oldkey.setB(key.getB());
                    }
                }

            }

        }
        return subgraph[v.getId()];

    }


    private int getMaxKey(Map<Integer,List<Vertex>> map){
        if (map == null){
            return  0;
        }
        int maxIndex = -1;
        Set<Integer> set = map.keySet();
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()){
            int index = iterator.next().intValue();
            if (index > maxIndex){
                maxIndex = index;
            }
        }

        return maxIndex;
    }
}
