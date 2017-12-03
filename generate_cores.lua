#!/usr/bin/env lua5.3

dofile("gensrc/addressing_modes.lua")
dofile("gensrc/instructions.lua")

for k,v in pairs(addressing_modes) do
   v.name = k
end

local function generate_core(pkg, name, opcodes, flags, comment)
   local sourcefile = assert(io.open("src/"..pkg:gsub("%.","/").."/"..name..".java","wb"))
   local cur_cycle = nil
   function out(...)
      if cur_cycle then
         local text = table.concat{...}
         sourcefile:write((text:gsub("%$(.[^$\n]*)%$",
                                     function(x)
                                        local rett = {}
                                        if x ~= "$" then
                                           rett[1] = "if("..x..") { ++stage; doInstruction(); }"
                                        end
                                        cur_cycle = cur_cycle + 1
                                        rett[#rett+1] = "break; } case "..cur_cycle..": {"
                                        return table.concat(rett,"\n")
         end)))
      else
         sourcefile:write(...)
      end
   end
   out("package "..pkg..";\n")
   out[[

/*
 * NOTE: This source file is automatically generated! Manually editing it is
 * pointless!
 */

]]
   out(comment)
   out("public class "..name.." extends AbstractCore {\n")
   out("  public "..name.."(Logi6502 parent) { super(parent); }\n")
   out[[
  @Override
  @SuppressWarnings("unused")
  protected void doInstruction() {
    switch(fetchedOpcode) {
]]
   for n=0,255 do
      out("    case (byte)"..n..":\n")
      out("switch(stage) {\n\ncase 1: {")
      cur_cycle = 1
      if opcodes[n][2] ~= nil then out(opcodes[n][2].init) end
      opcodes[n][1](flags, opcodes[n][2], select(3, table.unpack(opcodes[n])))
      cur_cycle = nil
      out("} default: stage = -1; if(true) return; } break;\n")
   end
   out[[
    }
  }
}
]]
   sourcefile:close()
   out = nil
end

local opcodes_w65c02 = dofile("gensrc/opcodes_w65c02.lua")
generate_core("name.bizna.logi6502", "W65C02", opcodes_w65c02, {}, [[
/**
 * This core strictly emulates a Western Design Center-branded W65C02 or
 * W65C02S. This includes the \"Rockwell bit extensions\".
 */
]])
