import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import PathFinding.EdgeList;

public class App {
  public static void main(String[] args) throws Exception {
    EdgeList edgeList = new EdgeList();
    loadDataFromFile(edgeList);
    edgeList.DisplayData();
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
}
