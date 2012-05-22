/* ========================================================
 * Rectangle.java
 *
 * Author:      kmchugh
 * Created:     Dec 1, 2010, 1:15:44 PM
 *
 * Description
 * --------------------------------------------------------
 * General Class Description.
 *
 * Change Log
 * --------------------------------------------------------
 * Init.Date        Ref.            Description
 * --------------------------------------------------------
 *
 * ===================================================== */

package Goliath.Graphics;


        
/**
 * Class Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Dec 1, 2010
 * @author      kmchugh
**/
public class Rectangle extends Polygon
{
    // TODO : Make a rectangle mutable
    private float m_nWidth;
    private float m_nHeight;

    /**
     * Creates a new instance of Rectangle
     */
    public Rectangle(float tnX, float tnY, float tnWidth, float tnHeight)
    {
        super(new Point(tnX, tnY), new Point[]{new Point(0, 0),
                        new Point(tnWidth, 0),
                        new Point(tnWidth, tnHeight),
                        new Point(0, tnHeight)});

        m_nWidth = tnWidth;
        m_nHeight = tnHeight;
    }
    
    /**
     * Creates a rectangle from point to point, calculating height and width from the 
     * difference between the points, and using the min x and y for the top left
     * @param toPoint the first point
     * @param toPoint the second point
     */
    public Rectangle(Point toPoint1, Point toPoint2)
    {   
        super(new Point(Math.min(toPoint1.getX(), toPoint2.getX()), Math.min(toPoint1.getY(), toPoint2.getY())));
        Point loSize = Point.subtract(toPoint1, toPoint2);
        Dimension loDimension = new Dimension(Math.abs(loSize.getX()), Math.abs(loSize.getY()));
        m_nWidth = loDimension.getWidth();
        m_nHeight = loDimension.getHeight();
        
        add(new Point(0, 0));
        add(new Point(loDimension.getWidth(), 0));
        add(new Point(loDimension.getWidth(), loDimension.getHeight()));
        add(new Point(0 , loDimension.getHeight()));
    }

    public Rectangle(Point toPoint, Dimension toDimension)
    {
        this(toPoint.getX(), toPoint.getY(), toDimension.getWidth(), toDimension.getHeight());
    }

    public Dimension getDimension()
    {
        return new Dimension(m_nWidth, m_nHeight);
    }

    


}
