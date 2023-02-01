
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

// 2 class below just only for UCS algorithm
class PathComparator implements Comparator<Path>
{

  @Override
  public int compare(Path o1, Path o2) {
    if(o1.getTotalCost()<o2.getTotalCost()){return -1;}
    if(o1.getTotalCost()>o2.getTotalCost()){return 1;}
    else return 0;
  }
  
}
class Path{
  private ArrayList<Node> nodes;
  private int totalCost;
  public Path(int cost)
  {
    nodes = new ArrayList<>();
    totalCost = cost;
  }
  public void addNode(Node node)
  {
    if(nodes.contains(node)){return;}
    nodes.add(node);
  }
  public void addAlls(ArrayList<Node> nodes)
  {
    this.nodes.addAll(nodes);
  }
  public void setTotalCost(int cost)
  {
    totalCost = cost;
  }
  public void addTotalCost(int addValue)
  {
    totalCost+=addValue;
  }
  public ArrayList<Node> getNodes(){return nodes;}
  public Node getLastNode(){return nodes.get(nodes.size()-1);}
  public int getTotalCost(){return totalCost;}

}
//-----------------------------------------

class Node{
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

  public void clearParentForAllNodes() {
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
    Node end = ourGraph.getNode("Z");
    deleteOldDataInFileOutput(start.getName(),end.getName());
    System.out.println("Implement FBS");
    if (BFS(start, end, ourGraph)) {
      displayPath(start, end, ourGraph,"Implement FBS");
    } else {
      displayData("Implement FBS",null );
      System.out.println("Cannot find path !!!!!");
    }
    System.out.println("\nImplement DFS");
    if (DFS(start, end, ourGraph)) {
      displayPath(start, end, ourGraph,"Implement DFS");
    } else {
      displayData("Implement DFS",null );
      System.out.println("Cannot find path !!!!!");
    }

    System.out.println("\nImplement DLS");
    if (DLS(start, end, ourGraph, 4)) {
      displayPath(start, end, ourGraph,"Implement DLS");
    } else {
      displayData("Implement DLS",null );
      System.out.println("Cannot find path !!!!!");
    }

    System.out.println("\nImplement IDS");
    if (IDS(start, end, ourGraph)) {
      displayPath(start, end, ourGraph,"Implement IDS");
    } else {
      displayData("Implement IDS",null );
      System.out.println("Cannot find path !!!!!");
    }

    System.out.println("\nImplement UCS");
    Path optimalPath = UCS(start,end,ourGraph);
    if(optimalPath!=null)
    {
      displayOptimalPath(optimalPath);
    }
    else
    {
      displayData("Implement UCS",null );
      System.out.println("Cannot find path !!!!!");
    }
  }

  //handle displaying path onto screen and read, write file
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
  private static void writeDataToFile(String path,String nameAlgo)
  {
    try {
      FileWriter myWriter = new FileWriter("src/OutputData.txt",true);
      myWriter.write(nameAlgo+"\n");
      myWriter.write(path+"\n");
      myWriter.close();
      
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  private static void deleteOldDataInFileOutput(String startName, String endName)
  {
    try {
      FileWriter myWriter = new FileWriter("src/OutputData.txt");
      myWriter.write("");

      myWriter.close();
      
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    try {
      FileWriter myWriter = new FileWriter("src/OutputData.txt",true);
      myWriter.write("Start Node: "+ startName+"\n");
      myWriter.write("End Node: "+ endName+"\n");
      myWriter.close();
      
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  private static void displayPath(Node start, Node des, Graph graph,String nameAlgo) {
    ArrayList<Node> paths = new ArrayList<Node>();
    paths.add(des);
    Node parent = des.getParent();
    while (parent != null) {
      paths.add(parent);
      parent = parent.getParent();
    }
    Collections.reverse(paths);
    displayData(nameAlgo, paths);
    graph.clearParentForAllNodes();
  }

  private static void displayOptimalPath(Path path)
  {
    ArrayList<Node> nodes = path.getNodes();
    System.out.println("Total cost of optimal path: "+path.getTotalCost());
    displayData("Implement UCS", nodes);

  }
  private static void displayData(String nameAlgo, ArrayList<Node> paths) {
    if(paths==null){
      writeDataToFile("Cannot find path !!!!", nameAlgo);
      return;
    }
    StringBuilder pathDirect = new StringBuilder();
    for (int i =0;i<paths.size()-1;i++) {
      String n = paths.get(i).getName() + "->";
      pathDirect.append(n);
      System.out.print(n);
    }
    String n = paths.get(paths.size()-1).getName();
    pathDirect.append(n);
    System.out.print(n);
    writeDataToFile(pathDirect.toString(), nameAlgo);
  }
  
  // implement Breadth-first search
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
  // implement Depth-first search
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
  // implement Depth-limited search
  private static boolean DLS(Node start, Node des, Graph graph, int depthLimit) {
    if (start == null || des == null) {
      return false;
    }
    ArrayList<Node> visitedList = new ArrayList<>();
    int depth = -1;
    DLSRecusive(start, des, visitedList, depth, depthLimit);

    return des.getParent() != null;
  }

  private static void DLSRecusive(Node node, Node des, ArrayList<Node> visitedList, int depth, int depthLimit) {
    depth++;
    if (depth >= depthLimit) {
      return;
    }
    visitedList.add(node);
    for (Edge edge : node.getEdges()) {
      Node destVertex = edge.getDestVertex();
      if (!visitedList.contains(destVertex)) {
        destVertex.SetParent(node);
        if (destVertex.equals(des)) {
          return;
        }
        DLSRecusive(destVertex, des, visitedList, depth, depthLimit);

      }
    }
  }
  //implement Iterative deepening depth-first searc 
  private static int DLS2(Node start, Node des, Graph graph, int depthLimit) {
    if (start == null || des == null) {
      return 0;
    }
    ArrayList<Node> visitedList = new ArrayList<>();
    int depth = -1;
    DLSRecusive(start, des, visitedList, depth, depthLimit);
    
    return visitedList.size();
  }

  private static boolean IDS(Node start, Node des, Graph graph) {
    
    int depthLimit = 1;
    int lastLengthVisitedList = 0;
    int currentLengthVisitedList = 0;
    while(des.getParent()==null)
    {
      currentLengthVisitedList = DLS2(start, des, graph, depthLimit);
      if(currentLengthVisitedList==lastLengthVisitedList)
      {
        break;
      }
      lastLengthVisitedList = currentLengthVisitedList;
      depthLimit++;
    }
    return des.getParent() != null;
  }
  //implement Uniform-cost search
  private static Path UCS(Node start, Node des, Graph graph)
  {
    if(start==null||des==null){return null;}
    ArrayList<Node> visitedList = new ArrayList<>();
    PriorityQueue<Path> priorityQueue = new PriorityQueue<>(graph.getNodes().size(),new PathComparator());
    //start node
    Path startPath = new Path(0);
    startPath.addNode(start);
    priorityQueue.add(startPath);
    while(!priorityQueue.isEmpty())
    {
      Path oldPath = priorityQueue.poll();
      Node lastNode = oldPath.getLastNode();
      if(lastNode==des){return oldPath;}
      
      if(visitedList.contains(lastNode)){continue;}
      visitedList.add(lastNode);
      for(Edge edge: lastNode.getEdges())
      {
        Path path = new Path(oldPath.getTotalCost());
        path.addAlls(oldPath.getNodes());
        path.addNode(edge.getDestVertex());
        path.addTotalCost(edge.getCost());
        priorityQueue.add(path);
      }

    }
    return null;
  }

}
