package PathFinding;

import java.util.ArrayList;

public class EdgeList {
    private ArrayList<Edge> edges;

    public EdgeList()
    {
        edges = new ArrayList<Edge>();
    }

    public void AddEdge(Edge edge)
    {
        if(checkHasSameEdgeInList(edge)){return;}
        edges.add(edge);
    }
    public void AddEdge(String startV, String endV, int cost)
    {
        Edge edge = new Edge(startV, endV,cost);
        AddEdge(edge);
    }
    private boolean checkHasSameEdgeInList(Edge edge)
    {
        for(Edge e:edges)
        {
            if(e==edge){return true;}
        }
        return false;
    }

}
