package com.example.circlething.model;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Polygon
{
	private int			_numOfEnds;
	private float		_radius;
	Point				_center;
	ArrayList<Point>	_ends;
	Paint				_paint;

	public Polygon(int numOfEnds, float radius, Point center, float rotateAngle, Paint paint)
	{
		_paint = paint;
		_ends = new ArrayList<Point>(numOfEnds);

		setPolygon(numOfEnds, radius, center, rotateAngle);
	}

	public void setPolygon(int numOfEnds, float radius, Point center)
	{
		setPolygon(numOfEnds, radius, center, 0);
	}

	public void update(float radius, float rotateAngle)
	{
		setPolygon(_numOfEnds, radius, _center, rotateAngle);
	}

	public void setPolygon(int numOfEnds, float radius, Point center, float rotateAngle)
	{

		_numOfEnds = numOfEnds;
		_radius = radius;
		_center = center;

		if (numOfEnds != 0)
		{
			float angle = (float) (2 * Math.PI / numOfEnds) + rotateAngle;
			int x = 0;
			int y = 0;

			for (int i = 1; i <= numOfEnds; i++)
			{
				angle = angle * i;

				x = (int) (Math.cos(angle) * radius) + center.x;
				y = (int) (Math.sin(angle) * radius) + center.y;

				Point p = new Point(x, y);
				_ends.add(p);
			}
		}
	}

	public void rotate(float rotateAngle)
	{
		setPolygon(_numOfEnds, _radius, _center, rotateAngle);
	}

	public void OnDrawe(Canvas canvas)
	{

		for (Point end : _ends)
		{

			canvas.drawLine(_center.x, _center.y, end.x, end.y, _paint);
		}
	}

	public float getRad()
	{
		return _radius;
	}

}
