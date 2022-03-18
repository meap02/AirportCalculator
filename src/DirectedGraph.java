//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// The holy grail of this project, its the full implementation 
// of all of the tools that I need to make a working directed graph
// includes all traversal methods and some helper methods to get specific information
//
import java.util.Iterator;

/**
 * A directed graph class that can be used with or withough weights on the edges
 * @author kjjus
 *
 * @param <T>
 * 	The data used inside of each vertex of the graph
 */
public class DirectedGraph<T> implements GraphInterface<T> {
	
	//////////////////////////////////////////////////////////////////////////////
	//							Fields										   //
	////////////////////////////////////////////////////////////////////////////
	private DictionaryInterface<T, VertexInterface<T>> vertices;
	private int edgeCount;
	
	//////////////////////////////////////////////////////////////////////////////
	//							Constructors								   //
	////////////////////////////////////////////////////////////////////////////
	public DirectedGraph() {
		vertices = new LinkedDictionary<>();
		edgeCount = 0;
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//							Public Methods								   //
	////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean addVertex(T vertexLabel) {
		VertexInterface<T> addOutcome = //When the dictionary executes the add() it will return the value it put in or null
				vertices.add(vertexLabel, new Vertex<>(vertexLabel));
		return addOutcome != null; //We use that to tell if the operation was succesful
	}

	@Override
	public boolean addEdge(T start, T end, double edgeWeight) {
		boolean result = false;
		if (start != null && end != null && !Double.isNaN(edgeWeight)) {
			//these two declarations will extract the wanted vertices from the dictionary
			VertexInterface<T> startVertex = vertices.getValue(start);
			VertexInterface<T> endVertex = vertices.getValue(end);
			/*
			 * This little loop will ensure that the provided edge does not already exist
			 */
			Iterator<VertexInterface<T>> neighborIterator = startVertex.getNeighborIterator();
			while(neighborIterator.hasNext()) {
				if(neighborIterator.next().equals(endVertex))
					throw new RuntimeException("Edge from " + start + " to " + end + " already exists");
			}
			if (startVertex != null && endVertex != null) {
				result = startVertex.connect(endVertex, edgeWeight);
				edgeCount++;
			} 
		}else
			throw new IllegalArgumentException("Invalid Arguments to add and edge");
		return result;
	}

	@Override
	public boolean addEdge(T start, T end) {
		return addEdge(start,end, 0); //Little shortcut to write less code :)
	}
	
	@Override
	public boolean removeEdge(T start, T end) {
		VertexInterface<T> startVertex = vertices.getValue(start);
		VertexInterface<T> endVertex = vertices.getValue(end);
		edgeCount--;
		return startVertex.disconnect(endVertex);
	}

	@Override
	public boolean hasEdge(T start, T end) {
		boolean found = false;
		 //these two declarations will extract the wanted vertices from the dictionary
		VertexInterface<T> startVertex = vertices.getValue(start); 
		VertexInterface<T> endVertex = vertices.getValue(end);
		if(startVertex != null && endVertex != null) {
			//this will allow for the neighbors of the start vertex to be iterated through
			Iterator<VertexInterface<T>> neighbors = 
					startVertex.getNeighborIterator();
			while(neighbors.hasNext() && !found) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				//If the match is found, then the edge already exists
				if(nextNeighbor.equals(endVertex)) 
					found = true;
			}
		}
		return found;
	}

	@Override
	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	@Override
	public int getNumberOfVertices() {
		return vertices.getSize();
	}

	@Override
	public int getNumberOfEdges() {
		// TODO Auto-generated method stub
		return edgeCount;
	}

	@Override
	public void clear() {
		vertices.clear();
		edgeCount = 0;
	}
	
	/**
	 * Helper method used to find the vertex that is not a neighbor to any other
	 * @return
	 * 	Will return the vertex that is not a neighbor to any other, if it does not exist
	 * 	i.e the graph is cyclic, then will return null
	 */
	private VertexInterface<T> findOrigin(){
		boolean found = false;
		VertexInterface<T> foundVertex = null;
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		BagInterface<VertexInterface<T>> compareBag = new ArrayBag<>(vertices.getSize());
		/*
		 * This will find a vertex with no predecessors
		 */
		while(vertexIterator.hasNext() && foundVertex == null) {
			//Will make sure to check all of the vertexes in the graph
			VertexInterface<T> nextVertex = vertexIterator.next();
			Iterator<VertexInterface<T>> notTheVerteciesYouAreLookingFor = nextVertex.getNeighborIterator();
			while(notTheVerteciesYouAreLookingFor.hasNext()) {
				//Will check where each of the edges is pointing and add them to the bag
				VertexInterface<T> nextNeighbor = notTheVerteciesYouAreLookingFor.next();
				if(!nextNeighbor.isVisited()) {
					nextNeighbor.visit();
					compareBag.add(nextNeighbor);
				}
			}
		}
		
		vertexIterator = vertices.getValueIterator();
		while(vertexIterator.hasNext() && !found) {
			VertexInterface<T> nextVertex = vertexIterator.next();
			if(!compareBag.contains(nextVertex)) {
				found = true;
				foundVertex = nextVertex;
			}
		}
		resetVertices();
		
		return foundVertex;
	}
	
	/**
	 * This will check if the graph has cycles by using the depth first traversal.
	 * if any of the deepest points in the graph point to another vertex that came before it
	 * than there must be a cycle
	 * @return
	 *  whether the graph has a cycle or not
	 */
	protected boolean hasCycle() {
		boolean found = false;
		VertexInterface<T> origin = findOrigin();
		//This means that there was no vertex without a predecessor, so the entire graph is a cycle
		if(origin == null)
			return true;
		
		/*
		 * Using the depth traversal and comparing each of the vertecies edges with
		 * the vertices that have come previous, we can determine if a cycle is formed
		 */
		QueueInterface<T> depthTraversal= getDepthFirstTraversal(origin.getLabel());
		BagInterface<VertexInterface<T>> pastVertices = new ArrayBag<>(vertices.getSize());
		pastVertices.add(origin);
		
		/*
		 * this will search through the depth traversal as a queue and compare it with
		 * the past vertices as a bag. if a match is found, that means that there is a cycle
		 * and the method will return true
		 */
		while(!depthTraversal.isEmpty() && !found) {
			//Gets the next vertex in the depth traversal
			VertexInterface<T> frontVertex = vertices.getValue(depthTraversal.dequeue());
			pastVertices.add(frontVertex);
			//gets the neighbor iterator for the vertex
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
			//Iterate through the neighbors
			while(neighbors.hasNext() && !found) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if(pastVertices.contains(nextNeighbor)) {
					found = true;
				}
			}	
			
		}
		return found;
	}
	
	/**
	 * Used after each traversal to clean the vertices of the predecesor,
	 * cost and visted boolean that may have been changed
	 */
	protected void resetVertices() {
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext()) {
			VertexInterface<T> nextVertex = vertexIterator.next();
			nextVertex.unvisit();
			nextVertex.setCost(0);
			nextVertex.setPredecessor(null);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	//					Traversal and Sorting Algorithms					   //
	////////////////////////////////////////////////////////////////////////////


	@Override
	public QueueInterface<T> getBreadthFirstTraversal(T origin) {
		/*
		 * This is the queue that will be returned
		 */
		QueueInterface<T> traversal = new LinkedQueue<>();
		/*
		 * This queue will be used to process through the breadth traversal
		 */
		QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
		VertexInterface<T> originVertex = vertices.getValue(origin);
		originVertex.visit();
		vertexQueue.enqueue(originVertex);
		traversal.enqueue(origin);
		while(!vertexQueue.isEmpty()) {
			VertexInterface<T> frontVertex = vertexQueue.dequeue();
			Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
			
			while(neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if(!nextNeighbor.isVisited()) {
					vertexQueue.enqueue(nextNeighbor);
					traversal.enqueue(nextNeighbor.getLabel());
					nextNeighbor.visit();
				}
			}
		}
		
		resetVertices();
		
		return traversal;
	}

	@Override
	public QueueInterface<T> getDepthFirstTraversal(T origin) {
		/*
		 * This is the queue that will be returned
		 */
		QueueInterface<T> traversal = new LinkedQueue<>();
		/*
		 * This stack will be used to process through the depth traversal
		 */
		StackInterface<VertexInterface<T>> vertexs = new LinkedStack<>();
		//Start at the origin
		VertexInterface<T> originVertex = vertices.getValue(origin);
		originVertex.visit();
		vertexs.push(originVertex);
		traversal.enqueue(origin);
		//Proceed fronm the origin
		while(!vertexs.isEmpty()) {
			VertexInterface<T> topVertex = vertexs.peek();
			if(topVertex.hasUnvisitedNeighbor()) { //If there are any neighbors to the top of the stack that need to be visited
				VertexInterface<T> nextNeighbor = topVertex.getUnvisitedNeighbor();
				nextNeighbor.visit(); //Visit it
				vertexs.push(nextNeighbor);//push it on the stack
				traversal.enqueue(nextNeighbor.getLabel());//That is the next one in the traversal
			} else//all neighbors are visited
				vertexs.pop();
		}
		
		resetVertices();
		
		return traversal;
	}
	
	@Override
	public StackInterface<T> getTopologicalOrder() {
		
		VertexInterface<T> origin = findOrigin();
		if(origin == null || hasCycle())
			throw new RuntimeException("The specified graph has a cycle and therefore "
									+ "cannot have a valid topoligical ordering");
		/*
		 * the stack that will be returned, the vertex with
		 * no predecesors will be on top and the 
		 */
		StackInterface<T> vertexStack = new LinkedStack<>();
		int numberOfVertices = vertices.getSize();
		VertexInterface<T> nextVertex;
		for(int i = 1; i < numberOfVertices; i++) {
			nextVertex = origin;
			while(nextVertex.hasNeighbor() && nextVertex.hasUnvisitedNeighbor()) {
				nextVertex = nextVertex.getUnvisitedNeighbor();
			}
			nextVertex.visit();
			vertexStack.push(nextVertex.getLabel());
		}
		vertexStack.push(origin.getLabel());
		return vertexStack;
	}

	@Override
	public int getShortestPath(T start, T end, StackInterface<T> path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getCheapestPath(T start, T end, StackInterface<T> path) {
		boolean done = false;
		
		if (start != null && end !=null) {
			VertexInterface<T> startVertex = vertices.getValue(start); //get each actual vertex
			VertexInterface<T> endVertex = vertices.getValue(end);
			//Create a min heap to always have acces to lowest distance
			MinHeapInterface<VertexInterface<T>> heap = new ArrayMinHeap<>(vertices.getSize());
			VertexInterface<T> topVertex = null;
			heap.add(startVertex);
			startVertex.visit();
			while (!done && !heap.isEmpty()) {
				topVertex = heap.removeMin(); //Grab shortest path
				if (topVertex.equals(endVertex)) //If the path is complete then we are done
					done = true;
				else { //Otherwise we need to add each of its branching paths
					/*
					 * using both the iterators in tandem is ok because they use the same order of
					 * neighbors within their iterators
					 */
					Iterator<VertexInterface<T>> vertexIterator = topVertex.getNeighborIterator();
					Iterator<Double> weightIterator = topVertex.getWeightIterator();
					while (vertexIterator.hasNext()) { //While there is a neighbor
						VertexInterface<T> nextNeighbor = vertexIterator.next();
						double weightOfEdge = weightIterator.next();
						/*
						 * If this section is true then the vertex hasnt been visited and should be
						 * if false, that meas that a shorter path to that vertex has already been 
						 * found and we can skip it
						 */
						if (!nextNeighbor.isVisited() || (nextNeighbor.getCost() > (topVertex.getCost() + weightOfEdge))) { 
							nextNeighbor.setCost(topVertex.getCost() + weightOfEdge);//add the weights
							nextNeighbor.setPredecessor(topVertex);//set the path so we can track it later
							nextNeighbor.visit();//make sure no other path can touch this node
							heap.add(nextNeighbor);//and finally add it to the minHeap
						}
					} //End of iterator section
				} //End of "if at the end of the path" statement
			} //End of while, traversal ends here
			
			if (done) { //If the heap becomes empty and done is still false, then there is no way to get to the airport
				double pathCost = topVertex.getCost();
				path.push(end);
				VertexInterface<T> lastVertex = topVertex;
				while (lastVertex.hasPredecessor()) {
					lastVertex = lastVertex.getPredecessor();
					path.push(lastVertex.getLabel());
				}
				resetVertices();//Always reset vetices for the next traversal
				return pathCost;
			/*
			 * if all of the vertices have been visited and we did not end on the\
			 * desired vertex, then a path does not exist
			 */
			}else {
				resetVertices();//No matter what
				throw new RuntimeException("There is no valid path with the given parameters");
			}
		}else {
			resetVertices(); //no matter what
			throw new RuntimeException("Start and end points cannot be null");
		}
	}
}//END OF DIRECTED GRAPH CLASS
