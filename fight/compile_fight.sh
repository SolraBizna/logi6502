#!/bin/sh

set -e
wla-65c02 -o fight.65c.o fight.65c
wlalink -r fight.link fight.bin
rm -f fight.65c.o
lua <<EOF >fight.txt
local i = assert(io.open("fight.bin","rb"))
io.write("v2.0 raw\n")
local runchar,runlen
repeat
  local char = i:read(1)
  if char then char = char:byte() end
  if runchar ~= char then
    if runchar then
      if runlen == 1 then io.write(("%02X\n"):format(runchar))
      else io.write(("%i*%02X\n"):format(runlen, runchar)) end
    end
    runchar,runlen = char,1
  else runlen = runlen + 1 end
until not char
EOF
