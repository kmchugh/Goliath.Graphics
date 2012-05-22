/* ========================================================
 * Colour.java
 *
 * Author:      admin
 * Created:     Aug 31, 2011, 1:12:39 PM
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

import Goliath.Applications.Application;
import Goliath.Collections.HashTable;
import Goliath.Collections.List;
import Goliath.Exceptions.InvalidParameterException;
import java.awt.Color;
import java.lang.reflect.Field;

/**
 * Class Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Aug 31, 2011
 * @author      admin
 **/
public class Colour extends Color
{
    public static Colour TRANSPARENT = new Colour(0, 0, 0, 0);
    private static HashTable<String, Colour> g_oColourMap;
    
    public static Colour getColor(String tcColour)
    {
        tcColour = tcColour.toLowerCase();
        if ( tcColour.startsWith("#") ) 
        {
            tcColour = tcColour.substring(1);
            if (tcColour.length() == 6)
            {
                return new Colour(Integer.parseInt(tcColour, 16));
            }
            else if (tcColour.length() == 8)
            {
                return new Colour(Integer.parseInt(tcColour, 16));
            }
            else
            {
                throw new InvalidParameterException("The colour string provided is not valid", "tcColour", tcColour);
            }
        }
        else
        {
            if (tcColour.equalsIgnoreCase("transparent"))
            {
                return TRANSPARENT;
            }
            else
            {
                if (g_oColourMap == null)
                {
                    g_oColourMap = new HashTable<String, Colour>();
                    List<Field> loFields = new List<Field>(Color.class.getDeclaredFields());
                    for (Field loField : loFields)
                    {
                        if (loField.getType() == Color.class)
                        {
                            String lcName = loField.getName();
                            if (!g_oColourMap.containsKey(lcName))
                            {
                                try
                                {
                                    Color loColour = (Color)loField.get(0);
                                    g_oColourMap.put(lcName, new Colour(loColour.getRed()/255f, loColour.getGreen()/255f, loColour.getBlue()/255f, loColour.getAlpha()/255f));
                                }
                                catch (Throwable ex)
                                {
                                    Application.getInstance().log(ex);
                                }
                            }
                        }
                    }
                }
                
                Colour loColour = g_oColourMap.get(tcColour);
                if (loColour == null)
                {
                    Application.getInstance().log("Colour definition not found : " + tcColour);
                    loColour = new Colour(1, 1, 1);
                }
                return loColour;
            }
        }
    }

    /**
     * Creates a new instance of Colour
     */
    public Colour(int tnColour)
    {
        super(tnColour);
    }
    
    public Colour(float tnRed, float tnGreen, float tnBlue)
    {
        super(tnRed, tnGreen, tnBlue);
    }
    
    public Colour(int tnRed, int tnGreen, int tnBlue)
    {
        super(tnRed/255f, tnGreen/255f, tnBlue/255f);
    }
    
    public Colour(int tnRed, int tnGreen, int tnBlue, int tnAlpha)
    {
        super(tnRed/255f, tnGreen/255f, tnBlue/255f, tnAlpha/255f);
    }
    
    public Colour(float tnRed, float tnGreen, float tnBlue, float tnAlpha)
    {
        super(tnRed, tnGreen, tnBlue, tnAlpha);
    }

    @Override
    public String toString()
    {
        return super.toString() + ", " + getAlpha();
    }
    
    
}
