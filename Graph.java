package graph;

import java.util.HashSet;
import java.util.Set;

class Graph {
	Set<Vertex> vertices;
	Set<Edge> edges;

	public Graph() {
		vertices = new HashSet<Vertex>();
		edges = new HashSet<Edge>();
	}
	public Graph(Set<Vertex> v, Set<Edge> e) {
		vertices = v;
		edges = e;
	}

	public boolean contains(Vertex v) {
		return vertices.contains(v);
	}

	public boolean contains(Edge e) {
		return edges.contains(e);
	}

	public boolean adjacent(Vertex v1, Vertex v2) {
		return edges.contains(new Edge(v1,v2));
	}
}
