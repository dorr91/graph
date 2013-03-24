package graph;

import java.util.Set;

public class UndirectedGraph extends Graph {
    
    public UndirectedGraph() {
        super();
    }
    public UndirectedGraph(Set<Vertex> vertices, Set<UndirectedEdge> edges) {
        super();
        for(Vertex v : vertices) super.add(v);
        for(Edge e : edges) {
            super.add(e);
            super.add(new Edge(e.getDest(), e.getSource(), e.getWeight()));
        }
    }
    
    public boolean add(UndirectedEdge e) {
        Object[] vertices = e.getVertices().toArray();
        Vertex v1 = (Vertex)vertices[0];
        Vertex v2 = (Vertex)vertices[1];
        Edge e1 = new Edge(v1,v2,e.getWeight());
        Edge e2 = new Edge(v2,v1,e.getWeight());
        if(super.add(e1)) {
            if(super.add(e2)) {
                return true;
            } else {
                super.remove(e1);
            }
        }
        return false;
    }
    
}
