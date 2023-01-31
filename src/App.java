import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class Node {
  private Node parent;
  private String name;
  private LinkedList<Edge> edgeList;

  public Node(String name) {
    this.name = name;
    edgeList = new LinkedList<>();
  }

  public void SetParent(Node node) {
    parent = node;
  }

  public Node getParent() {
    return parent;
  }

  public String getName() {
    return name;
  }

  public LinkedList<Edge> getEdges() {
    return edgeList;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Node)) {
      return false;
    }
    Node node = (Node) obj;
    return name.equals(node.getName());
  }
}

class Edge {
  private int cost;
  private Node destVertex;

  public Edge(Node dest, int cost) {
    this.destVertex = dest;
    this.cost = cost;
  }

  public Edge(Node dest) {
    this.destVertex = dest;
    this.cost = 1;
  }

  public int getCost() {
    return cost;
  }

  public Node getDestVertex() {
    return destVertex;
  }
}

class Graph {
  private HashSet<Node> nodes;

  public Graph() {
    nodes = new HashSet<>();
  }

  public boolean AddEdge(Node v1, Node v2, int cost) {
    return v1.getEdges().add(new Edge(v2, cost)) && v2.getEdges().add(new Edge(v1, cost));
  }

  public boolean AddVertex(Node v) {
    return nodes.add(v);
  }

  public void printGraph() {
    for (Node v : nodes) {
      System.out.print("vertex name: " + v.getName() + ":\n");
      for (Edge e : v.getEdges()) {
        System.out.print("destVertex: " + e.getDestVertex().getName() + ", cost: " + e.getCost() + "\n");
      }
      System.out.print("\n");
    }
  }

  public Node getNode(String nameNode) {
    for (Node node : nodes) {
      if (nameNode.equals(node.getName())) {
        return node;
      }
    }
    return null;
  }

  public HashSet<Node> getNodes() {
    return nodes;
  }
  public void clearParentForAllNodes()
  {
    for (Node node : nodes) {
        node.SetParent(null);
      }
  }
  
}

public class App {
  public static void main(String[] args) {
    Graph ourGraph = new Graph();
    loadDataFromFile(ourGraph);
    Node start = ourGraph.getNode("A");
    Node end = ourGraph.getNode("D");
    System.out.println("Implement FBS");
    if (BFS(start, end, ourGraph)) {
      displayPath(start, end, ourGraph);
    } else {
      System.out.println("Cannot find path !!!!!");
    }
    System.out.println("\nImplement DFS");
    if (DFS(start, end, ourGraph)) {
      displayPath(start, end, ourGraph);
    } else {
      System.out.println("Cannot find path !!!!!");
    }

    System.out.println("\nImplement DLS");
    if (DLS(start, end, ourGraph,2)) {
      displayPath(start, end, ourGraph);
    } else {
      System.out.println("Cannot find path !!!!!");
    }
    // ourGraph.printGraph();
  }

  private static void loadDataFromFile(Graph graph) {
    try {
      File myObj = new File("src/InputData.txt");
      Scanner myReader = new Scanner(myObj);
      int line = 0;
      while (myReader.hasNextLine()) {
        line++;
        String data = myReader.nextLine();
        if (line == 1) {
          String[] texts = data.split(",");
          for (String t : texts) {
            graph.AddVertex(new Node(t));
          }
        } else {
          data = data.substring(1, data.length() - 1);
          String[] texts = data.split(",");
          graph.AddEdge(graph.getNode(texts[0]), graph.getNode(texts[1]), Integer.parseInt(texts[2]));
        }

      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  private static void displayPath(Node start, Node des, Graph graph) {
    ArrayList<Node> paths = new ArrayList<Node>();

    paths.add(des);
    Node parent = des.getParent();
    while (parent != null) {
      paths.add(parent);
      parent = parent.getParent();
    }
    Collections.reverse(paths);
    for (Node node : paths) {
      System.out.print(node.getName() + " ");
    }
    graph.clearParentForAllNodes();
  }

  private static boolean BFS(Node start, Node des, Graph graph) {
    if (start == null || des == null) {
      return false;
    }
    ArrayList<Node> visitedList = new ArrayList<>();
    Queue<Node> queue = new LinkedList<Node>();
    queue.add(start);
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      visitedList.add(node);
      for (Edge edge : node.getEdges()) {
        Node destVertex = edge.getDestVertex();
        if (visitedList.contains(destVertex)) {
          continue;
        }
        destVertex.SetParent(node);
        if (destVertex.equals(des)) {
          return true;
        }
        queue.add(destVertex);
      }
    }
    return false;
  }

  private static boolean DFS(Node start, Node des, Graph graph) {
    if (start == null || des == null) {
      return false;
    }
    ArrayList<Node> visitedList = new ArrayList<>();
    Stack<Node> stack = new Stack<Node>();
    stack.add(start);
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      visitedList.add(node);
      for (Edge edge : node.getEdges()) {
        Node destVertex = edge.getDestVertex();
        if (visitedList.contains(destVertex)) {
          continue;
        }
        destVertex.SetParent(node);
        if (destVertex.equals(des)) {
          return true;
        }
        stack.add(destVertex);
      }
    }
    return false;
  }

  private static boolean DLS(Node start, Node des, Graph graph, int depthLimit) {
    if (start == null || des == null) {
      return false;
    }
    ArrayList<Node> visitedList = new ArrayList<>();
    Stack<Node> stack = new Stack<Node>();
    stack.add(start);
    int depth = 0;
    while (!stack.isEmpty()) {
      if (depth <= depthLimit) {
        Node node = stack.pop();
        visitedList.add(node);
        for (Edge edge : node.getEdges()) {
          Node destVertex = edge.getDestVertex();
          if (visitedList.contains(destVertex)) {
            continue;
          }
          
          destVertex.SetParent(node);
          if (destVertex.equals(des)) {
            return true;
          }
          stack.add(destVertex);
        }
        
        depth++;
      }
      else
      {
        System.out.println("no paths for depth limit like that :((");
        return false;
      }

    }
    return false;
  }

}
