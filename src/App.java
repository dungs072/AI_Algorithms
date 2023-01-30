import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import PathFinding.Edge;
import PathFinding.EdgeList;

public class App {
  public static void main(String[] args) throws Exception {
    //initial
    EdgeList edgeList = new EdgeList();
    HashMap<String,String> visitedHashList = new HashMap<String,String>();
    loadDataFromFile(edgeList);
    edgeList.SortEdgeList();
    String startV = "D";
    String desV = "B";

    //implement FBS
    if(FBS(startV,desV, edgeList,visitedHashList))
    {
      displayPath(startV, desV, visitedHashList);
    }
  }

  private static void loadDataFromFile(EdgeList edgeList) {
    try {
      File myObj = new File("src/PathFinding/InputData.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        data = data.substring(1, data.length()-1);
        String[] texts = data.split(",");
        edgeList.AddEdge(texts[0], texts[1], Integer.parseInt(texts[2]));
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  private static void displayPath(String startV,String desV,HashMap<String,String> visitedHashList)
  {
    ArrayList<String> paths=  new ArrayList<>();
    String parent = visitedHashList.get(desV);
    paths.add(desV);
    while(!startV.equals(parent))
    {
      paths.add(parent);
      parent = visitedHashList.get(parent); 
    }
    paths.add(startV);
    Collections.reverse(paths);
    for(String v: paths)
    {
      System.out.print(v+" ");
    }
    
    
  }
  private static boolean FBS(String startV, String desV, EdgeList edgeList,HashMap<String,String> visitedHashList)
  {
      if(!edgeList.checkHasVertice(startV)||!edgeList.checkHasVertice(desV))
      {
          System.out.println("Cannot find path !!!!!");
          return false;
      }
      Queue<String> queue = new LinkedList<>();
      queue.add(startV);
      while(!queue.isEmpty())
      {
        String v = queue.poll();
        
        for(Edge e:edgeList.getEdges())
        {
            if(v.equals(e.getStartVertices()))
            {
              if(visitedHashList.containsKey(e.getEndVertices())){continue;}
              queue.add(e.getEndVertices());
              visitedHashList.put(e.getEndVertices(), v);
              if(e.getEndVertices().equals(desV))
              {
                return true;
              }
            }
        }
      }
      System.out.println("Cannot find path !!!!!");
      
      return false;
  }
}
