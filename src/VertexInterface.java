//
// Name: Just, Kyle
// Project: #5
// Due: 05/13/2021
// Course: cs-2400-03-s21
//
// Description:
// Lays out the functions for a vertex to perform
//

import java.util.Iterator;

public interface VertexInterface<T> extends Comparable<VertexInterface<T>> {
	
	public T getLabel();
	
	public void visit();
	
	public void unvisit();
	
	public boolean isVisited();
	
	public boolean connect(VertexInterface<T> endVertex, double weight);
	
	public boolean connect(VertexInterface<T> endVertex);
	
	public boolean disconnect(VertexInterface<T> endVertex);

	public Iterator<VertexInterface<T>> getNeighborIterator();
	
	public Iterator<Double> getWeightIterator();
	
	public boolean hasNeighbor();
	
	public boolean hasUnvisitedNeighbor();
	
	public VertexInterface<T> getUnvisitedNeighbor();
	
	public void setPredecessor(VertexInterface<T> predecessor);
	
	public VertexInterface<T> getPredecessor();
	
	public boolean hasPredecessor();
	
	public void setCost(double newCost);
	
	public double getCost();
}
