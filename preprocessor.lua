#!/usr/bin/env lua

local function die_with_usage()
   print[[
Usage: preprocessor.lua <classic|evolution> input.java output.java

You don't need this to compile Logi6502 from an official source release. You
only need it if you're making changes to the code, and even then, you should be
using the Makefile instead of running this preprocessor manually.
]]
   os.exit(1)
end

if #arg ~= 3 then
   die_with_usage()
end

local active_variant = arg[1]
local inpath = arg[2]
local outpath = arg[3]

if active_variant ~= "classic" and active_variant ~= "evolution" then
   print("I don't know that variant.")
   die_with_usage()
end

local infile = assert(io.open(inpath, "rb"))

local lines = {}

local lineno = 1
local had_errors = false
local function errf(format, ...)
   io.stderr:write(
      ("%s:%i: "):format(inpath, lineno),
      format:format(...),
      "\n")
   had_errors = true
end

local SAME_LINE_TAG_RE = "//([a-z]+)\r?$"
local MULTI_LINE_TAG_RE = "^[ \t]+/%*(/?[a-z]+/?)%*/[ \t]*\r?$"

local state = nil
local one_line_only
for l in infile:lines() do
   local comment_out = false
   if state == nil then
      local tag = l:match(SAME_LINE_TAG_RE)
      if tag then
	 comment_out = tag ~= active_variant
      else
	 local tag = l:match(MULTI_LINE_TAG_RE)
	 if tag then
	    if tag:sub(1,1) == "/" then
	       errf("close tag without open tag")
	    else
	       one_line_only = tag:sub(-1,-1) == "/"
	       if one_line_only then tag = tag:sub(1,-2) end
	       state = tag
	    end
	 end
      end
   else
      local tag = l:match(MULTI_LINE_TAG_RE)
      if tag then
	 if tag:sub(1,1) ~= "/" then
	    errf("open tag while another is still open")
	    state = nil
	 elseif tag:sub(-1,-1) == "/" then
	    errf("one-liner tag while a tag is open")
	    state = nil
	 else
	    local wat = tag:sub(2,-1)
	    if wat ~= state then
	       errf("mismatched close tag")
	    end
	    state = nil
	 end
      else
	 comment_out = state ~= active_variant
	 if one_line_only then state = nil end
      end
   end
   if comment_out then
      lines[#lines+1] = "//"..l:gsub("^ ? ?","")
   else
      lines[#lines+1] = l
   end
   lineno = lineno + 1
end

if had_errors then os.exit(1) end

local outfile = assert(io.open(outpath, "wb"))
for _,l in ipairs(lines) do
   assert(outfile:write(l, "\n"))
end
