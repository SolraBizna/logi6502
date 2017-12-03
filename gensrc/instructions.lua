instructions = {}

setmetatable(instructions,
             {__index=function(t,k) error("Unknown instruction: "..k) end})

function instructions.BRK(flags, am)
   out[[
wantPush((byte)(pc >> 8));
$$$
wantPush((byte)pc);
$$$
wantPush((byte)(p|P_B_BIT));
$$$
p &= ~P_D_BIT;
p |= P_I_BIT;
wantVPB(true);
wantRead(vectorToPull);
$$$
pc = (short)(data & 0xFF);
wantRead((short)(vectorToPull+1));
vectorToPull = IRQ_VECTOR;
$$$
pc |= data << 8;
wantVPB(false);
]]
end

function instructions.ORA(flags, am)
   out(am.read)
   out[[
a |= data;
simplePUpdateNZ(a);
]]
end

function instructions.AND(flags, am)
   out(am.read)
   out[[
a &= data;
simplePUpdateNZ(a);
]]
end

function instructions.EOR(flags, am)
   out(am.read)
   out[[
a ^= data;
simplePUpdateNZ(a);
]]
end

function instructions.BIT(flags, am)
   out(am.read)
   out[[
p = (byte)((p&0x3F)|(data&0xC0));
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
]]
end

function instructions.BIT_immediate(flags, am)
   out(am.read)
   out[[
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
]]
end

function instructions.NOP(flags, am)
end

function instructions.TRB(flags, am)
   out(am.mlbon)
   out(am.read)
   out(am.spurious_read)
   out[[
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
data &= ~a;
]]
   out(am.write)
   out(am.mlboff)
end

function instructions.TSB(flags, am)
   out(am.mlbon)
   out(am.read)
   out(am.spurious_read)
   out[[
if((data&a) != 0) p &= ~P_Z_BIT;
else p |= P_Z_BIT;
data |= a;
]]
   out(am.write)
   out(am.mlboff)
end

function instructions.ASL(flags, am)
   out(am.mlbon)
   out(am.read)
   out(am.spurious_read)
   out[[
short result = (short)((data&0xFF)<<1);
data = (byte)result;
simplePUpdateNZC(result);
]]
   out(am.write)
   out(am.mlboff)
end

function instructions.ROL(flags, am)
   out(am.mlbon)
   out(am.read)
   out(am.spurious_read)
   out[[
short result = (short)(((data&0xFF)<<1)|(((p&P_C_BIT)!=0)?1:0));
data = (byte)result;
simplePUpdateNZC(result);
]]
   out(am.write)
   out(am.mlboff)
end

function instructions.ROR(flags, am)
   out(am.mlbon)
   out(am.read)
   out(am.spurious_read)
   out[[
short result = (short)(((data&0xFF)>>1)|(((p&P_C_BIT)!=0)?0x80:0));
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
data = (byte)result;
simplePUpdateNZ(result);
]]
   out(am.write)
   out(am.mlboff)
end

function instructions.LSR(flags, am)
   out(am.mlbon)
   out(am.read)
   out(am.spurious_read)
   out[[
if((data&1)!=0) p |= P_C_BIT;
else p &= ~P_C_BIT;
short result = (short)((data&0xFF)>>1);
data = (byte)result;
simplePUpdateNZ(result);
]]
   out(am.write)
   out(am.mlboff)
end

function instructions.RMB(flags, am, bit)
   out(am.read)
   out(am.spurious_read)
   out("data &= ~(1<<"..bit..");\n")
   out(am.write)
end

function instructions.SMB(flags, am, bit)
   out(am.read)
   out(am.spurious_read)
   out("data |= 1<<"..bit..";\n")
   out(am.write)
end

function instructions.BBR(flags, am, bit)
   out("taking_branch = (data&(1<<"..bit..")) == 0;\n")
   out(am.taking_branch)
   out("if(taking_branch) pc = ea;\n")
end

function instructions.BBS(flags, am, bit)
   out("taking_branch = (data&(1<<"..bit..")) != 0;\n")
   out(am.taking_branch)
   out("if(taking_branch) pc = ea;\n")
end

function instructions.Branch(flags, am, mask, cmp)
   out("taking_branch = (p&"..mask..") == "..cmp..";\n")
   out(am.taking_branch)
   out("if(taking_branch) pc = ea;\n")
end

function instructions.INC(flags, am)
   out(am.mlbon)
   out(am.read)
   out(am.spurious_read)
   out[[
++data;
simplePUpdateNZ(data);
]]
   out(am.write)
   out(am.mlboff)
end

function instructions.DEC(flags, am)
   out(am.mlbon)
   out(am.read)
   out(am.spurious_read)
   out[[
--data;
simplePUpdateNZ(data);
]]
   out(am.write)
   out(am.mlboff)
end

function instructions.JMP(flags, am)
   out("pc = ea;\n")
end

function instructions.ADC(flags, am)
   out(am.read)
   out[[
ea = (short)((data&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
]]
   -- BCD fixup
   out[[
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
$(p&P_D_BIT)==0$
if(((a^ea)&(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
]]
end

function instructions.SBC(flags, am)
   out(am.read)
   out[[
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF));
if((p&P_C_BIT)!=0) ++ea;
if((p&P_D_BIT)!=0) {
]]
   -- BCD fixup
   out[[
if((ea&0x0F) > 0x09) ea += 0x06;
if((ea&0xF0) > 0x90) ea += 0x60;
}
$(p&P_D_BIT)==0$
if(((a^ea)&~(data^ea)&0x80)!=0) p |= P_V_BIT;
else p &= ~P_V_BIT;
simplePUpdateNZC(ea);
a = (byte)ea;
]]
end

function instructions.CMP(flags, am)
   out(am.read)
   out[[
ea = (short)(((data^0xFF)&0xFF) + (a&0xFF) + 1);
simplePUpdateNZC(ea);
]]
end

function instructions.CPX(flags, am)
   out(am.read)
   out[[
ea = (short)(((data^0xFF)&0xFF) + (x&0xFF) + 1);
simplePUpdateNZC(ea);
]]
end

function instructions.CPY(flags, am)
   out(am.read)
   out[[
ea = (short)(((data^0xFF)&0xFF) + (y&0xFF) + 1);
simplePUpdateNZC(ea);
]]
end

function instructions.STZ(flags, am)
   out[[
data = 0x00;
]]
   out(am.write)
end

function instructions.STA(flags, am)
   out[[
data = a;
]]
   out(am.write)
end

function instructions.STX(flags, am)
   out[[
data = x;
]]
   out(am.write)
end

function instructions.STY(flags, am)
   out[[
data = y;
]]
   out(am.write)
end

function instructions.LDA(flags, am)
   out(am.read)
   out[[
simplePUpdateNZ(data);
a = data;
]]
end

function instructions.LDX(flags, am)
   out(am.read)
   out[[
simplePUpdateNZ(data);
x = data;
]]
end

function instructions.LDY(flags, am)
   out(am.read)
   out[[
simplePUpdateNZ(data);
y = data;
]]
end

function instructions.JSR(flags, am)
   out[[
ea = (short)(data & 0xFF);
wantPush((byte)(pc >> 8));
$$$
wantPush((byte)pc);
$$$
wantRead(pc++);
$$$
ea |= (short)(data << 8);
pc = ea;
]]
end

function instructions.RTI(flags, am)
   out[[
ea = pc;
wantRead(ea);
$$$
wantPop();
$$$
p = data;
]]
   if flags.more_compatible_with_6502 then
      out[[
p |= P_1_BIT;
]]
   end
   out[[
wantPop();
$$$
pc = (short)(data&0xFF);
wantPop();
$$$
pc |= (short)(data<<8);
wantRead(pc);
$$$
]]
end

function instructions.RTS(flags, am)
   out[[
ea = pc;
wantRead(ea);
$$$
wantRead(ea);
$$$
wantPop();
$$$
pc = (short)(data&0xFF);
wantPop();
$$$
pc |= (short)data<<8;
$$$
++pc;
]]
end

function instructions.PHP(flags, am)
   out[[
]]
   if flags.more_compatible_with_6502 then
      out[[
wantPush((byte)(p|P_B_BIT));
$$$
]]
   else
      out [[
wantPush(p);
$$$
]]
   end
end

function instructions.PLP(flags, am)
   out[[
wantPop();
$$$
]]
   if flags.more_compatible_with_6502 then
      out[[
p = (byte)(data | P_1_BIT);
]]
   else
      out[[
p = data;
]]
   end
end

function instructions.PHA(flags, am)
   out[[
wantPush(a);
$$$
]]
end

function instructions.PLA(flags, am)
   out[[
wantPop();
$$$
a = data;
]]
end

function instructions.PHX(flags, am)
   out[[
wantPush(x);
$$$
]]
end

function instructions.PLX(flags, am)
   out[[
wantPop();
$$$
x = data;
]]
end

function instructions.PHY(flags, am)
   out[[
wantPush(y);
$$$
y = data;
]]
end

function instructions.PLY(flags, am)
   out[[
wantPop();
$$$
y = data;
]]
end

function instructions.CLC(flags, am)
   out[[
p &= ~P_C_BIT;
]]
end

function instructions.SEC(flags, am)
   out[[
p |= P_C_BIT;
]]
end

function instructions.CLV(flags, am)
   out[[
p &= ~P_V_BIT;
]]
end

function instructions.SEV(flags, am)
   out[[
p |= P_V_BIT;
]]
end

function instructions.CLD(flags, am)
   out[[
p &= ~P_D_BIT;
]]
end

function instructions.SED(flags, am)
   out[[
p |= P_D_BIT;
]]
end

function instructions.CLI(flags, am)
   out[[
p &= ~P_I_BIT;
]]
end

function instructions.SEI(flags, am)
   out[[
p |= P_I_BIT;
]]
end

function instructions.TAX(flags, am)
   out[[
x = a;
simplePUpdateNZ(x);
]]
end

function instructions.TXA(flags, am)
   out[[
a = x;
simplePUpdateNZ(a);
]]
end

function instructions.TAY(flags, am)
   out[[
y = a;
simplePUpdateNZ(y);
]]
end

function instructions.TYA(flags, am)
   out[[
a = y;
simplePUpdateNZ(a);
]]
end

function instructions.TSX(flags, am)
   out[[
x = s;
simplePUpdateNZ(x);
]]
end

function instructions.TXS(flags, am)
   out[[
s = x;
]]
end

function instructions.WAI(flags, am)
   out[[
parent.setRDY(cis, false);
$$$
if(!parent.getIRQB(cis) && !(parent.getNMIB(cis) && !previousNMI)) --stage;
$$$
]]
end

function instructions.STP(flags, am)
   out[[
stopped = true;
]]
end
