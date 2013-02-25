package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import graph.Graph;

public class GraphUtils {
/* useful graph algorithms */
    
    /* shortestPath
     * single-source shortest path; use dijkstra's if all >= 0 weight
     * TODO: implement <0 weight algorithm
     * runtime: O(ugly). this is not implemented in the proper runtime.
     * TODO: speed it up!
     * @param start : the start vertex
     * @param end : the goal vertex
     * @return a list of edges that corresponds to a path from start to end
     */
    public static List<Edge> shortestPath(Graph g, Vertex start, Vertex end) {
        if(!g.reachable(start,end)) return null;
        
        PriorityQueue<Edge> p = new PriorityQueue<Edge>();
        //add the initial set of edges to the priority queue
        for(Edge e : g.outgoingEdges(start)) {
            p.add(e);
        }

        Map<Vertex, List<Edge>> paths = new HashMap<Vertex, List<Edge>>();
        paths.put(start, new ArrayList<Edge>());
        
        //keep looking until we've found the destination vertex
        //or we're out of edges to look at
        while(paths.get(end) != null && p.peek() != null) {
            Edge next = p.remove();
            Vertex src = next.getSource();
            Vertex dest = next.getDest();
            
            //have we seen this vertex before?
            if(paths.get(dest) == null) {
                //haven't seen this vertex, meaning we've just found a new shortest path
                List<Edge> partialPath = paths.get(src);
                List<Edge> fullPath = new ArrayList<Edge>(partialPath);
                fullPath.add(next);

                paths.put(dest, fullPath);
                for(Edge e : g.outgoingEdges(dest)) {
                    //add the outbound edges to our queue
                    p.add(e);
                }
            }
        }
        
        return paths.get(end);
    }
    
    public static boolean isEulerianCycle(Graph g) {
        if(g.getEdges().size() % 2 != 0) return false;
        for(Vertex v : g.getVertices()) {
            if(g.neighbors(v).size() % 2 != 0) return false;
        }
        
        throw new RuntimeException("Unimplemented");
    }
}
