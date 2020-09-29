package mask;

import hc.logging.util.MaskUtil;
import org.junit.Assert;
import org.junit.Test;

public class MaskTest {

    @Test
    public void testMaskCommmonFailOffset() {
        try {
            String test = MaskUtil.mask("62220289674123", 8, 7);
        } catch(Exception ex) {
            Assert.assertEquals("parameter end: 7, is larger than start: 8", ex.getMessage());
            return;
        }
        Assert.fail("failed test case, this code should not reach");
    }

    @Test
    public void testMaskCommmonFailOffsetLargerThanZero() {
        try {
            String test = MaskUtil.mask("62220289674123", -1, 7);
        } catch(Exception ex) {
            Assert.assertEquals("parameter start:7 or end:-1 is less than zero, which should larger than zero", ex.getMessage());
            return;
        }
        Assert.fail("failed test case, this code should not reach");
    }

    @Test
    public void testMaskBankCardFormatWrong() {
        String test = MaskUtil.maskBankCard("123");
        Assert.assertEquals("123", test);
    }

    @Test
    public void testMaskBankCardEmpty() {
        String test = MaskUtil.maskBankCard("");
        Assert.assertEquals("", test);
    }

    @Test
    public void testMaskBankCardNull() {
        String test = MaskUtil.maskBankCard(null);
        Assert.assertEquals(null, test);
    }

    @Test
    public void testMaskBankCardCardSpaces() {
        String test = MaskUtil.maskBankCard("   ");
        Assert.assertEquals("   ", test);
    }

    @Test
    public void testMaskBankCardNormal() {
        String test = MaskUtil.maskBankCard("62220289674123");
        Assert.assertEquals("6222****674123", test);
    }

    @Test
    public void testMaskBankCardNormalLessLength() {
        String test = MaskUtil.maskBankCard("9674123");
        Assert.assertEquals("****123", test);
    }

    @Test
    public void testMaskCNNormal() {
        String test = MaskUtil.maskChineseName("里梦");
        Assert.assertEquals("里*", test);
    }

    @Test
    public void testMaskCNNormalMOre() {
        String test = MaskUtil.maskChineseName("里梦神奇的一天神奇");
        Assert.assertEquals("里********", test);
    }

    @Test
    public void testMaskCNSingle() {
        String test = MaskUtil.maskChineseName("里");
        Assert.assertEquals("里", test);
    }
}
