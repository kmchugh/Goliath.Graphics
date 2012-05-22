 /* =========================================================
 * Polygon.java
 *
 * Author:      kmchugh
 * Created:     11-Feb-2008, 09:33:22
 *
 * Description
 * --------------------------------------------------------
 * A Polygon is a list of points that define a closed shape
 * The order of the points is important as it defines the shape
 * The points are drawn in clockwise order
 *
 * Change Log
 * --------------------------------------------------------
 * Init.Date        Ref.            Description
 * --------------------------------------------------------
 *
 * =======================================================*/

package Goliath.Graphics;

import Goliath.Collections.List;
import Goliath.Exceptions.InvalidParameterException;
import Goliath.Interfaces.Collections.IList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author kenmchugh
 */
public class Polygon extends Shape
        implements IList<Point>
{
    private List<Point> m_oPoints;

    public Polygon(Point toLocation, Point[] toPoints)
    {
        super(toLocation);
        m_oPoints = new List<Point>(toPoints);
    }

    public Polygon(Point toLocation)
    {
        super(toLocation);
        m_oPoints = new List<Point>();
    }


    /**
     * List implementation
     */

    @Override
    public String toString()
    {
        return m_oPoints.toString();
    }

    @Override
    public boolean retainAll(Collection<?> arg0)
    {
        return m_oPoints.retainAll(arg0);
    }

    @Override
    public boolean removeAll(Collection<?> arg0)
    {
        return m_oPoints.removeAll(arg0);
    }

    @Override
    public boolean containsAll(Collection<?> arg0)
    {
        return m_oPoints.containsAll(arg0);
    }

    @Override
    public java.util.List<Point> subList(int arg0, int arg1)
    {
        return m_oPoints.subList(arg0, arg1);
    }

    @Override
    public ListIterator<Point> listIterator(int arg0)
    {
        return m_oPoints.listIterator(arg0);
    }

    @Override
    public ListIterator<Point> listIterator()
    {
        return m_oPoints.listIterator();
    }

    @Override
    public Iterator<Point> iterator()
    {
        return m_oPoints.iterator();
    }

    @Override
    public int hashCode()
    {
        return m_oPoints.hashCode();
    }

    @Override
    public boolean equals(Object arg0)
    {
        return m_oPoints.equals(arg0);
    }

    @Override
    public <T> T[] toArray(T[] arg0)
    {
        return m_oPoints.toArray(arg0);
    }

    @Override
    public Object[] toArray()
    {
        return m_oPoints.toArray();
    }

    @Override
    public int size()
    {
        return m_oPoints.size();
    }

    @Override
    public Point set(int arg0, Point arg1)
    {
        return m_oPoints.set(arg0, arg1);
    }

    @Override
    public boolean remove(Object arg0)
    {
        return m_oPoints.remove(arg0);
    }

    @Override
    public Point remove(int arg0)
    {
        return m_oPoints.remove(arg0);
    }

    @Override
    public int lastIndexOf(Object arg0)
    {
        return m_oPoints.lastIndexOf(arg0);
    }

    @Override
    public boolean isEmpty()
    {
        return m_oPoints.isEmpty();
    }

    @Override
    public int indexOf(Object arg0)
    {
        return m_oPoints.indexOf(arg0);
    }

    @Override
    public Point get(int arg0)
    {
        return m_oPoints.get(arg0);
    }

    @Override
    public boolean contains(Object arg0)
    {
        return m_oPoints.contains(arg0);
    }

    @Override
    public void clear()
    {
        m_oPoints.clear();
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends Point> arg1)
    {
        return m_oPoints.addAll(arg0, arg1);
    }

    @Override
    public boolean addAll(Collection<? extends Point> arg0)
    {
        return m_oPoints.addAll(arg0);
    }

    @Override
    public boolean add(Point arg0)
    {
        return m_oPoints.add(arg0);
    }

    @Override
    public void add(int tnIndex, Point toElement)
    {
        m_oPoints.add(tnIndex, toElement);
    }
    
    /**
     * Checks if the polygon specified is in the rectangle specified
     * @param toShape the polygon to check
     * @param toHitShape the rectangle to check is inside (or intersects) the polygon
     * @param tlIntersect if true, this will also check for intersection polygons not just fully contained
     * @return true if the polygon is in the rectangle specified
     */
    public static boolean hitTest(Polygon toShape, Polygon toHitShape, boolean tlIntersect)
    {
        // TODO: For interseting hit test, we also need to test to see if the toShape is contained by toHitShape, this should be considered a hit
        boolean llReturn = false;
        
        Rectangle loShapeBounds = Polygon.getBoundingRectangle(toShape);
        
        Point loShapeLocation = toShape.getLocation();
        Point loHitLocation = toHitShape.getLocation();
        float lnShapeX = loShapeLocation.getX();
        float lnShapeY = loShapeLocation.getY();
        float lnShapeWidth = loShapeBounds.getDimension().getWidth();
        float lnShapeHeight = loShapeBounds.getDimension().getHeight();
        float lnHitX = loHitLocation.getX();
        float lnHitY = loHitLocation.getY();
        
        // Coarse hit test
        for (Point loPoint : toHitShape)
        {
            boolean llPoint = (loPoint.getX() + lnHitX  >= lnShapeX &&
                               loPoint.getY() + lnHitY  >= lnShapeY &&
                               loPoint.getX() + lnHitX  <= lnShapeX + lnShapeWidth &&
                               loPoint.getY() + lnHitY  <= lnShapeY + lnShapeHeight);
            
            // TODO: Implement fine grain checks here
            
            // If any point is in the polygon, and we are checking for intersect, then we can return
            if (llPoint && tlIntersect)
            {
                return true;
            }
            
            // If any point is outside the polygon and we are not looking for intersect, then return
            if (!llPoint && !tlIntersect)
            {
                return false;
            }
            
            llReturn = llPoint && llReturn;
        }
        return llReturn;
    }


    /**
     * Checks if the point specified is inside the polygon specified
     * @param toPolygon the polygon to check
     * @param toPoint the point to check
     * @return true if the point is in the polygon, otherwise false
     */
    public static boolean hitTest(Polygon toPolygon, Point toPoint)
    {
        Rectangle loBounds = getBoundingRectangle(toPolygon);
        boolean llReturn = false;
        
        float lnX = loBounds.getLocation().getX();
        float lnY = loBounds.getLocation().getY();

        llReturn = (toPoint.getX() >= lnX
                && toPoint.getX() <= lnX + loBounds.getDimension().getWidth()
                && toPoint.getY() >= lnY
                && toPoint.getY() <= lnY + loBounds.getDimension().getHeight());
        
        if (llReturn)
        {
            //TODO: Implement fine tune hit test for complicated polygons
            
            /*
            for (int i=0, lnCount = toPolygon.size(); i<lnCount; i++)
            {
                float lnX = toPoint.getX();
                float lnY = toPoint.getY();
                Point loCurrent = toPolygon.get(i);
                Point loNext = toPolygon.get((i < lnCount-1)?i+1:0);

                float lnAX = loCurrent.getX();
                float lnAY = loCurrent.getY();

                float lnBX = loNext.getX();
                float lnBY = loNext.getY();

                if (lnAY < lnY && lnBY >= lnY || lnBY < lnY && lnAY >= lnY)
                {
                    if (lnAX + (lnY - lnAY) / (lnBY - lnAY) * (lnBX - lnAX) < lnX)
                    {
                        // TODO: It may be possible to return as soon as this is false
                        llReturn = !llReturn;
                    }
                }
                else
                {
                    llReturn = !llReturn;
                }
            }

             */
        }
        return llReturn;
    }

    /**
     * Checks if two polygons intersect each other and returns the polygon that defines the intersection of the two polygons
     * @param toPolygonA The first polygon to check
     * @param toPolygonB The second polygon to check
     * @return the intersection polygon if the polygons do intersect or null if they do not intersect
     */
    public static Polygon intersects(Polygon toPolygonA, Polygon toPolygonB)
    {
        return null;
    }

    /**
     * Uses the point to subdivid the polygon specified and returns the internal polygons.  The point must be inside the polygon.
     * This does not change the original polygon in any way
     * @param toPolygon The polygon to subdivide
     * @param toPoint the point to use to subdivide the polygon
     * @return a list of the polygons created from the subdivision
     */
    public static List<Polygon> subdivide(Polygon toPolygon, Point toPoint)
    {
        if (!hitTest(toPolygon, toPoint))
        {
            // The point was NOT inside the polygon
            throw new InvalidParameterException("The point was not inside the polygon", "toPoint");
        }
        
        List<Polygon> loPolygons = new List<Polygon>(toPolygon.size());
        
        // We are looping through using a counter rather than foreach, because we need to access the next point as well
        for (int i=0; i<toPolygon.size(); i++)
        {
            Point loPoint = toPolygon.get(i);                               // The current point
            Point loNextPoint = toPolygon.get((i + 1) % toPolygon.size());      // The next point, or the first point if we are at the last

            Polygon loSub = new Polygon(toPolygon.getLocation());
            loSub.add(loPoint);
            loSub.add(loNextPoint);
            loSub.add(toPoint);

            loPolygons.add(loSub);
        }
        return loPolygons;
    }

    /**
     * Gets the full area of the polygon specefied
     * @param toPolygon
     * @return
     */
    public static float getArea(Polygon toPolygon)
    {
        // TODO: Check for polygons that have one or two points because at the moment, they can be created in the code
        // TODO: Extend for complex polygons


        float lnReturn = 0;

        // We are looping through using a counter rather than foreach, because we need to access the next point as well
        for (int i=0; i<toPolygon.size(); i++)
        {
            Point loPoint = toPolygon.get(i);                               // The current point
            Point loNextPoint = toPolygon.get((i + 1) % toPolygon.size());      // The next point, or the first point if we are at the last

            lnReturn += loPoint.getX() * loNextPoint.getY();
            lnReturn -= loPoint.getY() * loNextPoint.getX();
        }

        lnReturn /= 2f;

        // Make sure it is positive because of the clockwise defintions
        return Math.abs(lnReturn);
    }

    /**
     * This will give the center of mass of the polygon, it assumes the polygon is flat (2d) and made of a uniform material
     * @param toPolygon
     * @return
     */
    public static Point getCenter(Polygon toPolygon)
    {
        // TODO: Check for polygons that have one or two points because at the moment, they can be created in the code
        // TODO: Extend for complex polygons

        float lnX = 0;
        float lnY = 0;
        float lnArea = getArea(toPolygon);
        float lnFactor = 0;

        // We are looping through using a counter rather than foreach, because we need to access the next point as well
        for (int i=0; i<toPolygon.size(); i++)
        {
            Point loPoint = toPolygon.get(i);                               // The current point
            Point loNextPoint = toPolygon.get((i + 1) % toPolygon.size());      // The next point, or the first point if we are at the last

            lnFactor = loPoint.getX() * loNextPoint.getY() - loNextPoint.getX() * loPoint.getY();
            lnX += (loPoint.getX() + loNextPoint.getX()) * lnFactor;
            lnY += (loPoint.getY() + loNextPoint.getY()) * lnFactor;
        }

        lnArea *= 6f;
        lnFactor = 1/lnArea;
        lnX *= lnFactor;
        lnY *= lnFactor;
        return new Point(lnX, lnY);
    }

    /**
     * returns an array which defines the min and max values for a bounding box for the polygon specified
     * The array returned is defined as follows:
     * 0 = Min X
     * 1 = Max X
     * 2 = Min Y
     * 3 = Max Y
     * @param toPolygon
     * @return
     */
    public static Rectangle getBoundingRectangle(Polygon toPolygon)
    {
        // TODO: Reimplement this to allow sub classes of polygons to improve the speed
        float[] laValues = new float[4];
        laValues[0] = Float.MAX_VALUE;      // X value
        laValues[1] = Float.MAX_VALUE;      // Y Value
        laValues[2] = 0;                    // Width Value
        laValues[3] = 0;                    // Height Value
        
        Point loRectLocation = toPolygon.getLocation();
        for (Point loPoint : toPolygon)
        {
            laValues[0] = Math.min(laValues[0], loPoint.getX() + loRectLocation.getX());
            laValues[1] = Math.min(laValues[1], loPoint.getY() + loRectLocation.getY());
            laValues[2] = Math.max(laValues[2], loPoint.getX());
            laValues[3] = Math.max(laValues[3], loPoint.getY());
        }

        return new Rectangle(laValues[0], laValues[1], laValues[2], laValues[3]);
    }



    
}

