import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class Astar{

        //h scores is the stright-line distance from the current city to Bucharest
        public static void main(String[] args){

                //initialize the graph base on the Romania map
                Node n1 = new Node("Arad",366);
                Node n2 = new Node("Bucharest",0);
                Node n3 = new Node("Craiova",160);
                Node n4 = new Node("Dobreta",242);
                Node n5 = new Node("Eforie",161);
                Node n6 = new Node("Fagaras",176);
                Node n7 = new Node("Giurgiu",77);
                Node n8 = new Node("Hirsova",151);
                Node n9 = new Node("Iasi",266);
                Node n10 = new Node("Lugoj",244);
                Node n11 = new Node("Mehadia",241);
                Node n12 = new Node("Neamt",234);
                Node n13 = new Node("Oradea",380);
                Node n14 = new Node("Pitesti",100);
                Node n15 = new Node("Rimnicu Vilcea",193);
                Node n16 = new Node("Sibiu",253);
                Node n17 = new Node("Timisoara",329);
                Node n18 = new Node("Urziceni",80);
                Node n19 = new Node("Vaslui",199);
                Node n20 = new Node("Zerind",374);
 
                //initialize the edges

                //Arad
                n1.adjacencies = new Edge[]{
                        new Edge(n20,75),
                        new Edge(n16,140),
                        new Edge(n17,118)
                };

 		//Bucharest
                n2.adjacencies = new Edge[]{
                        new Edge(n14,101),
                        new Edge(n7,90)
                };

 		//Craiova
                n3.adjacencies = new Edge[]{
                        new Edge(n4,120),
                        new Edge(n15,146),
                        new Edge(n14,138)
                };

		//Drobeta
                n4.adjacencies = new Edge[]{
                        new Edge(n11,75),
                        new Edge(n3,120)
                };

		//Eforie
                n5.adjacencies = new Edge[]{
                        new Edge(n8,86)
                };

 		//Fagaras
                n6.adjacencies = new Edge[]{
                       new Edge(n16,99),
                       new Edge(n2,211)
                };
                 
		//Giurgiu
                n7.adjacencies = new Edge[]{
                        new Edge(n2,90)
                };

		//Hirsova
                n8.adjacencies = new Edge[]{
                        new Edge(n18,98),
                        new Edge(n5,86)
                };

		//Iasi
                n9.adjacencies = new Edge[]{
                        new Edge(n19,92),
                        new Edge(n12,87)
                };

		//Lugoj
                n10.adjacencies = new Edge[]{
                        new Edge(n17,111),
                        new Edge(n11,70)
                };

		//Mehadia
                n11.adjacencies = new Edge[]{
                        new Edge(n10,70),
                        new Edge(n4,75)
                };


		//Neamt
                n12.adjacencies = new Edge[]{
                        new Edge(n9,87)
                };

		//Oradea
                n13.adjacencies = new Edge[]{
                        new Edge(n20,71),
                        new Edge(n16,151)
                };

 		//Pitesti
                n14.adjacencies = new Edge[]{
                        new Edge(n15,146),
                        new Edge(n2,101),
                        new Edge(n3,138)
                };

		//Rimnicu Vilcea
                n15.adjacencies = new Edge[]{
                        new Edge(n16,80),
                        new Edge(n14,97),
                        new Edge(n3,146)
                };

 		//Sibiu
                n16.adjacencies = new Edge[]{
                        new Edge(n15,80),
                        new Edge(n6,99),
                        new Edge(n13,151)
                };

                //Timisoara
                n17.adjacencies = new Edge[]{
                        new Edge(n1,118),
                        new Edge(n10,111)
                };

		//Urziceni
                n18.adjacencies = new Edge[]{
                        new Edge(n2,85),
                        new Edge(n19,142),
			new Edge(n8,98)
                };

		//Vaslui
                n19.adjacencies = new Edge[]{
                        new Edge(n9,92),
                        new Edge(n18,142)
                };
                 

                //Zerind
                n20.adjacencies = new Edge[]{
                        new Edge(n13,71),
                        new Edge(n1,75)
                };

                AstarSearch(n10,n2);

                List<Node> path = printPath(n2);

                System.out.println("Path: " + path);

        }

        public static List<Node> printPath(Node target){
            List<Node> path = new ArrayList<Node>();
        
            for(Node node = target; node!=null; node = node.parent){
                path.add(node);
            }

            Collections.reverse(path);

            return path;
        }

        public static void AstarSearch(Node source, Node goal){

                Set<Node> explored = new HashSet<Node>();

                PriorityQueue<Node> queue = new PriorityQueue<Node>(20, 
                        new Comparator<Node>(){
                                 //override compare method
                 public int compare(Node i, Node j){
                    if(i.f_scores > j.f_scores){
                        return 1;
                    }

                    else if (i.f_scores < j.f_scores){
                        return -1;
                    }

                    else{
                        return 0;
                    }
                 }

                        }
                        );

                //cost from start
                source.g_scores = 0;

                queue.add(source);

                boolean found = false;

                while((!queue.isEmpty())&&(!found)){

                        //the node in having the lowest f_score value
                        Node current = queue.poll();

                        explored.add(current);

                        //goal found
                        if(current.value.equals(goal.value)){
                                found = true;
                        }

                        //check every child of current node
                        for(Edge e : current.adjacencies){
                                Node child = e.target;
                                double cost = e.cost;
                                double temp_g_scores = current.g_scores + cost;
                                double temp_f_scores = temp_g_scores + child.h_scores;


                                /*if child node has been evaluated and 
                                the newer f_score is higher, skip*/
                                
                                if((explored.contains(child)) && 
                                        (temp_f_scores >= child.f_scores)){
                                        continue;
                                }

                                /*else if child node is not in queue or 
                                newer f_score is lower*/
                                
                                else if((!queue.contains(child)) || 
                                        (temp_f_scores < child.f_scores)){

                                        child.parent = current;
                                        child.g_scores = temp_g_scores;
                                        child.f_scores = temp_f_scores;

                                        if(queue.contains(child)){
                                                queue.remove(child);
                                        }

                                        queue.add(child);

                                }

                        }

                }

        }
        
}

class Node{

        public final String value;
        public double g_scores;
        public final double h_scores;
        public double f_scores = 0;
        public Edge[] adjacencies;
        public Node parent;

        public Node(String val, double hVal){
                value = val;
                h_scores = hVal;
        }

        public String toString(){
                return value;
        }

}

class Edge{
        public final double cost;
        public final Node target;

        public Edge(Node targetNode, double costVal){
                target = targetNode;
                cost = costVal;
        }
}