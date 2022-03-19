package name.bizna.logi6502;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;

public class Logi6502Simple extends Logi6502 {
    public static final int PORT_PHI2 = 0;
    public static final int PORT_IRQB=1;
    public static final int PORT_NMIB=2;
    public static final int PORT_RESB=3;
    public static final int PORT_A=4;
    public static final int PORT_D=5;
    public static final int PORT_RWB = 6;
    public static final int PORT_VPB=7;
    public static final int PORT_SYNC=8;
    public static final int NUM_PINS = 10;
    private static final int PINS_PER_SIDE = 5;
    static {
        assert(PINS_PER_SIDE * 2 == NUM_PINS);
    }
    private static final int PIXELS_PER_PIN = 20;
    private static final int V_MARGIN = 20;
    private static final int LEFT_X = -80;
    private static final int RIGHT_X = 80;
    private static final int TOP_Y = ((PINS_PER_SIDE - 1) * PIXELS_PER_PIN / -2) - V_MARGIN;
    private static final int BOT_Y = ((PINS_PER_SIDE - 1) * PIXELS_PER_PIN / 2) + V_MARGIN;
    private static final int PIN_START_Y = TOP_Y + V_MARGIN + PIXELS_PER_PIN / 2;
    private static final int PIN_STOP_Y = BOT_Y - V_MARGIN;
    private static final PortInfo[] portInfos = new PortInfo[]{
            // Left side, top to bottom
            PortInfo.simpleInput("PHI2"),
            PortInfo.simpleInput("IRQB#"),
            PortInfo.simpleInput("NMIB#"),
            PortInfo.simpleInput("RESB#"),
            null,
            // Right side, bottom to top
            PortInfo.sharedOutput("A", 16),
            PortInfo.simpleBidi("D", 8),
            PortInfo.sharedOutput("RWB"),
            PortInfo.simpleOutput("VPB#"),
            PortInfo.simpleOutput("SYNC"),
    };
    public Logi6502Simple() {
        super("W65C02S (Simple)");
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
                if(i == PORT_PHI2)
                    painter.drawClock(n, dir);
                else
                    painter.drawPort(n, portInfos[i].name, dir);
                ++n;
            }
        }
        super.paintInstance(painter);
    }

    @Override
    protected boolean getRESB(InstanceState i) {
//      return i.getPort(PORT_RESB) != Value.TRUE; //classic
        return i.getPortValue(PORT_RESB) != Value.TRUE; //evolution
    }

    @Override
    protected boolean getPHI2(InstanceState i) {
//      return i.getPort(PORT_PHI2) != Value.FALSE; //classic
        return i.getPortValue(PORT_PHI2) != Value.FALSE; //evolution
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
        i.setPort(PORT_A, Value.createKnown(BitWidth.create(16), a), 12);
    }

    @Override
    public void doRead(InstanceState i, short a) {
        doAddr(i, a);
        i.setPort(PORT_D, Value.createUnknown(BitWidth.create(8)), 9);
        boolPort(i, PORT_RWB, true, 9);
    }

    @Override
    public void doWrite(InstanceState i, short a, byte data) {
        doAddr(i, a);
        i.setPort(PORT_D, Value.createKnown(BitWidth.create(8), data), 15);
        boolPort(i, PORT_RWB, false, 9);
    }

    @Override
    public byte getD(InstanceState i) {
//      return (byte)i.getPort(PORT_D).toIntValue(); //classic
        return (byte)i.getPortValue(PORT_D).toLongValue(); //evolution
    }
    @Override
    public void setVPB(InstanceState i, boolean x) {
        boolPort(i, PORT_VPB, !x, 6);
    }
    @Override
    public void setSYNC(InstanceState i, boolean x) {
        boolPort(i, PORT_SYNC, x, 6);
    }
}
