package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Graph {
	Set<Vertex> vertices;
	Set<Edge> edges;
	
	int negativeValues; //a semaphore to control the use of dijkstra's for sssp

	public Graph() {
		vertices = new HashSet<Vertex>();
		edges = new HashSet<Edge>();
	}
	public Graph(Set<Vertex> v, Set<Edge> e) {
		vertices = v;
		edges = e;
	}

	/* basic graph functionality */
	public boolean contains(Vertex v) {
		return vertices.contains(v);
	}

	public boolean contains(Edge e) {
		return edges.contains(e);
	}

	public boolean adjacent(Vertex v1, Vertex v2) {
		return edges.contains(new Edge(v1,v2,0));
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
	
	/* neighbors
	 * get a set of the vertices that a given vertex has edges to
	 * runtime O(|E|)
	 * @param v : the vertex to consider
	 * @return a list of vertices in the neighborhood of v
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
	
	/* useful algorithms */
	
	/* shortestPath
	 * single-source shortest path; use dijkstra's if all >= 0 weight
	 * TODO: implement <0 weight algorithm
	 * runtime: O(ugly). this is not implemented in the proper runtime.
	 * TODO: speed it up!
	 * @param start : the start vertex
	 * @param end : the goal vertex
	 * @return a list of edges that corresponds to a path from start to end
	 */
	public List<Edge> shortestPath(Vertex start, Vertex end) {
		PriorityQueue<Edge> p = new PriorityQueue<Edge>();
		//add the initial set of edges to the priority queue
		for(Edge e : outgoingEdges(start)) {
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
				for(Edge e : outgoingEdges(dest)) {
					//add the outbound edges to our queue
					p.add(e);
				}
			}
		}
		
		return paths.get(end);
	}
}
