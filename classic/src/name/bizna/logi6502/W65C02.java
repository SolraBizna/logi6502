package name.bizna.logi6502;

/*
 * NOTE: This source file is automatically generated! Manually editing it is
 * pointless!
 */

/**
 * This core strictly emulates a Western Design Center-branded W65C02 or
 * W65C02S. This includes the \"Rockwell bit extensions\".
 */
public class W65C02 extends AbstractCore {
  public W65C02(Logi6502 parent) { super(parent); }
  @Override
  @SuppressWarnings("unused")
  protected void doInstruction() {
    switch(fetchedOpcode) {
    case (byte)0:
switch(stage) {

case 1: {wantPush((byte)(pc >> 8));
break; } case 2: {
wantPush((byte)pc);
break; } case 3: {
wantPush((byte)(p|P_B_BIT));
break; } case 4: {
p &= ~P_D_BIT;
p |= P_I_BIT;
wantVPB(true);
wantRead(vectorToPull);
break; } case 5: {
pc = (short)(data & 0xFF);
wantRead((short)(vectorToPull+1));
vectorToPull = IRQ_VECTOR;
break; } case 6: {
pc |= data << 8;
wantVPB(false);
} default: stage = -1; if(true) return; } break;
    case (byte)1:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 4: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 5: {
a |= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)2:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
} default: stage = -1; if(true) return; } break;
    case (byte)3:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)4:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
data |= a;
wantWrite(ea, data);
break; } case 4: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)5:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
a |= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)6:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
short result = (short)((data&0xFF)<<1);
data = (byte)result;
simplePUpdateNZC(result);
wantWrite(ea, data);
break; } case 4: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)7:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data &= ~(1<<0);
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)8:
switch(stage) {

case 1: {--pc;
wantPush(p);
break; } case 2: {
} default: stage = -1; if(true) return; } break;
    case (byte)9:
switch(stage) {

case 1: {a |= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)10:
switch(stage) {

case 1: {--pc;
data = a;
short result = (short)((data&0xFF)<<1);
data = (byte)result;
simplePUpdateNZC(result);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)11:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)12:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
data |= a;
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)13:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
a |= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)14:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
short result = (short)((data&0xFF)<<1);
data = (byte)result;
simplePUpdateNZC(result);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)15:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<0)) == 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)16:
switch(stage) {

case 1: {ea = (short)(pc + data);
taking_branch = (p&P_N_BIT) == 0;
boolean doFixup = (pc>>8) != (ea>>8);
if(doFixup) wantRead((short)((pc&0xFF00)|(ea&0xFF)));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 2: {
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)17:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 4: {
wantRead(ea);
break; } case 5: {
a |= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)18:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 4: {
a |= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)19:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)20:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
data &= ~a;
wantWrite(ea, data);
break; } case 4: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)21:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
a |= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)22:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
short result = (short)((data&0xFF)<<1);
data = (byte)result;
simplePUpdateNZC(result);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)23:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data &= ~(1<<1);
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)24:
switch(stage) {

case 1: {--pc;
p &= ~P_C_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)25:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
a |= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)26:
switch(stage) {

case 1: {--pc;
data = a;
++data;
simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)27:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)28:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
data &= ~a;
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)29:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
a |= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)30:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
parent.setMLB(cis, true);
wantRead(ea);
break; } case 4: {
wantRead(ea);
break; } case 5: {
short result = (short)((data&0xFF)<<1);
data = (byte)result;
simplePUpdateNZC(result);
wantWrite(ea, data);
break; } case 6: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)31:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<1)) == 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)32:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantPush((byte)(pc >> 8));
break; } case 2: {
wantPush((byte)pc);
break; } case 3: {
wantRead(pc++);
break; } case 4: {
ea |= (short)(data << 8);
pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)33:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 4: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 5: {
a &= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)34:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
} default: stage = -1; if(true) return; } break;
    case (byte)35:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)36:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
p = (byte)((p&0x3F)|(data&0xC0));
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)37:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
a &= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)38:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
short result = (short)(((data&0xFF)<<1)|(((p&P_C_BIT)!=0)?1:0));
data = (byte)result;
simplePUpdateNZC(result);
wantWrite(ea, data);
break; } case 4: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)39:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data &= ~(1<<2);
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)40:
switch(stage) {

case 1: {--pc;
wantPop();
break; } case 2: {
p = data;
} default: stage = -1; if(true) return; } break;
    case (byte)41:
switch(stage) {

case 1: {a &= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)42:
switch(stage) {

case 1: {--pc;
data = a;
short result = (short)(((data&0xFF)<<1)|(((p&P_C_BIT)!=0)?1:0));
data = (byte)result;
simplePUpdateNZC(result);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)43:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)44:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
p = (byte)((p&0x3F)|(data&0xC0));
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)45:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
a &= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)46:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
short result = (short)(((data&0xFF)<<1)|(((p&P_C_BIT)!=0)?1:0));
data = (byte)result;
simplePUpdateNZC(result);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)47:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<2)) == 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)48:
switch(stage) {

case 1: {ea = (short)(pc + data);
taking_branch = (p&P_N_BIT) == P_N_BIT;
boolean doFixup = (pc>>8) != (ea>>8);
if(doFixup) wantRead((short)((pc&0xFF00)|(ea&0xFF)));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 2: {
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)49:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 4: {
wantRead(ea);
break; } case 5: {
a &= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)50:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 4: {
a &= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)51:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)52:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
p = (byte)((p&0x3F)|(data&0xC0));
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)53:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
a &= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)54:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
short result = (short)(((data&0xFF)<<1)|(((p&P_C_BIT)!=0)?1:0));
data = (byte)result;
simplePUpdateNZC(result);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)55:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data &= ~(1<<3);
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)56:
switch(stage) {

case 1: {--pc;
p |= P_C_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)57:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
a &= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)58:
switch(stage) {

case 1: {--pc;
data = a;
--data;
simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)59:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)60:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
p = (byte)((p&0x3F)|(data&0xC0));
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)61:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
a &= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)62:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
parent.setMLB(cis, true);
wantRead(ea);
break; } case 4: {
wantRead(ea);
break; } case 5: {
short result = (short)(((data&0xFF)<<1)|(((p&P_C_BIT)!=0)?1:0));
data = (byte)result;
simplePUpdateNZC(result);
wantWrite(ea, data);
break; } case 6: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)63:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<3)) == 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)64:
switch(stage) {

case 1: {--pc;
ea = pc;
wantRead(ea);
break; } case 2: {
wantPop();
break; } case 3: {
p = data;
wantPop();
break; } case 4: {
pc = (short)(data&0xFF);
wantPop();
break; } case 5: {
pc |= (short)(data<<8);
wantRead(pc);
break; } case 6: {
} default: stage = -1; if(true) return; } break;
    case (byte)65:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 4: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 5: {
a ^= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)66:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
} default: stage = -1; if(true) return; } break;
    case (byte)67:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)68:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
} default: stage = -1; if(true) return; } break;
    case (byte)69:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
a ^= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)70:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
short result = (short)((data&0xFF)>>1);
data = (byte)result;
simplePUpdateNZ(result);
wantWrite(ea, data);
break; } case 4: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)71:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data &= ~(1<<4);
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)72:
switch(stage) {

case 1: {--pc;
wantPush(a);
break; } case 2: {
} default: stage = -1; if(true) return; } break;
    case (byte)73:
switch(stage) {

case 1: {a ^= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)74:
switch(stage) {

case 1: {--pc;
data = a;
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
short result = (short)((data&0xFF)>>1);
data = (byte)result;
simplePUpdateNZ(result);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)75:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)76:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)77:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
a ^= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)78:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
short result = (short)((data&0xFF)>>1);
data = (byte)result;
simplePUpdateNZ(result);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)79:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<4)) == 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)80:
switch(stage) {

case 1: {ea = (short)(pc + data);
taking_branch = (p&P_V_BIT) == 0;
boolean doFixup = (pc>>8) != (ea>>8);
if(doFixup) wantRead((short)((pc&0xFF00)|(ea&0xFF)));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 2: {
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)81:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 4: {
wantRead(ea);
break; } case 5: {
a ^= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)82:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 4: {
a ^= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)83:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)84:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
} default: stage = -1; if(true) return; } break;
    case (byte)85:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
a ^= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)86:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
short result = (short)((data&0xFF)>>1);
data = (byte)result;
simplePUpdateNZ(result);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)87:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data &= ~(1<<5);
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)88:
switch(stage) {

case 1: {--pc;
p &= ~P_I_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)89:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
a ^= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)90:
switch(stage) {

case 1: {--pc;
wantPush(y);
break; } case 2: {
y = data;
} default: stage = -1; if(true) return; } break;
    case (byte)91:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)92:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
} default: stage = -1; if(true) return; } break;
    case (byte)93:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
a ^= data;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)94:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
parent.setMLB(cis, true);
wantRead(ea);
break; } case 4: {
wantRead(ea);
break; } case 5: {
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
short result = (short)((data&0xFF)>>1);
data = (byte)result;
simplePUpdateNZ(result);
wantWrite(ea, data);
break; } case 6: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)95:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<5)) == 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)96:
switch(stage) {

case 1: {--pc;
ea = pc;
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
wantPop();
break; } case 4: {
pc = (short)(data&0xFF);
wantPop();
break; } case 5: {
pc |= (short)data<<8;
break; } case 6: {
++pc;
} default: stage = -1; if(true) return; } break;
    case (byte)97:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 4: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 5: {
ea = (short)((data&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 6: {
if(((a^ea)&(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)98:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
} default: stage = -1; if(true) return; } break;
    case (byte)99:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)100:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
data = 0x00;
wantWrite(ea, data);
break; } case 2: {
} default: stage = -1; if(true) return; } break;
    case (byte)101:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea = (short)((data&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 3: {
if(((a^ea)&(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)102:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
short result = (short)(((data&0xFF)>>1)|(((p&P_C_BIT)!=0)?0x80:0));
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
data = (byte)result;
simplePUpdateNZ(result);
wantWrite(ea, data);
break; } case 4: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)103:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data &= ~(1<<6);
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)104:
switch(stage) {

case 1: {--pc;
wantPop();
break; } case 2: {
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)105:
switch(stage) {

case 1: {ea = (short)((data&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 2: {
if(((a^ea)&(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)106:
switch(stage) {

case 1: {--pc;
data = a;
short result = (short)(((data&0xFF)>>1)|(((p&P_C_BIT)!=0)?0x80:0));
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
data = (byte)result;
simplePUpdateNZ(result);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)107:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)108:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 4: {
ea |= (short)(data << 8);
pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)109:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
ea = (short)((data&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 4: {
if(((a^ea)&(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)110:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
short result = (short)(((data&0xFF)>>1)|(((p&P_C_BIT)!=0)?0x80:0));
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
data = (byte)result;
simplePUpdateNZ(result);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)111:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<6)) == 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)112:
switch(stage) {

case 1: {ea = (short)(pc + data);
taking_branch = (p&P_V_BIT) == P_V_BIT;
boolean doFixup = (pc>>8) != (ea>>8);
if(doFixup) wantRead((short)((pc&0xFF00)|(ea&0xFF)));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 2: {
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)113:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 4: {
wantRead(ea);
break; } case 5: {
ea = (short)((data&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 6: {
if(((a^ea)&(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)114:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 4: {
ea = (short)((data&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 5: {
if(((a^ea)&(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)115:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)116:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
data = 0x00;
wantWrite(ea, data);
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)117:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
ea = (short)((data&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 4: {
if(((a^ea)&(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)118:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
short result = (short)(((data&0xFF)>>1)|(((p&P_C_BIT)!=0)?0x80:0));
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
data = (byte)result;
simplePUpdateNZ(result);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)119:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data &= ~(1<<7);
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)120:
switch(stage) {

case 1: {--pc;
p |= P_I_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)121:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
ea = (short)((data&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 5: {
if(((a^ea)&(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)122:
switch(stage) {

case 1: {--pc;
wantPop();
break; } case 2: {
y = data;
} default: stage = -1; if(true) return; } break;
    case (byte)123:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)124:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 5: {
ea |= (short)(data << 8);
pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)125:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
ea = (short)((data&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 5: {
if(((a^ea)&(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)126:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
parent.setMLB(cis, true);
wantRead(ea);
break; } case 4: {
wantRead(ea);
break; } case 5: {
short result = (short)(((data&0xFF)>>1)|(((p&P_C_BIT)!=0)?0x80:0));
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
data = (byte)result;
simplePUpdateNZ(result);
wantWrite(ea, data);
break; } case 6: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)127:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<7)) == 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)128:
switch(stage) {

case 1: {ea = (short)(pc + data);
taking_branch = (p&0) == 0;
boolean doFixup = (pc>>8) != (ea>>8);
if(doFixup) wantRead((short)((pc&0xFF00)|(ea&0xFF)));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 2: {
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)129:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 4: {
ea |= (short)(data << 8);
data = a;
wantWrite(ea, data);
break; } case 5: {
} default: stage = -1; if(true) return; } break;
    case (byte)130:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
} default: stage = -1; if(true) return; } break;
    case (byte)131:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)132:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
data = y;
wantWrite(ea, data);
break; } case 2: {
} default: stage = -1; if(true) return; } break;
    case (byte)133:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
data = a;
wantWrite(ea, data);
break; } case 2: {
} default: stage = -1; if(true) return; } break;
    case (byte)134:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
data = x;
wantWrite(ea, data);
break; } case 2: {
} default: stage = -1; if(true) return; } break;
    case (byte)135:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data |= 1<<0;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)136:
switch(stage) {

case 1: {--pc;
data = y;
--data;
simplePUpdateNZ(data);
y = data;
} default: stage = -1; if(true) return; } break;
    case (byte)137:
switch(stage) {

case 1: {if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)138:
switch(stage) {

case 1: {--pc;
a = x;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)139:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)140:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
data = y;
wantWrite(ea, data);
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)141:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
data = a;
wantWrite(ea, data);
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)142:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
data = x;
wantWrite(ea, data);
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)143:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<0)) != 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)144:
switch(stage) {

case 1: {ea = (short)(pc + data);
taking_branch = (p&P_C_BIT) == 0;
boolean doFixup = (pc>>8) != (ea>>8);
if(doFixup) wantRead((short)((pc&0xFF00)|(ea&0xFF)));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 2: {
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)145:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 4: {
data = a;
wantWrite(ea, data);
break; } case 5: {
} default: stage = -1; if(true) return; } break;
    case (byte)146:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
data = a;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)147:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)148:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
data = y;
wantWrite(ea, data);
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)149:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
data = a;
wantWrite(ea, data);
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)150:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += y & 0xFF;
data = x;
wantWrite(ea, data);
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)151:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data |= 1<<1;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)152:
switch(stage) {

case 1: {--pc;
a = y;
simplePUpdateNZ(a);
} default: stage = -1; if(true) return; } break;
    case (byte)153:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
data = a;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)154:
switch(stage) {

case 1: {--pc;
s = x;
} default: stage = -1; if(true) return; } break;
    case (byte)155:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)156:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
data = 0x00;
wantWrite(ea, data);
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)157:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
data = a;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)158:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
data = 0x00;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)159:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<1)) != 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)160:
switch(stage) {

case 1: {simplePUpdateNZ(data);
y = data;
} default: stage = -1; if(true) return; } break;
    case (byte)161:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 4: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 5: {
simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)162:
switch(stage) {

case 1: {simplePUpdateNZ(data);
x = data;
} default: stage = -1; if(true) return; } break;
    case (byte)163:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)164:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
simplePUpdateNZ(data);
y = data;
} default: stage = -1; if(true) return; } break;
    case (byte)165:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)166:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
simplePUpdateNZ(data);
x = data;
} default: stage = -1; if(true) return; } break;
    case (byte)167:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data |= 1<<2;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)168:
switch(stage) {

case 1: {--pc;
y = a;
simplePUpdateNZ(y);
} default: stage = -1; if(true) return; } break;
    case (byte)169:
switch(stage) {

case 1: {simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)170:
switch(stage) {

case 1: {--pc;
x = a;
simplePUpdateNZ(x);
} default: stage = -1; if(true) return; } break;
    case (byte)171:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)172:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
simplePUpdateNZ(data);
y = data;
} default: stage = -1; if(true) return; } break;
    case (byte)173:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)174:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
simplePUpdateNZ(data);
x = data;
} default: stage = -1; if(true) return; } break;
    case (byte)175:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<2)) != 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)176:
switch(stage) {

case 1: {ea = (short)(pc + data);
taking_branch = (p&P_C_BIT) == P_C_BIT;
boolean doFixup = (pc>>8) != (ea>>8);
if(doFixup) wantRead((short)((pc&0xFF00)|(ea&0xFF)));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 2: {
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)177:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 4: {
wantRead(ea);
break; } case 5: {
simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)178:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 4: {
simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)179:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)180:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
simplePUpdateNZ(data);
y = data;
} default: stage = -1; if(true) return; } break;
    case (byte)181:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)182:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += y & 0xFF;
wantRead(ea);
break; } case 3: {
simplePUpdateNZ(data);
x = data;
} default: stage = -1; if(true) return; } break;
    case (byte)183:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data |= 1<<3;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)184:
switch(stage) {

case 1: {--pc;
p &= ~P_V_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)185:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)186:
switch(stage) {

case 1: {--pc;
x = s;
simplePUpdateNZ(x);
} default: stage = -1; if(true) return; } break;
    case (byte)187:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)188:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
simplePUpdateNZ(data);
y = data;
} default: stage = -1; if(true) return; } break;
    case (byte)189:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
simplePUpdateNZ(data);
a = data;
} default: stage = -1; if(true) return; } break;
    case (byte)190:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
simplePUpdateNZ(data);
x = data;
} default: stage = -1; if(true) return; } break;
    case (byte)191:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<3)) != 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)192:
switch(stage) {

case 1: {ea = (short)(((data^0xFF)&0xFF) + (y&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)193:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 4: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 5: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)194:
switch(stage) {

case 1: {} default: stage = -1; if(true) return; } break;
    case (byte)195:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)196:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea = (short)(((data^0xFF)&0xFF) + (y&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)197:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)198:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
--data;
simplePUpdateNZ(data);
wantWrite(ea, data);
break; } case 4: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)199:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data |= 1<<4;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)200:
switch(stage) {

case 1: {--pc;
data = y;
++data;
simplePUpdateNZ(data);
y = data;
} default: stage = -1; if(true) return; } break;
    case (byte)201:
switch(stage) {

case 1: {ea = (short)(((data^0xFF)&0xFF) + (a&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)202:
switch(stage) {

case 1: {--pc;
data = x;
--data;
simplePUpdateNZ(data);
x = data;
} default: stage = -1; if(true) return; } break;
    case (byte)203:
switch(stage) {

case 1: {--pc;
parent.setRDY(cis, false);
break; } case 2: {
parent.setRDY(cis, true);
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)204:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
ea = (short)(((data^0xFF)&0xFF) + (y&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)205:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)206:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
--data;
simplePUpdateNZ(data);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)207:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<4)) != 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)208:
switch(stage) {

case 1: {ea = (short)(pc + data);
taking_branch = (p&P_Z_BIT) == 0;
boolean doFixup = (pc>>8) != (ea>>8);
if(doFixup) wantRead((short)((pc&0xFF00)|(ea&0xFF)));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 2: {
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)209:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 4: {
wantRead(ea);
break; } case 5: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)210:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 4: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)211:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)212:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
} default: stage = -1; if(true) return; } break;
    case (byte)213:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)214:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
--data;
simplePUpdateNZ(data);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)215:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data |= 1<<5;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)216:
switch(stage) {

case 1: {--pc;
p &= ~P_D_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)217:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)218:
switch(stage) {

case 1: {--pc;
wantPush(x);
break; } case 2: {
} default: stage = -1; if(true) return; } break;
    case (byte)219:
switch(stage) {

case 1: {--pc;
stopped = true;
} default: stage = -1; if(true) return; } break;
    case (byte)220:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)221:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)222:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
parent.setMLB(cis, true);
wantRead(ea);
break; } case 4: {
wantRead(ea);
break; } case 5: {
--data;
simplePUpdateNZ(data);
wantWrite(ea, data);
break; } case 6: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)223:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<5)) != 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)224:
switch(stage) {

case 1: {ea = (short)(((data^0xFF)&0xFF) + (x&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)225:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 4: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 5: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 6: {
if(((a^ea)&~(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)226:
switch(stage) {

case 1: {} default: stage = -1; if(true) return; } break;
    case (byte)227:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)228:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea = (short)(((data^0xFF)&0xFF) + (x&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)229:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 3: {
if(((a^ea)&~(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)230:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
++data;
simplePUpdateNZ(data);
wantWrite(ea, data);
break; } case 4: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)231:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data |= 1<<6;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)232:
switch(stage) {

case 1: {--pc;
data = x;
++data;
simplePUpdateNZ(data);
x = data;
} default: stage = -1; if(true) return; } break;
    case (byte)233:
switch(stage) {

case 1: {ea = (short)(((data^0xFF)&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 2: {
if(((a^ea)&~(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)234:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)235:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)236:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
ea = (short)(((data^0xFF)&0xFF) + (x&0xFF) + 1);
simplePUpdateNZC(ea);
} default: stage = -1; if(true) return; } break;
    case (byte)237:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 3: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 4: {
if(((a^ea)&~(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)238:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
++data;
simplePUpdateNZ(data);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)239:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<6)) != 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)240:
switch(stage) {

case 1: {ea = (short)(pc + data);
taking_branch = (p&P_Z_BIT) == P_Z_BIT;
boolean doFixup = (pc>>8) != (ea>>8);
if(doFixup) wantRead((short)((pc&0xFF00)|(ea&0xFF)));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 2: {
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    case (byte)241:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 4: {
wantRead(ea);
break; } case 5: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 6: {
if(((a^ea)&~(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)242:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
break; } case 3: {
ea |= (short)(data << 8);
wantRead(ea);
break; } case 4: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 5: {
if(((a^ea)&~(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)243:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)244:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
} default: stage = -1; if(true) return; } break;
    case (byte)245:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
wantRead(ea);
break; } case 3: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 4: {
if(((a^ea)&~(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)246:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
ea += x & 0xFF;
parent.setMLB(cis, true);
wantRead(ea);
break; } case 3: {
wantRead(ea);
break; } case 4: {
++data;
simplePUpdateNZ(data);
wantWrite(ea, data);
break; } case 5: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)247:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(ea);
break; } case 2: {
wantRead(ea);
break; } case 3: {
data |= 1<<7;
wantWrite(ea, data);
break; } case 4: {
} default: stage = -1; if(true) return; } break;
    case (byte)248:
switch(stage) {

case 1: {--pc;
p |= P_D_BIT;
} default: stage = -1; if(true) return; } break;
    case (byte)249:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 5: {
if(((a^ea)&~(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)250:
switch(stage) {

case 1: {--pc;
wantPop();
break; } case 2: {
x = data;
} default: stage = -1; if(true) return; } break;
    case (byte)251:
switch(stage) {

case 1: {--pc;
} default: stage = -1; if(true) return; } break;
    case (byte)252:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
} default: stage = -1; if(true) return; } break;
    case (byte)253:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
wantRead(ea);
break; } case 4: {
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
if((p&P_D_BIT)==0) { ++stage; doInstruction(); }
break; } case 5: {
if(((a^ea)&~(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
} default: stage = -1; if(true) return; } break;
    case (byte)254:
switch(stage) {

case 1: {ea = (short)(data & 0xFF);
wantRead(pc++);
break; } case 2: {
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
if(!doFixup) { ++stage; doInstruction(); }
break; } case 3: {
parent.setMLB(cis, true);
wantRead(ea);
break; } case 4: {
wantRead(ea);
break; } case 5: {
++data;
simplePUpdateNZ(data);
wantWrite(ea, data);
break; } case 6: {
parent.setMLB(cis, false);
} default: stage = -1; if(true) return; } break;
    case (byte)255:
switch(stage) {

case 1: {ea = (short)data;
wantRead(pc++);
break; } case 2: {
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
taking_branch = (data&(1<<7)) != 0;
if(taking_branch) pc = ea;
} default: stage = -1; if(true) return; } break;
    }
  }
}
