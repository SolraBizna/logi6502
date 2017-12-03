package name.bizna.logi6502;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.util.GraphicsUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Logi6502Expert extends Logi6502 {
    public static final int PORT_VPB=0;
    public static final int PORT_RDY=1;
    public static final int PORT_IRQB=2;
    public static final int PORT_MLB=3;
    public static final int PORT_NMIB=4;
    public static final int PORT_SYNC=5;
    public static final int PORT_A=6;
    public static final int PORT_D=7;
    public static final int PORT_RWB = 8;
    public static final int PORT_BE = 9;
    public static final int PORT_PHI2 = 10;
    public static final int PORT_SOB = 11;
    public static final int PORT_RESB = 12;
    public static final int NUM_PINS = 14;
    private static final int PINS_PER_SIDE = 7;
    static {
        assert(PINS_PER_SIDE * 2 == NUM_PINS);
    }
    private static final int PIXELS_PER_PIN = 20;
    private static final int V_MARGIN = 10;
    private static final int LEFT_X = -80;
    private static final int RIGHT_X = 80;
    private static final int TOP_Y = ((PINS_PER_SIDE - 1) * PIXELS_PER_PIN / -2) - V_MARGIN;
    private static final int BOT_Y = ((PINS_PER_SIDE - 1) * PIXELS_PER_PIN / 2) + V_MARGIN;
    private static final int PIN_START_Y = TOP_Y + V_MARGIN + PIXELS_PER_PIN/2;
    private static final int PIN_STOP_Y = BOT_Y - V_MARGIN;
    private static final PortInfo[] portInfos = new PortInfo[]{
            // Left side, top to bottom
            PortInfo.simpleOutput("VPB#"),
            PortInfo.simpleBidi("RDY"),
            PortInfo.simpleInput("IRQB#"),
            PortInfo.simpleOutput("MLB#"),
            PortInfo.simpleInput("NMIB#"),
            PortInfo.simpleOutput("SYNC"),
            null,
            // Right side, bottom to top
            PortInfo.sharedOutput("A", 16),
            PortInfo.simpleBidi("D", 8),
            PortInfo.sharedOutput("RWB"),
            PortInfo.simpleInput("BE"),
            PortInfo.simpleInput("PHI2"),
            PortInfo.simpleInput("SOB#"),
            PortInfo.simpleInput("RESB#")
    };
    public Logi6502Expert() {
        super("W65C02S (Expert)");
        setOffsetBounds(Bounds.create(LEFT_X,TOP_Y,RIGHT_X - LEFT_X,BOT_Y - TOP_Y));
        addStandardPins(portInfos, LEFT_X, RIGHT_X, PIN_START_Y, PIN_STOP_Y, PIXELS_PER_PIN, PINS_PER_SIDE);
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        painter.drawBounds();
        int n = 0;
        for(int i = 0; i < portInfos.length; ++i) {
            if(portInfos[i] != null) {
                Direction dir = i < PINS_PER_SIDE ? Direction.EAST : Direction.WEST;
                painter.drawPort(n, portInfos[i].name, dir);
                ++n;
            }
        }
        super.paintInstance(painter);
    }

    @Override
    protected boolean getRESB(InstanceState i) {
        return i.getPort(PORT_RESB) != Value.TRUE;
    }

    @Override
    protected boolean getPHI2(InstanceState i) {
        return i.getPort(PORT_PHI2) != Value.FALSE;
    }

    @Override
    public boolean getIRQB(InstanceState i) {
        return i.getPort(PORT_IRQB) == Value.FALSE;
    }

    @Override
    public boolean getNMIB(InstanceState i) {
        return i.getPort(PORT_NMIB) == Value.FALSE;
    }

    private void boolPort(InstanceState i, int port, boolean value, int delay) {
        i.setPort(port, value ? Value.TRUE : Value.FALSE, delay);
    }
    private void doAddr(InstanceState i, short a) {
        i.setPort(PORT_A, Value.createKnown(BitWidth.create(16), a), 12);
    }
    private boolean checkBE(InstanceState i) {
        if(i.getPort(PORT_BE) == Value.FALSE) {
            i.setPort(PORT_A, Value.createUnknown(BitWidth.create(16)), 12);
            i.setPort(PORT_D, Value.createUnknown(BitWidth.create(8)), 12);
            i.setPort(PORT_RWB, Value.UNKNOWN, 12);
            return false;
        }
        else return true;
    }

    @Override
    public void doRead(InstanceState i, short a) {
        if(!checkBE(i)) return;
        doAddr(i, a);
        i.setPort(PORT_D, Value.createUnknown(BitWidth.create(8)), 9);
        boolPort(i, PORT_RWB, true, 9);
    }

    @Override
    public void doWrite(InstanceState i, short a, byte data) {
        if(!checkBE(i)) return;
        doAddr(i, a);
        i.setPort(PORT_D, Value.createKnown(BitWidth.create(8), data), 15);
        boolPort(i, PORT_RWB, false, 9);
    }

    @Override
    public byte getD(InstanceState i) {
        return (byte)i.getPort(PORT_D).toIntValue();
    }
    @Override
    public boolean getRDY(InstanceState i) {
        return i.getPort(PORT_RDY) != Value.FALSE;
    }
    @Override
    public boolean getSOB(InstanceState i) {
        return i.getPort(PORT_SOB) == Value.FALSE;
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
