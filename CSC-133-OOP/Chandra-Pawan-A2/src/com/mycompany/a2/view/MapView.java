package com.mycompany.a2.view;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.plaf.Border;

public class  MapView extends Container implements Observer
{
	public MapView()
	{
		getAllStyles().setBorder(Border.createLineBorder(3,
				ColorUtil.LTGRAY));
		getStyle().setBgColor(ColorUtil.LTGRAY);
	}
	
	public void update(Observable observable, Object data)
	{
		// TODO Auto-generated method stub
		
	}

}
