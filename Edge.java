package graph;

class Edge implements Comparable {
	Vertex src, dest;
	int weight;
	
	public Edge(Vertex v1, Vertex v2, int weight) {
		src = v1;
		dest = v2;
		this.weight = weight;
	}

	public boolean has(Vertex v) {
		return (v.equals(src) || v.equals(dest));
	}
	public Vertex getSource() {
		return src;
	}
	public Vertex getDest() {
		return dest;
	}
	public void setVertices(Vertex v1, Vertex v2) {
		src = v1;
		dest = v2;
	}
	public void setSource(Vertex newSource) {
		src = newSource;
	}
	public void setDest(Vertex newDest) {
		dest = newDest;
	}

	public int getWeight() {
		return weight;
	}
	public void setWeight(int newWeight) {
		weight = newWeight;
	}

	public boolean equals(Object o) {
		Edge other = (Edge)o;
		return (src.equals(other.getSrc())
			&& dest.equals(other.getDest())
			&& weight == other.getWeight());
	}

	public int compareTo(Object o) {
		Edge other = (Edge)o;
		//this comparison doesn't actually mean anything. Oh well.
		return other.equals(this) ? 0 : 1;
	}
}
