package good;

import hc.logging.MaskTypeEnum;
import hc.logging.mask.MaskToStringBuilder;
import hc.logging.type.MaskField;
import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class MyAccount extends MyAccountBase {
    @MaskField(type = MaskTypeEnum.PHONE)
    private String name;

    @Override
    public String toString() {
        return new MaskToStringBuilder(this, super.toString()).toMaskString();
    }
}
