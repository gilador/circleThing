package com.example.circlething;

import java.util.ArrayList;

import com.example.circlething.model.Polygon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

public class DrawingPanel extends SurfaceView implements SurfaceHolder.Callback
{

	// Point _center;
	// Point _end;
	// int _rad = 100;
	// ArrayList<Point> _ends;
	// ArrayList<Integer> _ends;
	ArrayList<Polygon>	_polygons;

	DrawingPanelThread	_thread;
	private Point		_screenDim;
	private Paint		_paintLine;
	private Paint		_paintBack;
	private Point		_end2;

	public DrawingPanel(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public DrawingPanel(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public DrawingPanel(Context context)
	{
		super(context);

		init(context);

		// TODO Auto-generated constructor stub
	}

	private void init(Context context)
	{
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		_paintLine = new Paint();
		_paintLine.setColor(Color.WHITE);

		_polygons = new ArrayList<Polygon>();

		_polygons.add(new Polygon(1, 100, new Point(300, 300), 0, _paintLine));

		// _paintLine.setFlags(Paint.ANTI_ALIAS_FLAG);
		//
		// _paintBack = new Paint();
		// _paintBack.setColor(Color.BLACK);
		// _paintBack.setFlags(Paint.ANTI_ALIAS_FLAG);
		//
		// setBackgroundColor(Color.WHITE);
		// _screenDim = new Point();
		// int x = getWidth();
		// // _ends = new ArrayList<Point>();
		//
		// WindowManager wm = (WindowManager)
		// context.getSystemService(Context.WINDOW_SERVICE);
		// Display display = wm.getDefaultDisplay();
		// display.getSize(_screenDim);
		//
		// _center = new Point(_screenDim.x / 2, _screenDim.y / 2);
		//
		// _end = new Point((_screenDim.x / 2) + _rad, (_screenDim.y / 2) +
		// _rad);
		// _end2 = new Point((_screenDim.x / 2) - _rad, (_screenDim.y / 2) -
		// _rad);

	}

	@Override
	public void onDraw(Canvas canvas)
	{

		for (Polygon polygon : _polygons)
		{

			polygon.OnDrawe(canvas);
		}
		// canvas.drawCircle(_center.x, _center.y, _rad, _paintBack);
		// canvas.drawLine(_center.x, _center.y, _end.x, _end.y, _paintLine);
		// canvas.drawLine(_center.x, _center.y, _end2.x, _end2.y, _paintLine);
		// canvas.drawLine(_center.x, _center.y, _end.x, _end2.y, _paintLine);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{

		setWillNotDraw(false); // Allows us to use invalidate() to call onDraw()

		_thread = new DrawingPanelThread(getHolder()); // Start the thread that
		_thread.setRunning(true); // will make calls to
		_thread.start(); // onDraw()
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		try
		{
			_thread.setRunning(false); // Tells thread to stop
			_thread.join(); // Removes thread from mem.
		} catch (InterruptedException e)
		{
		}
	}

	class DrawingPanelThread extends Thread
	{
		private SurfaceHolder	_surfaceHolder;
		private boolean			_run	= false;

		public DrawingPanelThread(SurfaceHolder surfaceHolder)
		{
			_surfaceHolder = surfaceHolder;
		}

		public void setRunning(boolean run)
		{ // Allow us to stop the thread
			_run = run;
		}

		@Override
		public void run()
		{
			Canvas c;
			// float angle1;
			// float angle2;

			// angle1 = (float) Math.atan2(_center.x - _end.x, _center.y -
			// _end.y);
			//
			// angle2 = (float) Math.atan2(_center.x - _end2.x, _center.y -
			// _end2.y);

			float rot = 0;

			while (_run)
			{ // When setRunning(false) occurs, _run is
				c = null; // set to false and loop ends, stopping thread

				try
				{

					c = _surfaceHolder.lockCanvas(null);
					synchronized (_surfaceHolder)
					{
						for (Polygon polygon : _polygons)
						{
							rot += (float) Math.toRadians(1);
							polygon.update(polygon.getRad(), rot);
						}

						// angle1 += Math.toRadians(1);
						// angle2 += Math.toRadians(1);
						//
						// int newX = (int) (Math.cos(angle1) * _rad) +
						// _center.x;
						// int newY = (int) (Math.sin(angle1) * _rad) +
						// _center.y;
						//
						// _end.x = newX;
						// _end.y = newY;
						//
						// int newX2 = (int) (Math.cos(angle2) * _rad) +
						// _center.x;
						// int newY2 = (int) (Math.sin(angle2) * _rad) +
						// _center.y;
						//
						// _end2.x = newX2;
						// _end2.y = newY2;

						postInvalidate();

					}
				} finally
				{
					if (c != null)
					{
						_surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

}
