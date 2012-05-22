/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Goliath.Graphics.Constants;

/**
 *
 * @author kenmchugh
 */
public final class Position extends Goliath.DynamicEnum
{
    protected Position(String tcValue)
    {
        super(tcValue);
    }

    private static Position g_oTOPLEFT;
    public static Position TOP_LEFT()
    {
        if (g_oTOPLEFT == null)
        {
            g_oTOPLEFT = createEnumeration(Position.class, "TOPLEFT");
        }
        return g_oTOPLEFT;
    }

    private static Position g_oTOPCENTER;
    public static Position TOP_CENTER()
    {
        if (g_oTOPCENTER == null)
        {
            g_oTOPCENTER = createEnumeration(Position.class, "TOPCENTER");
        }
        return g_oTOPCENTER;
    }

    private static Position g_oTOPRIGHT;
    public static Position TOP_RIGHT()
    {
        if (g_oTOPRIGHT == null)
        {
            g_oTOPRIGHT = createEnumeration(Position.class, "TOPRIGHT");
        }
        return g_oTOPRIGHT;
    }

    private static Position g_oMIDDLELEFT;
    public static Position MIDDLE_LEFT()
    {
        if (g_oMIDDLELEFT == null)
        {
            g_oMIDDLELEFT = createEnumeration(Position.class, "MIDDLELEFT");
        }
        return g_oMIDDLELEFT;
    }

    private static Position g_oMIDDLECENTER;
    public static Position MIDDLE_CENTER()
    {
        if (g_oMIDDLECENTER == null)
        {
            g_oMIDDLECENTER = createEnumeration(Position.class, "MIDDLECENTER");
        }
        return g_oMIDDLECENTER;
    }

    private static Position g_oMIDDLERIGHT;
    public static Position MIDDLE_RIGHT()
    {
        if (g_oMIDDLERIGHT == null)
        {
            g_oMIDDLERIGHT = createEnumeration(Position.class, "MIDDLERIGHT");
        }
        return g_oMIDDLERIGHT;
    }

    private static Position g_oBOTTOMLEFT;
    public static Position BOTTOM_LEFT()
    {
        if (g_oBOTTOMLEFT == null)
        {
            g_oBOTTOMLEFT = createEnumeration(Position.class, "BOTTOMLEFT");
        }
        return g_oBOTTOMLEFT;
    }

    private static Position g_oBOTTOMCENTER;
    public static Position BOTTOM_CENTER()
    {
        if (g_oBOTTOMCENTER == null)
        {
            g_oBOTTOMCENTER = createEnumeration(Position.class, "BOTTOMCENTER");
        }
        return g_oBOTTOMCENTER;
    }

    private static Position g_oBOTTOMRIGHT;
    public static Position BOTTOM_RIGHT()
    {
        if (g_oBOTTOMRIGHT == null)
        {
            g_oBOTTOMRIGHT = createEnumeration(Position.class, "BOTTOMRIGHT");
        }
        return g_oBOTTOMRIGHT;
    }
}
