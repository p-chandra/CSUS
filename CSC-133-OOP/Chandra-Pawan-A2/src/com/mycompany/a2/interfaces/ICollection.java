package com.mycompany.a2.interfaces;

import com.mycompany.a2.objects.GameObject;

public interface ICollection
{
	public void add(GameObject object);
	public IIterator getIterator();
}
