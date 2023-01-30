package PathFinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class EdgeList {
    private ArrayList<Edge> edges;
    private HashSet<String> vertices;
    public EdgeList()
    {
        edges = new ArrayList<Edge>();
        vertices = new HashSet<>();
    }

    public void AddEdge(Edge edge)
    {
        if(checkHasSameEdgeInList(edge)){return;}
        edges.add(edge);
        edges.add(new Edge(edge.getEndVertices(), edge.getStartVertices(), edge.getCost()));
        vertices.add(edge.getStartVertices());
        vertices.add(edge.getEndVertices());
    }
    public void AddEdge(String startV, String endV, int cost)
    {
        Edge edge = new Edge(startV, endV,cost);
        AddEdge(edge);
    }
    public void SortEdgeList()
    {
        Collections.sort(edges);
    }
    public ArrayList<Edge> getEdges(){return edges;}
    public HashSet<String> getVertices(){return vertices;}
    public void DisplayData()
    {
        for(Edge e : edges)
        {
            System.out.println(e);
        }
        System.out.println("vertices");
        for(String v:vertices)
        {
            System.out.println(v);
        }
    }
    private boolean checkHasSameEdgeInList(Edge edge)
    {
        for(Edge e:edges)
        {
            if(e.compareTo(edge)==0){return true;}
        }
        return false;
    }
    public boolean checkHasVertice(String vertice)
    {
        for(String v: vertices)
        {
            if(vertice.equals(v)){return true;}
        }
        return false;
    }

}
