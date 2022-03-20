package name.bizna.logi6502;

import com.cburch.logisim.instance.InstanceState;

import java.util.Random;

public abstract class AbstractCore {
    public static final short IRQ_VECTOR = (short) 0xFFFE;
    public static final short RESET_VECTOR = (short) 0xFFFC;
    public static final short NMI_VECTOR = (short) 0xFFFA;
    public static final byte P_C_BIT = 0x01;
    public static final byte P_Z_BIT = 0x02;
    public static final byte P_I_BIT = 0x04;
    public static final byte P_D_BIT = 0x08;
    public static final byte P_B_BIT = 0x10;
    public static final byte P_1_BIT = 0x20;
    public static final byte P_V_BIT = 0x40;
    public static final byte P_N_BIT = (byte) 0x80;
    protected Logi6502 parent;
    protected InstanceState cis;
    public AbstractCore(Logi6502 parent) {
        this.parent = parent;
    }
    protected byte a, x, y, p, s, data;
    protected short ea;
    protected byte fetchedOpcode, stage;
    protected short vectorToPull = IRQ_VECTOR;
    protected short pc;
    protected boolean stopped = true;
    protected boolean previousNMI = false, previousSO = false, previousClock = true, taking_branch = false, wanting_sync, wanting_vpb;
    protected short intendedA;
    protected byte intendedD;
    protected boolean intendedRWB;

    public void shred() {
        Random r = new Random();
        byte[] corr = new byte[12];
        r.nextBytes(corr);
        a = corr[0];
        x = corr[1];
        y = corr[2];
        p = corr[3];
        s = corr[4];
        data = corr[5];
        fetchedOpcode = corr[6];
        stage = corr[7];
        ea = (short)((corr[8]&0xFF) | corr[9]<<8);
        intendedA = ea;
        pc = (short)((corr[10]&0xFF) | corr[11]<<8);
        intendedRWB = false;
        previousNMI = true;
        previousSO = true;
        wanting_sync = false;
        wanting_vpb = false;
    }

    abstract protected void doInstruction();

    protected void simplePUpdateNZ(int result) {
        if((result & 0xFF) == 0) p |= P_Z_BIT;
        else p &= ~P_Z_BIT;
        if((result & 0x80) != 0) p |= P_N_BIT;
        else p &= ~P_N_BIT;
    }

    protected void simplePUpdateNZC(int result) {
        if((result & 0xFF) == 0) p |= P_Z_BIT;
        else p &= ~P_Z_BIT;
        if((result & 0x80) != 0) p |= P_N_BIT;
        else p &= ~P_N_BIT;
        if((result & 0x100) != 0) p |= P_C_BIT;
        else p &= ~P_C_BIT;
    }

    public byte readA() {
        return a;
    }

    public byte readX() {
        return x;
    }

    public byte readY() {
        return y;
    }

    public byte readP() {
        return p;
    }

    public byte readS() {
        return s;
    }

    public short readPC() {
        return pc;
    }

    public void writeA(byte nu) {
        a = nu;
    }

    public void writeX(byte nu) {
        x = nu;
    }

    public void writeY(byte nu) {
        y = nu;
    }

    public void writeP(byte nu) {
        p = nu;
    }

    public void writeS(byte nu) {
        s = nu;
    }

    public void writePC(short nu) {
        pc = nu;
    }

    public void reset(InstanceState cis) {
        stopped = false;
        fetchedOpcode = 0;
        stage = 1;
        p |= P_I_BIT;
        p &= ~P_D_BIT;
        previousNMI = true;
        vectorToPull = RESET_VECTOR;
        wanting_vpb = false;
        parent.setVPB(cis, false);
        wanting_sync = false;
        parent.setSYNC(cis, false);
        parent.setMLB(cis, false);
        parent.setRDY(cis, true);
    }

    protected void wantRead(short address) {
        intendedA = address;
        intendedRWB = true;
    }

    protected void wantWrite(short address, byte data) {
        intendedA = address;
        intendedD = data;
        intendedRWB = false;
    }

    protected void wantPush(byte data) {
        if(vectorToPull == RESET_VECTOR)
            wantRead((short)(0x0100 | (s & 0xFF)));
        else
            wantWrite((short)(0x0100 | (s & 0xFF)), data);
        --s;
    }

    protected void wantPop() {
        ++s;
        wantRead((short)(0x0100 | (s & 0xFF)));
    }

    protected void wantVPB(boolean want) {
        wanting_vpb = want;
    }

    public void goh(InstanceState cis, boolean isResetting, boolean clockIsHigh) {
        if(isResetting)
            reset(cis);
        boolean isReady = parent.getRDY(cis)
	    && (fetchedOpcode != (byte)0xCB || stage != 2);
        if(!isReady && !stopped && fetchedOpcode == (byte)0xCB && stage == 2) {
	    if(parent.getIRQB(cis) || (parent.getNMIB(cis) && !previousNMI)) {
		parent.setRDY(cis, true);
		isReady = parent.getRDY(cis);
	    }
        }
        if(stopped || !isReady) return;
        this.cis = cis;
        if(!clockIsHigh && previousClock) {
            // falling edge! go!
            boolean so = parent.getSOB(cis);
            if(so != previousSO) {
                if(so)
                    p |= P_V_BIT;
                previousSO = so;
            }
            do {
                wanting_sync = stage < 0;
                if(stage < 0) {
                    wantRead(pc++);
                    ++stage;
                } else if(stage == 0) {
                    wantRead(pc++);
                    boolean nmi = parent.getNMIB(cis);
                    if(nmi && !previousNMI) {
                        vectorToPull = NMI_VECTOR;
                        pc -= 2;
                        fetchedOpcode = 0x00; // BRK
                    } else if(parent.getIRQB(cis) && (p & P_I_BIT) == 0) {
                        vectorToPull = IRQ_VECTOR;
                        pc -= 2;
                        fetchedOpcode = 0x00; // BRK
                    } else {
                        fetchedOpcode = parent.getD(cis);
                    }
                    previousNMI = nmi;
                    ++stage;
                } else {
                    data = parent.getD(cis);
                    doInstruction();
                    if(stage >= 0) ++stage;
                }
            } while(stage < 0 && !stopped);
	    // all finished, let's update our bus signals
            if(intendedRWB)
                parent.doRead(cis, intendedA);
            else
                parent.doWrite(cis, intendedA, intendedD);
            parent.setSYNC(cis, wanting_sync);
            parent.setVPB(cis, wanting_vpb);
        }
        this.cis = null;
        previousClock = clockIsHigh;
    }
}
