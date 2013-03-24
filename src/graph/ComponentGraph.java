package graph;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;

public class ComponentGraph extends UndirectedGraph {
    private Map<Vertex, Set<Vertex>> components;
    public ComponentGraph() {
        super();
        components = new HashMap<Vertex, Set<Vertex>>();
    }
    
    public boolean add(Edge e) {
        Set<Vertex> c1 = components.get(e.getSource());
        Set<Vertex> c2 = components.get(e.getSource());
        if(!super.add(e)) return false;
        
        if(c1 != c2) {
            c1.addAll(c2);
        }
        for(Vertex v : c2) {
            components.put(v, c1);
        }
        
        return true;
    }
    
    public boolean remove(Edge e) {
        if(!super.remove(e)) return false;
        
        Vertex v1 = e.getSource();
        Vertex v2 = e.getDest();
        
        Set<Vertex> component = components.get(v1);
        Set<Vertex> newComponent = new HashSet<Vertex>();
        
        if(!GraphUtils.reachable(this, v1, v2)) { 
            //removing this edge split this component into two
        }
        
        for(Vertex v : component) {
            if(!GraphUtils.reachable(this,v,v1)) {
                //no longer in v1's component. remove and re-add.
                component.remove(v);
                newComponent.add(v);
                components.put(v, newComponent);
            }
        }
        return true;
    }
    
    public boolean remove(Vertex v) {
       return false;
    }
}
