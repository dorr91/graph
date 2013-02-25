package graph;

class Vertex<T> {
	T data;

	public Vertex() {
		data = null;
	}
	public Vertex(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}
	public void setData(T newData) {
		data = newData;
	}
}
