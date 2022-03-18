//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// Gives all of the algoriths that ca be performed on a graph
//

public interface GraphAlgorithmsInterface<T> {
	
	/**
	 * Will return the breadth first traversal of the graph
	 * @param origin
	 * 	The point that the traversal will start from
	 * @return
	 * 	A queue in the order of the breadth first traversal starting with the
	 * 	specified origin
	 */
	public QueueInterface<T> getBreadthFirstTraversal(T origin);
	
	/**
	 * Will return the depth first traversal of the graph
	 * @param origin
	 * 	The point that the traversal will start from
	 * @return
	 * 	A queue in the order of the breadth first traversal starting with the
	 * 	specified origin
	 */
	public QueueInterface<T> getDepthFirstTraversal(T origin);
	
	/**
	 * Will get the order of vertices in the order that they would point to
	 * with no vertex being availible untill all vertices pointing to it have 
	 * been visited. Will not run for graphs with cycles
	 * @return
	 * 	The topological order of the graph in the form of a stack 
	 * 	the vertex that is not a neighbor to any vertex will be on top
	 */
	public StackInterface<T> getTopologicalOrder();

	/**
	 * Will get the path between the start and end vertices that has the least edges
	 * @param start
	 * 	Defined vertex to start at
	 * @param end
	 * 	Defined vertex to end at
	 * @param path
	 * 	This will be a stack that will start empty but at the completion of the method it will be loaded
	 * 	with the chain of vertices taken for the shortest parth. The start vertex will be on top and the end will be on bottom.
	 * @return
	 * 	The length of the shortest path
	 */
	public int getShortestPath(T start, T end, StackInterface<T> path);
	
	/**
	 * Will get the path between the start and end vertices that has the least weight to its edges
	 * @param start
	 * 	Defined vertex to start at
	 * @param end
	 * 	Defined vertex to end at
	 * @param path
	 * 	This will be a stack that will start empty but at the completion of the method it will be loaded
	 * 	with the chain of vertices taken for the shortest parth. The start vertex will be on top and the end will be on bottom.
	 * @return
	 * 	The sum of the weights of the shortest path
	 */
	public double getCheapestPath(T start, T end, StackInterface<T> path);
}
