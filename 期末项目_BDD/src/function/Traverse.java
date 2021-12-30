package function;

import java.util.ArrayList;
import java.util.List;

public class Traverse {

    public List<Vertex> vertexList = new ArrayList<>();

    public void doTraverse(Vertex vertex){
        if (vertex != null){
            vertex.setMark((vertex.getMark() +1) %2);
            vertexList.add(vertex);
            if (vertex.getLow() != null){
                if (vertex.getMark() != vertex.getLow().getMark()){
                    doTraverse(vertex.getLow());
                }
            }
            if (vertex.getHigh() != null){
                if (vertex.getMark() != vertex.getHigh().getMark()){
                    doTraverse(vertex.getHigh());
                }
            }
        }
    }

    public Vertex negationTraverse(Vertex vertex){
        if (vertex != null){
            if ((vertex.getLow() != null) && (vertex.getHigh() != null)){
                negationTraverse(vertex.getLow());
                negationTraverse(vertex.getHigh());
            }
            else{
                if (!vertex.isChanged()) {
                    vertex.setVal((vertex.getVal() + 1) % 2);
                    vertex.setChanged(true);
                }
            }

        }
        return vertex;
    }

    public Vertex initIsChanged(Vertex vertex){
        if (vertex != null){
            vertex.setChanged(false);
            if ((vertex.getLow() != null) && (vertex.getHigh() != null)){
                initIsChanged(vertex.getLow());
                initIsChanged(vertex.getHigh());
            }
        }
        return vertex;
    }

}