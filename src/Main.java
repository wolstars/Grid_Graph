import java.util.ArrayList;
import java.util.Random;

public class Main {
	Node[][] nodes;
	int k = 25;
	Random random = new Random(k);

	public static void main(String[] args) {
		Main m = new Main();
		m.graph();
	}

	public void graph() {
		nodes = new Node[k + 1][k + 1];
		for (int j = 0; j <= k; j++) {
			for (int i = 0; i <= k; i++) {
				nodes[j][i] = new Node();
			}
		}
		initialize(2, 3);
		efficient(2, 3);
		System.out.println(nodes[2][3]);
		System.out.println(nodes[2][3].getEdges(1).getWeight());
		System.out.println(nodes[2][4].getEdges(1).getWeight());
		System.out.println(nodes[2][5]);

	}

	public void initialize(int j, int i) {
		makeEdges();
		for (int l = 0; l < k; l++) {
			for (int y = 0; y < k; y++) {
				nodes[l][y].setValue(null);
			}
		}
		nodes[j][i].setValue(0);
	}

	public void efficient(int j, int i) {
		int startI = i;
		while (nodes[j][i].getEdges(1) != null) {
			relax(nodes[j][i], nodes[j][i + 1]);
			i++;
		}
		i = startI;
		while (nodes[j][i].getEdges(0) != null) {
			relax(nodes[j][i], nodes[j][i - 1]);
			i--;
		}
		if (j < k) {
			for (i = 0; i < k; i++) {
				relax(nodes[j][i], nodes[j + 1][i]);
			}
		}
		for (j = j + 1; j < k - 1; j++) {
			i = 0;
			while (nodes[j][i].getEdges(1) != null) {
				relax(nodes[j][i], nodes[j][i + 1]);
				i++;
			}
			while (nodes[j][i].getEdges(0) != null) {
				relax(nodes[j][i], nodes[j][i - 1]);
				i--;
			}
			for (i = 0; i < k; i++) {
				relax(nodes[j][i], nodes[j + 1][i]);
			}
		}
		i = 0;
		while (nodes[j][i].getEdges(1) != null) {
			relax(nodes[j][i], nodes[j][i + 1]);
			i++;
		}
		while (nodes[j][i].getEdges(0) != null) {
			relax(nodes[j][i], nodes[j][i - 1]);
			i--;
		}
	}

	public void makeEdges() {
		int low = 2;
		int high = 32;
		for (int j = 0; j < k - 1; j++) {
			nodes[j][0].addEdge(null);
			nodes[j][0].addEdge(new Edge(random.nextInt(high - low) + low, nodes[j][1]));
			nodes[j][0].addEdge(new Edge(random.nextInt(high - low) + low, nodes[j + 1][0]));
			for (int i = 1; i < k - 1; i++) {
				nodes[j][i].addEdge(new Edge(random.nextInt(high - low) + low, nodes[j][i - 1]));
				nodes[j][i].addEdge(new Edge(random.nextInt(high - low) + low, nodes[j][i + 1]));
				nodes[j][i].addEdge(new Edge(random.nextInt(high - low) + low, nodes[j + 1][i]));
			}
			nodes[j][k].addEdge(new Edge(random.nextInt(high - low) + low, nodes[j][k - 1]));
			nodes[j][k].addEdge(null);
			nodes[j][k].addEdge(new Edge(random.nextInt(high - low) + low, nodes[j + 1][k]));
		}
		nodes[k][0].addEdge(null);
		nodes[k][0].addEdge(new Edge(random.nextInt(high - low) + low, nodes[k][1]));
		nodes[k][0].addEdge(null);
		for (int i = 1; i < k - 1; i++) {
			nodes[k][i].addEdge(new Edge(random.nextInt(high - low) + low, nodes[k][i - 1]));
			nodes[k][i].addEdge(new Edge(random.nextInt(high - low) + low, nodes[k][i + 1]));
			nodes[k][0].addEdge(null);
		}
		nodes[k][k].addEdge(new Edge(random.nextInt(high - low) + low, nodes[k][k - 1]));
		nodes[k][k].addEdge(null);
		nodes[k][k].addEdge(null);
	}

	public void relax(Node node1, Node node2) {
		Edge e;
		for (int i = 0; i < 3; i++) {
			e = node1.getEdges(i);
			if (e == null) continue;
			if (e.getNode() == node2) {
				if (node2.getValue() == null) {
					node2.setValue(node1.getValue() + e.getWeight());
				} else {
					node2.setValue(Math.min(node1.getValue() + e.getWeight(), node2.getValue()));
				}
				return;
			}
		}
	}


class Edge {
	private Integer weight;
	private Node node;

	public Edge(Integer weight, Node node){
		this.weight = weight;
		this.node = node;
	}

	public Node getNode() {
		return node;
	}

	public Integer getWeight() {
		return weight;
	}

}

class Node{
	private Integer value;
	private boolean done = false;
	private Node path = null;
	private ArrayList<Edge> edges;
	
	public Node() {
		this.value = null;
		this.edges = new ArrayList<>();
	}
	
	public String toString() {
		return value.toString();
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
	public Edge getEdges(Integer i) {
		try {
			return edges.get(i);
		} catch (Exception e){
			return null;
		}
	}
}
}
