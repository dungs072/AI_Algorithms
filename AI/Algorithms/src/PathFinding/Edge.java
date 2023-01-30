package PathFinding;

public class Edge implements Comparable<Edge> {
    private String startVertices;
    private String endVertices;
    private int cost = 0;
    public Edge(String startVertices, String endVertices, int cost)
    {
        this.startVertices = startVertices;
        this.endVertices = endVertices;
        this.cost = cost;
    }
    public String getStartVertices(){return startVertices;}
    public String getEndVertices(){return endVertices;}
    public int getCost(){return cost;}
    @Override
    public int compareTo(Edge o) {
        if(startVertices==o.getStartVertices()&&endVertices==o.getEndVertices()){return 0;}
        else return 1;
    }

}
