package com.mycompany.a2.utility;

import java.util.Random;

public class Util
{
	// creates random int
	public static int randInt(int min, int max)
	{
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
