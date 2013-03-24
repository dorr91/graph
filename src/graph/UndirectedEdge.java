package graph;

import java.util.Set;
import java.util.HashSet;

public class UndirectedEdge extends Edge {
    Set<Vertex> vertices = new HashSet<Vertex>();
    
    public UndirectedEdge(Vertex v1, Vertex v2, int weight) {
        super(v1,v2,weight);
        vertices.add(v1);
        vertices.add(v2);
    }
    
    public Set<Vertex> getVertices() {
        return vertices;
    }
}
