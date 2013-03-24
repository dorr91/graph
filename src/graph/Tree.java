package graph;

import java.util.Set;

public class Tree extends Graph {
    
    public Tree(Set<Vertex> vertices, Set<Edge> edges) {
        super();
        boolean success = true;
        for(Vertex v : vertices) super.add(v);
        for(Edge e : edges) success = success && super.add(e);
        if(!success) throw new IllegalArgumentException(
                "Provided edges do not form a tree.");
    }

    public boolean add(Edge e) {
        if(GraphUtils.reachable(this, e.getSource(), e.getDest())) {
            return false;
        }
        
        return super.add(e);
    }
}
