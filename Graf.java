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
    public List<Edge> kruskal() {
        List<Edge> mst = new ArrayList<>();
        Collections.sort(edges, Comparator.comparingInt(edge -> edge.weight));
        UnionFind uf = new UnionFind(vertexes.size());

        for (Edge edge : edges) {
            int root1 = uf.find(edge.v1.id);
            int root2 = uf.find(edge.v2.id);
            if (root1 != root2) {
                mst.add(edge);
                uf.union(root1, root2);
            }
        }

        return mst;
    }

    static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int p) {
            if (parent[p] != p) {
                parent[p] = find(parent[p]);
            }
            return parent[p];
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP != rootQ) {
                if (rank[rootP] > rank[rootQ]) {
                    parent[rootQ] = rootP;
                } else if (rank[rootP] < rank[rootQ]) {
                    parent[rootP] = rootQ;
                } else {
                    parent[rootQ] = rootP;
                    rank[rootP]++;
                }
            }
        }
    }
    public List<Edge> prim(int startId) {
        Vertex start = getVertex(startId);
        if (start == null) return null;

        List<Edge> mst = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
        Set<Vertex> inMST = new HashSet<>();

        inMST.add(start);
        pq.addAll(EdgeConnection(start.id));

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            Vertex v1 = edge.v1;
            Vertex v2 = edge.v2;

            if (inMST.contains(v1) && inMST.contains(v2)) {
                continue;
            }

            mst.add(edge);
            Vertex newVertex = inMST.contains(v1) ? v2 : v1;
            inMST.add(newVertex);

            for (Edge nextEdge : EdgeConnection(newVertex.id)) {
                if (!inMST.contains(nextEdge.v1) || !inMST.contains(nextEdge.v2)) {
                    pq.add(nextEdge);
                }
            }
        }

        return mst;
    }
    public int greedyColoring() {
        Map<Vertex, Integer> colorMap = new HashMap<>();
        int maxColor = 0;

        for (Vertex vertex : vertexes) {
            Set<Integer> neighborColors = new HashSet<>();
            for (Edge edge : EdgeConnection(vertex.id)) {
                Vertex neighbor = (edge.v1 == vertex) ? edge.v2 : edge.v1;
                if (colorMap.containsKey(neighbor)) {
                    neighborColors.add(colorMap.get(neighbor));
                }
            }

            int color = 1;
            while (neighborColors.contains(color)) {
                color++;
            }

            colorMap.put(vertex, color);
            maxColor = Math.max(maxColor, color);
        }

        return maxColor;
    }

}



