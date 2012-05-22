/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Goliath.Graphics;

/**
 *
 * @author kenmchugh
 */
public abstract class Shape
{
    // TODO: Implement AWT Shape interface
    private Point m_oLocation;

    public Shape(Point toLocation)
    {
        m_oLocation = toLocation;
    }

    public final Point getLocation()
    {
        return m_oLocation;
    }
}
