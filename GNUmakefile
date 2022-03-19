usage:
	@echo 'Please explicitly specify which target you want to build, e.g.:'
	@echo '    make logi6502-classic.jar'
	@echo 'In the highly likely event that your Logisim jars are not in the hardcoded'
	@echo 'locations, specify them on the command line, e.g.:'
	@echo '    make logi6502-classic.jar LOGISIM_CLASSIC_PATH=/path/to/logisim-generic-2.7.1.jar'
	@false

LOGISIM_CLASSIC_PATH ?= ${HOME}/Downloads/logisim-generic-2.7.1.jar
LOGISIM_EVOLUTION_PATH ?= ${HOME}/Downloads/logisim-evolution-3.7.2-all.jar

SOURCES := \
	name/bizna/logi6502/AbstractCore.java \
	name/bizna/logi6502/Components.java \
	name/bizna/logi6502/CoreState.java \
	name/bizna/logi6502/Logi6502.java \
	name/bizna/logi6502/Logi6502Expert.java \
	name/bizna/logi6502/Logi6502Simple.java \
	name/bizna/logi6502/Logi6502_PDIP40.java \
	name/bizna/logi6502/PortInfo.java \
	name/bizna/logi6502/W65C02.java
CLASSIC_SOURCES := $(patsubst %.java, classic/src/%.java, $(SOURCES))
CLASSIC_CLASSES := $(patsubst %.java, classic/bin/%.class, $(SOURCES))
EVOLUTION_SOURCES := $(patsubst %.java, evolution/src/%.java, $(SOURCES))
EVOLUTION_CLASSES := $(patsubst %.java, evolution/bin/%.class, $(SOURCES))
MANIFEST := src/META-INF/MANIFEST.MF

showvars:
	@echo LOGISIM_CLASSIC_PATH="$(LOGISIM_CLASSIC_PATH)"
	@echo SOURCES="$(SOURCES)"
	@echo JAVA_SOURCES="$(JAVA_SOURCES)"
	@echo CLASSIC_SOURCES="$(CLASSIC_SOURCES)"
	@echo CLASSIC_CLASSES="$(CLASSIC_CLASSES)"

logi6502-classic.jar: $(CLASSIC_SOURCES) $(MANIFEST)
	@echo "Compiling $@..."
	@echo "Creating $@..."
	@javac --class-path "$(LOGISIM_CLASSIC_PATH)":classic/bin \
		-d classic/bin -s classic/src $(CLASSIC_SOURCES) \
		-Xlint:unchecked
	@jar --create --file "$@" --manifest src/META-INF/MANIFEST.MF \
		-C classic/bin .

classic/src/%.java: src/%.java preprocessor.lua
	@echo "Preprocessing $@..."
	@mkdir -p $(dir $@)
	@lua preprocessor.lua classic "$<" "$@"

logi6502-evolution.jar: $(EVOLUTION_SOURCES) $(MANIFEST)
	@echo "Compiling $@..."
	@echo "Creating $@..."
	@javac --class-path "$(LOGISIM_EVOLUTION_PATH)":evolution/bin \
		-d evolution/bin -s evolution/src $(EVOLUTION_SOURCES) \
		-Xlint:unchecked
	@jar --create --file "$@" --manifest src/META-INF/MANIFEST.MF \
		-C evolution/bin .

evolution/src/%.java: src/%.java preprocessor.lua
	@echo "Preprocessing $@..."
	@mkdir -p $(dir $@)
	@lua preprocessor.lua evolution "$<" "$@"

src/name/bizna/logi6502/W65C02.java: generate_cores.lua gensrc/addressing_modes.lua gensrc/instructions.lua gensrc/opcodes_w65c02.lua
	@echo "Generating $@..."
	@lua "$<"

clean:
	@rm -f logi6502-classic.jar logi6502-evolution.jar
	@rm -rf classic/bin evolution/bin

superclean:
	@rm -f logi6502-classic.jar logi6502-evolution.jar
	@rm -rf classic evolution

.PHONY: usage clean superclean showvars
.SECONDARY:
MAKEFLAGS += --no-builtin-rules
