package com.mycompany.a3.view;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.interfaces.IDrawable;
import com.mycompany.a3.interfaces.IIterator;

public class MapView extends Container implements Observer
{
	GameWorld gw;
	
	public MapView(GameWorld gw)
	{
		this.gw = gw;
		this.getAllStyles().setBgTransparency(255);
		getAllStyles().setBorder(Border.createLineBorder(3,
				ColorUtil.BLACK));
		getStyle().setBgColor(ColorUtil.BLACK);
		
	}

	public void paint(Graphics g)
	{
		super.paint (g);
		
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		gXform.translate(getAbsoluteX(),getAbsoluteY());
		gXform.translate(0, getHeight());
		gXform.scale(1, -1);
		gXform.translate(-getAbsoluteX(),-getAbsoluteY());
		g.setTransform(gXform);
		
		
		Point pCmpRelPrnt = new Point();
		pCmpRelPrnt.setX(getX());
		pCmpRelPrnt.setY(getY());
		IIterator it = gw.getGameObjects().getIterator();
		while(it.hasNext())
		{
			Object obj = it.getNext();
			
			if(obj instanceof IDrawable)
			{
				IDrawable drawable = (IDrawable) obj;
				drawable.draw(g, pCmpRelPrnt);
			}
		}
		g.resetAffine();
	}
	
	public void update(Observable observable, Object data)
	{
		this.repaint();
	}

}
