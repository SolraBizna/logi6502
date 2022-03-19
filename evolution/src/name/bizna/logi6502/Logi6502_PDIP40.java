package name.bizna.logi6502;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.util.GraphicsUtil;

import java.awt.*;

/*
  VPB RESB
  RDY PHI2O
PHI1O SOB
 IRQB PHI2
  MLB BE
 NMIB NC
 SYNC RWB
  VDD D0
   A0 D1
   A1 D2
   A2 D3
   A3 D4
   A4 D5
   A5 D6
   A6 D7
   A7 A15
   A7 A14
   A9 A13
  A10 A12
  A11 VSS
 */

public class Logi6502_PDIP40 extends Logi6502 {
    // real pin numbers minus 1
    public static final int PORT_VPB=0;
    public static final int PORT_RDY=1;
    public static final int PORT_PHI1O=2;
    public static final int PORT_IRQB=3;
    public static final int PORT_MLB=4;
    public static final int PORT_NMIB=5;
    public static final int PORT_SYNC=6;
    public static final int PORT_VDD=7;
    public static final int PORT_A0=8;
    public static final int PORT_A1=9;
    public static final int PORT_A2=10;
    public static final int PORT_A3=11;
    public static final int PORT_A4=12;
    public static final int PORT_A5=13;
    public static final int PORT_A6=14;
    public static final int PORT_A7=15;
    public static final int PORT_A8=16;
    public static final int PORT_A9=17;
    public static final int PORT_A10=18;
    public static final int PORT_A11=19;
    public static final int PORT_VSS=20;
    public static final int PORT_A12=21;
    public static final int PORT_A13=22;
    public static final int PORT_A14=23;
    public static final int PORT_A15=24;
    public static final int PORT_D7=25;
    public static final int PORT_D6=26;
    public static final int PORT_D5=27;
    public static final int PORT_D4=28;
    public static final int PORT_D3=29;
    public static final int PORT_D2=30;
    public static final int PORT_D1=31;
    public static final int PORT_D0=32;
    public static final int PORT_RWB = 33;
    public static final int PORT_NC = 34;
    public static final int PORT_BE = 35;
    public static final int PORT_PHI2 = 36;
    public static final int PORT_SOB = 37;
    public static final int PORT_PHI2O = 38;
    public static final int PORT_RESB = 39;
    public static final int NUM_PINS = 40;
    private static final int[] A_PINS = new int[]{
            PORT_A0,
            PORT_A1,
            PORT_A2,
            PORT_A3,
            PORT_A4,
            PORT_A5,
            PORT_A6,
            PORT_A7,
            PORT_A8,
            PORT_A9,
            PORT_A10,
            PORT_A11,
            PORT_A12,
            PORT_A13,
            PORT_A14,
            PORT_A15,
    };
    private static final int[] D_PINS = new int[]{
            PORT_D0,
            PORT_D1,
            PORT_D2,
            PORT_D3,
            PORT_D4,
            PORT_D5,
            PORT_D6,
            PORT_D7,
    };
    private static final int PINS_PER_SIDE = 20;
    static {
        assert(PINS_PER_SIDE * 2 == NUM_PINS);
    }
    private static final int PIXELS_PER_PIN = 10;
    private static final int V_MARGIN = 10;
    private static final int LEFT_X = -40;
    private static final int RIGHT_X = 40;
    private static final int TOP_Y = ((PINS_PER_SIDE - 1) * PIXELS_PER_PIN / -2) - V_MARGIN - 5;
    private static final int BOT_Y = ((PINS_PER_SIDE - 1) * PIXELS_PER_PIN / 2) + V_MARGIN - 5;
    private static final int PIN_START_Y = TOP_Y + V_MARGIN;
    private static final int PIN_STOP_Y = BOT_Y - V_MARGIN;
    private static final int NOTCH_WIDTH = 12;
    private static final int NOTCH_HEIGHT = 12;
    private static final PortInfo[] portInfos = new PortInfo[]{
            // Left side, top to bottom
            PortInfo.simpleOutput("VPB#"),
            PortInfo.simpleBidi("RDY"),
            PortInfo.simpleOutput("PHI1O"),
            PortInfo.simpleInput("IRQB#"),
            PortInfo.simpleOutput("MLB#"),
            PortInfo.simpleInput("NMIB#"),
            PortInfo.simpleOutput("SYNC"),
            PortInfo.simpleInput("VDD"),
            PortInfo.sharedOutput("A0"),
            PortInfo.sharedOutput("A1"),
            PortInfo.sharedOutput("A2"),
            PortInfo.sharedOutput("A3"),
            PortInfo.sharedOutput("A4"),
            PortInfo.sharedOutput("A5"),
            PortInfo.sharedOutput("A6"),
            PortInfo.sharedOutput("A7"),
            PortInfo.sharedOutput("A8"),
            PortInfo.sharedOutput("A9"),
            PortInfo.sharedOutput("A10"),
            PortInfo.sharedOutput("A11"),
            // Right side, bottom to top
            PortInfo.simpleInput("VSS"),
            PortInfo.sharedOutput("A12"),
            PortInfo.sharedOutput("A13"),
            PortInfo.sharedOutput("A14"),
            PortInfo.sharedOutput("A15"),
            PortInfo.simpleBidi("D7"),
            PortInfo.simpleBidi("D6"),
            PortInfo.simpleBidi("D5"),
            PortInfo.simpleBidi("D4"),
            PortInfo.simpleBidi("D3"),
            PortInfo.simpleBidi("D2"),
            PortInfo.simpleBidi("D1"),
            PortInfo.simpleBidi("D0"),
            PortInfo.sharedOutput("RWB#"),
            PortInfo.simpleInput("NC"),
            PortInfo.simpleInput("BE"),
            PortInfo.simpleInput("PHI2"),
            PortInfo.simpleInput("SOB#"),
            PortInfo.simpleInput("PHI2O"),
            PortInfo.simpleInput("RESB#")
    };
    public Logi6502_PDIP40() {
        super("W65C02S (PDIP-40)");
        setOffsetBounds(Bounds.create(LEFT_X,TOP_Y,RIGHT_X - LEFT_X,BOT_Y - TOP_Y));
        addStandardPins(portInfos, LEFT_X, RIGHT_X, PIN_START_Y, PIN_STOP_Y, PIXELS_PER_PIN, PINS_PER_SIDE);
    }

    @Override
    public void propagate(InstanceState state) {
        if(!powerOK(state))
            shred(state);
        else
            super.propagate(state);
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        Graphics g = painter.getGraphics();
        Bounds bounds = painter.getBounds();
        GraphicsUtil.switchToWidth(g, 2);
        g.drawPolyline(
                new int[]{bounds.getX()+bounds.getWidth()/2+NOTCH_WIDTH/2+1,
                        bounds.getX()+bounds.getWidth(),
                        bounds.getX()+bounds.getWidth(),
                        bounds.getX(),
                        bounds.getX(),
                        bounds.getX()+bounds.getWidth()/2-NOTCH_WIDTH/2},
                new int[]{bounds.getY(),
                        bounds.getY(),
                        bounds.getY()+bounds.getHeight(),
                        bounds.getY()+bounds.getHeight(),
                        bounds.getY(),
                        bounds.getY()},
                6);
        Shape oldClip = g.getClip();
        g.setClip(bounds.getX(), bounds.getY(), bounds.getX()+bounds.getWidth(), bounds.getY()+bounds.getHeight());
        g.drawOval(bounds.getX()+bounds.getWidth()/2-NOTCH_WIDTH/2-1,
                bounds.getY()-NOTCH_HEIGHT/2-1,
                NOTCH_WIDTH+2, NOTCH_HEIGHT+2);
        g.setClip(oldClip);
        GraphicsUtil.switchToWidth(g, 1);
        Font oldFont = g.getFont();
        g.setFont(oldFont.deriveFont(9.0f));
        int n = 0;
        for(int i = 0; i < portInfos.length; ++i) {
            if(portInfos[i] != null) {
                Direction dir = i < PINS_PER_SIDE ? Direction.EAST : Direction.WEST;
                painter.drawPort(n, portInfos[i].name, dir);
                ++n;
            }
        }
        g.setFont(oldFont);
        super.paintInstance(painter);
    }

    @Override
    protected boolean getRESB(InstanceState i) {
//      return i.getPort(PORT_RESB) != Value.TRUE; //classic
        return i.getPortValue(PORT_RESB) != Value.TRUE; //evolution
    }

    @Override
    protected boolean getPHI2(InstanceState i) {
//      Value v = i.getPort(PORT_PHI2); //classic
        Value v = i.getPortValue(PORT_PHI2); //evolution
        i.setPort(PORT_PHI2O, v, 1);
        if(v == Value.TRUE) {
            i.setPort(PORT_PHI1O, Value.FALSE, 1);
            return true;
        }
        else if(v == Value.FALSE) {
            i.setPort(PORT_PHI1O, Value.TRUE, 1);
            return false;
        }
        else {
            i.setPort(PORT_PHI1O, Value.ERROR, 1);
            return true;
        }
    }

    @Override
    public boolean getIRQB(InstanceState i) {
//      return i.getPort(PORT_IRQB) == Value.FALSE; //classic
        return i.getPortValue(PORT_IRQB) == Value.FALSE; //evolution
    }

    @Override
    public boolean getNMIB(InstanceState i) {
//      return i.getPort(PORT_NMIB) == Value.FALSE; //classic
        return i.getPortValue(PORT_NMIB) == Value.FALSE; //evolution
    }

    private void boolPort(InstanceState i, int port, boolean value, int delay) {
        i.setPort(port, value ? Value.TRUE : Value.FALSE, delay);
    }
    private void doAddr(InstanceState i, short a) {
        for(int n = 0; n < 16; ++n) {
            i.setPort(A_PINS[n], (a & (1 << n)) == 0 ? Value.FALSE : Value.TRUE, 12);
        }
    }
    private void valueToBusPins(InstanceState i, Value wat) {
        for(int port : A_PINS) i.setPort(port, Value.UNKNOWN, 12);
        for(int port : D_PINS) i.setPort(port, Value.UNKNOWN, 12);
        i.setPort(PORT_RWB, Value.UNKNOWN, 12);
    }
    private boolean checkBE(InstanceState i) {
//      if(i.getPort(PORT_BE) == Value.FALSE) { //classic
        if(i.getPortValue(PORT_BE) == Value.FALSE) { //evolution
            valueToBusPins(i, Value.UNKNOWN);
            return false;
        }
        else return true;
    }

    @Override
    public void doRead(InstanceState i, short a) {
        if(!checkBE(i)) return;
        doAddr(i, a);
        for(int port : D_PINS) i.setPort(port, Value.UNKNOWN, 12);
        boolPort(i, PORT_RWB, true, 9);
    }

    @Override
    public void doWrite(InstanceState i, short a, byte data) {
        if(!checkBE(i)) return;
        doAddr(i, a);
        for(int n = 0; n < 8; ++n) {
            i.setPort(D_PINS[n], (data & (1 << n)) == 0 ? Value.FALSE : Value.TRUE, 12);
        }
        boolPort(i, PORT_RWB, false, 9);
    }

    @Override
    public byte getD(InstanceState i) {
        byte ret = 0;
        for(int n = 0; n < 8; ++n) {
//          Value p = i.getPort(D_PINS[n]); //classic
            Value p = i.getPortValue(D_PINS[n]); //evolution
            if(p == Value.TRUE) ret = (byte)(ret | (1 << n));
        }
        return ret;
    }

    protected boolean powerOK(InstanceState i) {
//      boolean ret = i.getPort(PORT_VDD) == Value.TRUE && i.getPort(PORT_VSS) == Value.FALSE; //classic
        boolean ret = i.getPortValue(PORT_VDD) == Value.TRUE && i.getPortValue(PORT_VSS) == Value.FALSE; //evolution
        if(!ret)
            valueToBusPins(i, Value.UNKNOWN);
        return ret;
    }
    @Override
    public boolean getRDY(InstanceState i) {
//      return i.getPort(PORT_RDY) != Value.FALSE; //classic
        return i.getPortValue(PORT_RDY) != Value.FALSE; //evolution
    }
    @Override
    public boolean getSOB(InstanceState i) {
//      return i.getPort(PORT_SOB) == Value.FALSE; //classic
        return i.getPortValue(PORT_SOB) == Value.FALSE; //evolution
    }
    @Override
    public void setRDY(InstanceState i, boolean x) {
        i.setPort(PORT_RDY, x ? Value.UNKNOWN : Value.FALSE, 9);
    }
    @Override
    public void setVPB(InstanceState i, boolean x) {
        boolPort(i, PORT_VPB, !x, 6);
    }
    @Override
    public void setSYNC(InstanceState i, boolean x) {
        boolPort(i, PORT_SYNC, x, 6);
    }
    @Override
    public void setMLB(InstanceState i, boolean x) {
        boolPort(i, PORT_MLB, x, 6);
    }
}
