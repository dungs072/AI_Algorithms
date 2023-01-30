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
    public String toString()
    {
        return String.format("(%s,%s,%s)", startVertices,endVertices,Integer.toString(cost));
    }
    @Override
    public int compareTo(Edge o) {
        if((startVertices.equals(o.getStartVertices())&&endVertices.equals(o.getEndVertices()))||
        (startVertices.equals(o.getEndVertices())&&endVertices.equals(o.getStartVertices()))){return 0;}
        
        int value = startVertices.compareTo(o.getStartVertices());
        if(value!=0){return value;}
        return 1;
    }

}
