addressing_modes = {}

setmetatable(addressing_modes,
             {__index=function(t,k) error("Unknown addressing mode: "..k) end})

-- We could use metatables, or...
local function inherit_from(parent, child)
   for k,v in pairs(parent) do
      if child[k] == nil then child[k] = v end
   end
   return child
end

local has_ea = {
   mlbon=[[
parent.setMLB(cis, true);
]],
   mlboff=[[
parent.setMLB(cis, false);
]],
   read=[[
wantRead(ea);
$$$
]],
   write=[[
wantWrite(ea, data);
$$$
]],
   spurious_read=[[
wantRead(ea);
$$$
]],
}

-------------------------------------------------------------------------------
-- The addressing modes begin!                                               --
-------------------------------------------------------------------------------

-- Implied
-- Takes an extra cycle to read a non-existent operand byte
addressing_modes.implied = {
   init=[[
--pc;
]],
   spurious_read=[[
]],
   mlbon=[[
]],
   mlboff=[[
]],
}

-- Absolute
addressing_modes.absolute = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
wantRead(pc++);
$$$
ea |= (short)(data << 8);
]],
})

-- (Absolute,X)
addressing_modes.absolute_x_indirect = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
wantRead(pc++);
$$$
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
$!doFixup$
wantRead(ea);
$$$
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
$$$
ea |= (short)(data << 8);
]],
})

-- Absolute,X
addressing_modes.absolute_x = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
wantRead(pc++);
$$$
ea |= (short)(data << 8);
short neu = (short)(ea + (x&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
$!doFixup$
]],
})

-- Absolute,Y
addressing_modes.absolute_y = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
wantRead(pc++);
$$$
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
$!doFixup$
]],
})

-- (Absolute)
addressing_modes.absolute_indirect = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
wantRead(pc++);
$$$
ea |= (short)(data << 8);
wantRead(ea);
$$$
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
$$$
ea |= (short)(data << 8);
]],
})

-- Implied A
addressing_modes.implied_a = inherit_from(addressing_modes.implied, {
   read=[[
data = a;
]],
   write=[[
a = data;
]],
})

-- Implied X
addressing_modes.implied_x = inherit_from(addressing_modes.implied, {
   read=[[
data = x;
]],
   write=[[
x = data;
]],
})

-- Implied Y
addressing_modes.implied_y = inherit_from(addressing_modes.implied, {
   read=[[
data = y;
]],
   write=[[
y = data;
]],
})

-- Immediate
addressing_modes.immediate = {
   init=[[
]],
   read=[[
]],
   spurious_read=[[
]],
}

-- Relative
addressing_modes.relative = {
   init=[[
ea = (short)(pc + data);
]],
   -- This cycle only gets potentially burned if the branch is taken
   taking_branch=[[
boolean doFixup = (pc>>8) != (ea>>8);
if(doFixup) wantRead((short)((pc&0xFF00)|(ea&0xFF)));
$!doFixup$
]],
}

-- Relative Bit Branch
addressing_modes.relative_bit_branch = {
   init=[[
ea = (short)data;
wantRead(pc++);
$$$
byte off = data;
data = (byte)ea;
ea = (short)(pc + off);
wantRead((short)((pc&0xFF00)|(ea&0xFF)));
]],
   taking_branch=[[
]],
}

-- Zero Page
addressing_modes.zero_page = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
]],
})

-- (Zero Page,X)
addressing_modes.zero_page_x_indirect = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
wantRead(ea);
$$$
ea += x & 0xFF;
wantRead(ea);
$$$
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
$$$
ea |= (short)(data << 8);
]],
})

-- Zero Page,X
addressing_modes.zero_page_x = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
wantRead(ea);
$$$
ea += x & 0xFF;
]],
})

-- Zero Page,Y
addressing_modes.zero_page_y = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
wantRead(ea);
$$$
ea += y & 0xFF;
]],
})

-- (Zero Page)
addressing_modes.zero_page_indirect = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
wantRead(ea);
$$$
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
$$$
ea |= (short)(data << 8);
]],
})

-- (Zero Page),Y
addressing_modes.zero_page_indirect_y = inherit_from(has_ea, {
   init=[[
ea = (short)(data & 0xFF);
wantRead(ea);
$$$
wantRead((short)(ea+1));
ea = (short)(data & 0xFF);
$$$
ea |= (short)(data << 8);
short neu = (short)(ea + (y&0xFF));
boolean doFixup = (ea>>8) != (neu>>8);
ea = neu;
if(doFixup) wantRead((short)(ea-0x100));
$!doFixup$
]],
})

