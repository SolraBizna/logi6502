package name.bizna.logi6502;

import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;

import java.util.ArrayList;
import java.util.List;

public class Components extends Library {
    private ArrayList<Tool> tools;
    public Components() {
        try {
            tools = new ArrayList<Tool>();
            tools.add(new AddTool(new Logi6502Simple()));
            tools.add(new AddTool(new Logi6502Expert()));
            tools.add(new AddTool(new Logi6502_PDIP40()));
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public String getName() {
        return "W65C02S";
    }

    @Override
    public List<? extends Tool> getTools() {
        return tools;
    }

    /*evolution*/
    @Override
    public boolean removeLibrary(String str) {
	return true;
    }
    /*/evolution*/
}
