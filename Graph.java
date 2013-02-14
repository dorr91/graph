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
	Map<Vertex, Set<Vertex>> components;
	
	int negativeValues; //a semaphore to control the use of dijkstra's for sssp
	int numComponents; //keep track of the number of connected components

	public Graph() {
		vertices = new HashSet<Vertex>();
		edges = new HashSet<Edge>();
		components = new HashMap<Vertex, Set<Vertex>>();
	}
	public Graph(Set<Vertex> vertices, Set<Edge> edges) {
		this.vertices = vertices;
		this.edges = edges;
		
		for(Vertex v : vertices) {
			add(v);
		}
		for(Edge e : edges) {
			add(e);
		}
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
	
	public boolean reachable(Vertex v1, Vertex v2) {
		return components.get(v1).equals(components.get(v2));
	}
	
	public Set<Vertex> component(Vertex v) {
		return components.get(v);
	}
	public Set<Set<Vertex>> getComponents() {
		Set<Set<Vertex>> allComponents = new HashSet<Set<Vertex>>();
		allComponents.addAll(components.values());
		return allComponents;
	}
	
	public boolean isConnected() {
		return (numComponents == 1);
	}
	
	public boolean add(Vertex v) {		
		boolean success = vertices.add(v);
		//a new vertex has no edges and cannot be in an existing component.
		if(success) {
			Set<Vertex> component = new HashSet<Vertex>();
			component.add(v);
			components.put(v, component);
			numComponents++;
		}
		return success;
	}
	
	public boolean remove(Vertex v) {
		Set<Edge> outgoing = outgoingEdges(v);
		Set<Edge> incoming = incomingEdges(v);

		//removing edges will update the number of components as necessary
		for(Edge e : outgoing) {
			remove(e);
		}
		for(Edge e : incoming) {
			remove(e);
		}
		
		//remove the component now containing only v
		numComponents--;
		components.remove(v);
		vertices.remove(v);
		
		return true;
	}
	
	public boolean add(Edge e) {
		boolean success = edges.add(e);
		if(success) {
			Vertex v1 = e.getSource();
			Vertex v2 = e.getDest();
			Set<Vertex> v1comp = components.get(v1);
			Set<Vertex> v2comp = components.get(v2);

			//check if this edge joins two components and merge them if it does
			if(v1comp != v2comp) {
				for(Vertex v : v2comp) {
					v1comp.add(v);
					components.put(v, v1comp);
				}
				
				numComponents--;
			}
		}
		
		return success;
	}
	
	public boolean remove(Edge e) {
		return edges.remove(e);
		//TODO: check if numComponents changes
		//and update the components if it does.
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
		Set<Edge> componentEdges = new HashSet<Edge>();
		Set<Edge> incoming = new HashSet<Edge>();
		
		Set<Vertex> component = components.get(v);
		for(Vertex c : component) {
			for(Edge e : outgoingEdges(c)) {
				componentEdges.add(e);
			}
		}
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
		if(!reachable(start,end)) return null;
		
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
	
	public boolean isEulerianCycle() {
		if(edges.size() % 2 != 0) return false;
		if(numComponents != 1) return false;
		for(Vertex v : vertices) {
			if(neighbors(v).size() % 2 != 0) return false;
		}
		
		throw new RuntimeException("Unimplemented");
	}
}
