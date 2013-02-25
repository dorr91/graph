package graph;

import java.util.HashSet;
import java.util.Set;

class Graph {
	private Set<Vertex> vertices;
	private Set<Edge> edges;
	
	int negativeValues = 0; //a semaphore to control use of dijkstra's for SSSP

	public Graph() {
		vertices = new HashSet<Vertex>();
		edges = new HashSet<Edge>();
	}
	public Graph(Set<Vertex> vertices, Set<Edge> edges) {
		this.vertices = vertices;
		this.edges = edges;
		
		for(Edge e : edges) {
		    if(e.getWeight() < 0) {
		        negativeValues++;
		    }
		}
	}
	
    public boolean add(Vertex v) {
        return vertices.add(v);
    }

    public boolean remove(Vertex v) {
        return vertices.remove(v);
    }

    public boolean add(Edge e) {
        return edges.add(e);
    }

    public boolean remove(Edge e) {
        return edges.remove(e);
    }
    
    public Set<Vertex> getVertices() {
        return vertices;
    }
    public Set<Edge> getEdges() {
        return edges;
    }

	/* basic graph functionality */

	public boolean contains(Vertex v) {
		return vertices.contains(v);
	}

	public boolean contains(Edge e) {
		return edges.contains(e);
	}

	public boolean adjacent(Vertex v1, Vertex v2) {
		//this is pretty terrible. 
		//TODO: make it better.
		//maybe check just edges leaving vertices in the current component?
		for(Edge e : edges) {
			if(e.getSource().equals(v1) && e.getDest().equals(v2))
				return true;
		}
		return false;
	}
	
	public boolean reachable(Vertex src, Vertex dest) {
		//TODO: implement
	    return false;
	}
	
	/* neighbors
	 * get a set of the vertices that a given vertex has edges to
	 * runtime O(|E|)
	 * @param v : the vertex to consider
	 * @return a set of vertices in the neighborhood of v
	 */
	public Set<Vertex> neighbors(Vertex v) {
		Set<Vertex> neighbors = new HashSet<Vertex>();
		for(Edge e : edges) {
			if(e.getSource().equals(v)) neighbors.add(e.getDest());
		}
		
		return neighbors;
	}
	
	/* outgoingEdges
	 * get a set of the edges whose source is the given vertex.
	 * runtime O(|E|)
	 * @param v : the vertex to consider
	 * @return the set of edges whose source is v
	 */
	public Set<Edge> outgoingEdges(Vertex v) {
		Set<Edge> outgoing = new HashSet<Edge>();
		for(Edge e : edges) {
			if(e.getSource().equals(v)) outgoing.add(e);
		}
		
		return outgoing;
	}
	
	/* incomingEdges
	 * get a set of the edges whose dest is the given vertex
	 * runtime O(|E|) 
	 * @param v : the vertex to consider
	 * @return the set of edges whose dest is v
	 */
	public Set<Edge> incomingEdges(Vertex v) {
		Set<Edge> incoming = new HashSet<Edge>();
		
		for(Edge e : edges) {
			if(e.getDest().equals(v)) incoming.add(e);
		}
		
		return incoming;
	}
}
