
import java.util.List;
import java.util.ArrayList;



    public class Graf {
        public List<Vertex> vertexes = new ArrayList<Vertex>();

        public List<Edge> edges = new ArrayList<Edge>();

        public Vertex getVertex(int id) {
            if (!vertexes.isEmpty()) {
                for (int i = 0; i < vertexes.size(); i++) {
                    if (vertexes.get(i).id == id) {
                        return vertexes.get(i);

                    }
                }
            }
            return null;
        }
        public List<Edge> EdgeConnection(int id) {
            List<Edge> tempEdge = new ArrayList<Edge>();

            if (edges != null) {
                for (int i = 0; i < edges.size(); i++) {
                    if (id == edges.get(i).v1.id || id == edges.get(i).v2.id) {
                        edges.add(edges.get(i));
                    }
                }
            }else{
                return null;
            }

            return edges;
        }

        public void addVertex(int id) {
            if (vertexes.get(id) == null) {
                this.vertexes.add(new Vertex(id));
                System.out.println("Utworzono nowy wierzcholek o id " + id);
            }

        }

        public Edge removeVertex(int id) {
            Vertex vertex = getVertex(id);
            if (vertex != null) {
                List<Edge> removingEdge = EdgeConnection(id);
                if (removingEdge != null) {
                    for (int i = 0; i < removingEdge.size(); i++) {
                        System.out.println("Usunięto krawędź z wagą" + removingEdge.get(i).weight + " i id1: " + removingEdge.get(i).v1.id + "oraz id2" + removingEdge.get(i).v2.id);
                        edges.remove(removingEdge.get(id));

                    }
                }
                vertexes.remove(vertex);
                System.out.println("usunięto wierzchołek z id: " + id);
            }
            else {
                System.out.println("Wierzchołek z id: " + id + " " + "nie istnieje");
            }



            public Edge getEdge(int e1, int e2) {
                if (this.edges != null) {
                    for (int i = 0; i < this.edges.size(); i++) {
                        if (e1 == edges.get(i).v1.id && e2 == edges.get(i).v2.id) {
                            return edges.get(i);
                        }
                    }
                }
                return null;
            }

            public void addEdge(int e1, int e2, int weight) {
            Vertex v1 = getEdge(e1);
            Vertex v2 = getEdge(e2);
            if (v1 != null && v2 != null) {
                edges.add(new Edge(v1, v2, weight));
                System.out.println("Dodano krawędź v1 o wartości " + v1 + "i krawędź v2 o wartości " + v2);
            } else {
                System.out.println("Ta krawędź już istnieje");
            }
        }





            public void removeEdge(int e1,int e2){
                if (getEdge(e1, e2) != null) {
                    edges.remove(getEdge(e1, e2));
                    System.out.println("Usunieto krawedz przy wierzcholkach " + e1 + "i " + e2);
                } else {
                    System.out.println("Nie znaleziono krawedz przy wierzcholkach " + e1 + "i " + e2);
                }

            }
    }



