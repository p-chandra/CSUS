package com.mycompany.a3.interfaces;

public interface IIterator
{
	public boolean hasNext();
	public Object getNext();
	public int getIndex();
	public void setIndex(int loc);
}
