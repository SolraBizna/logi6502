package name.bizna.logi6502;

import com.cburch.logisim.instance.InstanceData;
import com.cburch.logisim.instance.InstanceState;

public class CoreState extends W65C02 implements InstanceData, Cloneable {
    public CoreState(Logi6502 parent) {
        super(parent);
    }
    public static CoreState get(InstanceState state, Logi6502 parent) {
        CoreState ret = (CoreState)state.getData();
        if(ret == null) {
            ret = new CoreState(parent);
            state.setData(ret);
        }
        return ret;
    }
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch(CloneNotSupportedException e) {
            return null;
        }
    }
}
