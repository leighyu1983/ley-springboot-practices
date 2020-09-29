package mask;

import hc.logging.mask.MaskUtil;
import org.junit.Assert;
import org.junit.Test;

public class MaskTest {

    @Test
    public void maskBankCardEmptyTest() {
        String val = MaskUtil.maskBankCard("");
        Assert.assertEquals("", val);

        String val1 = MaskUtil.maskBankCard(null);
        Assert.assertEquals(null, val1);

        String val2 = MaskUtil.maskBankCard(" ");
        Assert.assertEquals(" ", val2);
    }

    @Test
    public void maskBankCardNormalTest() {
        String val1 = MaskUtil.maskBankCard("62220289674123");
        Assert.assertEquals("622202****4123", val1);
    }

    @Test
    public void maskBankCardShortTest() {
        String val2 = MaskUtil.maskBankCard("896741");
        Assert.assertEquals("****41", val2);

        String val1 = MaskUtil.maskBankCard("     ");
        Assert.assertEquals("**** ", val1);
    }

    @Test
    public void maskChineseNameTest() {
        String val1 = MaskUtil.maskChineseName("李");
        Assert.assertEquals("李", val1);

        String val2 = MaskUtil.maskChineseName("李星云");
        Assert.assertEquals("李**", val2);

        String val3 = MaskUtil.maskChineseName("李志");
        Assert.assertEquals("李*", val3);
    }

    @Test
    public void maskEmailTest() {
        String val1 = MaskUtil.maskEmail("aabbcc");
        Assert.assertEquals("aabbcc", val1);

        String val2 = MaskUtil.maskEmail("a@");
        Assert.assertEquals("a@", val2);

        String val3 = MaskUtil.maskEmail("ab@");
        Assert.assertEquals("a*@", val3);

        String val4 = MaskUtil.maskEmail("a@hotmail.com");
        Assert.assertEquals("a@hotmail.com", val4);

        String val5 = MaskUtil.maskEmail("ab@hotmail.com");
        Assert.assertEquals("a*@hotmail.com", val5);

        String val6 = MaskUtil.maskEmail("abc@hotmail.com");
        Assert.assertEquals("a*c@hotmail.com", val6);

        String val7 = MaskUtil.maskEmail("aabbccdd@hotmail.com");
        Assert.assertEquals("a******d@hotmail.com", val7);
    }

    @Test
    public void maskIdCard() {
        String val1 = MaskUtil.maskIdCard("110101199912211111");
        Assert.assertEquals("1101011999****1111", val1);

        String val2 = MaskUtil.maskIdCard("9912211111");
        Assert.assertEquals("99****1111", val2);

        String val3 = MaskUtil.maskIdCard("12345");
        Assert.assertEquals("****5", val3);

        String val4 = MaskUtil.maskIdCard("1234");
        Assert.assertEquals("1234", val4);
    }

    @Test
    public void maskPhone() {
        String val1 = MaskUtil.maskPhone("13852344213");
        Assert.assertEquals("138****4213", val1);

        String val2 = MaskUtil.maskPhone("010 83554213");
        Assert.assertEquals("010 ****4213", val2);

        String val3 = MaskUtil.maskPhone("12345");
        Assert.assertEquals("****5", val3);
    }
}
