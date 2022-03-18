//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// This lays out the functions that any graph will need to perform
//

public interface BasicGraphInterface<T> {
	
	/**
	 * Adds a vertex with the specified data
	 * @param vertexData
	 * 	An object that labels this verticie, must be unique to all other vertices
	 * @return
	 * 	True if vertex is succesfully added, false if not
	 */
	public boolean addVertex(T vertexData);
	
	/**
	 * Adds a weighted edge to the graph between 2 distinct vertices. In a directed graph, this will point from
	 * start to the end vertex
	 * @param start
	 * 	Specified vertex to start from, must be present in the graph
	 * @param end
	 * 	Specified vertex to end at, must be present in the graph
	 * @param edgeWeight
	 * 	Will be used in eighted graphs to determan an arbitrary value about the edge
	 * @return
	 * 	True if the edge is added, false if not
	 */
	public boolean addEdge(T start, T end, double edgeWeight);
	
	/**
	 * Adds an edge to the graph. In a directed graph, will point from start vertex to the end vertex
	 * @param start
	 * 	Specified vertex to start from, must be present in the graph
	 * @param end
	 * 	Specified vertex to end at, must be present in the graph
	 * @return
	 * 	True if the edge is added, false if not
	 */
	public boolean addEdge(T start, T end);
	
	/**
	 * Will remove an edge between the 2 specified vertices and will return false if the edge does not exist
	 * @param start
	 * 	The vertex that the edge starts from
	 * @param end
	 *  The vertex that the edge ends at
	 * @return
	 * 	Wether the operation was succesful or not
	 */
	public boolean removeEdge(T start, T end);
	
	/**
	 * Will test if there is already an edge specified between the input vertecies
	 * @param start
	 * 	Specified vertex to start from, must be present in the graph
	 * @param end
	 *	 Specified vertex to end at, must be present in the graph
	 * @return
	 * 	True if there is a vertex at the specified location, false if not
	 * 
	 */
	public boolean hasEdge(T start, T end);
	
	/**
	 * Tests if the graph is empty or not
	 * 
	 */
	public boolean isEmpty();
	
	/**
	 * Will return the number of vertices in the graph
	 * 
	 */
	public int getNumberOfVertices();
	
	/**
	 * Will return the number of edges defined in the graph
	 */
	public int getNumberOfEdges();
	
	/**
	 * Will wipe the graph of all data
	 */
	public void clear();
}
