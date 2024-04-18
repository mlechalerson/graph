import java.util.*;

public class Graf {
    public List<Vertex> vertexes = new ArrayList<Vertex>();
    public List<Edge> edges = new ArrayList<Edge>();

    public Vertex getVertex(int id) {
        for (Vertex vertex : vertexes) {
            if (vertex.id == id) {
                return vertex;
            }
        }
        return null;
    }

    public List<Edge> EdgeConnection(int id) {
        List<Edge> tempEdge = new ArrayList<Edge>();

        for (Edge edge : edges) {
            if (id == edge.v1.id || id == edge.v2.id) {
                tempEdge.add(edge);
            }
        }

        return tempEdge.isEmpty() ? null : tempEdge;
    }

    public void addVertex(int id) {
        if (getVertex(id) == null) {
            this.vertexes.add(new Vertex(id));
            System.out.println("Utworzono nowy wierzcholek o id " + id);
        }
    }

    public void removeVertex(int id) {
        Vertex vertex = getVertex(id);
        if (vertex != null) {
            List<Edge> removingEdge = EdgeConnection(id);
            if (removingEdge != null) {
                for (Edge edge : removingEdge) {
                    System.out.println("Usunięto krawędź z wagą " + edge.weight + " i id1: " + edge.v1.id + " oraz id2: " + edge.v2.id);
                    edges.remove(edge);
                }
            }
            vertexes.remove(vertex);
            System.out.println("Usunięto wierzchołek o id: " + id);
        } else {
            System.out.println("Wierzchołek o id: " + id + " nie istnieje");
        }
    }

    public Edge getEdge(int e1, int e2) {
        for (Edge edge : edges) {
            if ((e1 == edge.v1.id && e2 == edge.v2.id) || (e1 == edge.v2.id && e2 == edge.v1.id)) {
                return edge;
            }
        }
        return null;
    }

    public void addEdge(int e1, int e2, int weight) {
        Vertex v1 = getVertex(e1);
        Vertex v2 = getVertex(e2);
        if (v1 != null && v2 != null) {
            if (getEdge(e1, e2) == null) {
                edges.add(new Edge(v1, v2, weight));
                System.out.println("Dodano krawędź między wierzchołkami " + e1 + " i " + e2);
            } else {
                System.out.println("Ta krawędź już istnieje");
            }
        } else {
            System.out.println("Jeden z wierzchołków nie istnieje");
        }
    }

    public void removeEdge(int e1, int e2) {
        Edge edge = getEdge(e1, e2);
        if (edge != null) {
            edges.remove(edge);
            System.out.println("Usunięto krawędź między wierzchołkami " + e1 + " i " + e2);
        } else {
            System.out.println("Nie znaleziono krawędzi między wierzchołkami " + e1 + " i " + e2);
        }
    }

    public Map<Vertex, Integer> dijkstra(Vertex start) {

        Map<Vertex, Integer> odleglosc = new HashMap<>();

        Map<Vertex, Vertex> prev = new HashMap<>();

        PriorityQueue<Vertex> pq = new PriorityQueue<>((v1, v2) -> odleglosc.get(v1) - odleglosc.get(v2));


        for (Vertex vertex : vertexes) {
            odleglosc.put(vertex, Integer.MAX_VALUE);
            prev.put(vertex, null);
        }


        odleglosc.put(start, 0);
        pq.add(start);


        while (!pq.isEmpty()) {
            Vertex current = pq.poll();
            List<Edge> neighbors = EdgeConnection(current.id);


            if (neighbors != null) {
                for (Edge edge : neighbors) {
                    Vertex next = edge.v1 == current ? edge.v2 : edge.v1;
                    int newDist = odleglosc.get(current) + edge.weight;
                    if (newDist < odleglosc.get(next)) {
                        odleglosc.put(next, newDist);
                        prev.put(next, current);
                        pq.add(next);
                    }
                }
            }
        }

        return odleglosc;
    }
}


