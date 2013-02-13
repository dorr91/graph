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
		return (src.equals(other.getSource())
			&& dest.equals(other.getDest())
			&& weight == other.getWeight());
	}
	public boolean equalsIgnoreWeight(Object o) {
		Edge other = (Edge)o;
		return (src.equals(other.getSource())
				&& dest.equals(other.getDest()));
	}

	public int compareTo(Object o) {
		Edge other = (Edge)o;
		//assuming this is for edge weight comparison
		//there may be a constraint s.t. (x.compareTo(y) == 0) <=> x.equals(y)
		//TODO: check and fix if we have to obey that.
		return (weight - other.getWeight());
	}
}
