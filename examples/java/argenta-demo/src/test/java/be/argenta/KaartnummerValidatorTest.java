package be.argenta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KaartnummerValidatorTest {

    private KaartnummerValidator validator;

    @BeforeEach
    public void setUp() {
        // ARRANGE
        this.validator = new KaartnummerValidator();
    }

    @Test
    public void isValid_nietValid_indienNull() {
        boolean result = validator.isValid(null);
        Assertions.assertEquals(false, result);
    }

    @Test
    public void isValid_welValid_bijEenGeldigKaartnr() {
        boolean result = validator.isValid(new Kaartnummer("12341234567890987"));
        Assertions.assertEquals(true, result);
    }

    @Test
    public void isValid_nietValid_MeerDan17Cijfers() {
        boolean result = validator.isValid(new Kaartnummer("123412341234123412341234"));

        Assertions.assertEquals(false, result);
    }

    @Test
    public void isValid_nietValid_MinderDan17Cijfers() {
        // 2. uitvoeren => ACT
        boolean result = validator.isValid(new Kaartnummer("1234"));

        // 3. checken => ASSERT
        //Assertions.assertFalse(result);
        Assertions.assertEquals(false, result);
    }

    @Test
    public void isValid_nietValid_karaktersBuitenCijfers() {
        // 2. uitvoeren => ACT
        boolean result = validator.isValid(new Kaartnummer("aaaaaaaaaaaaaaaaa"));

        // 3. checken => ASSERT
        //Assertions.assertFalse(result);
        Assertions.assertEquals(false, result);
    }

    @Test
    public void isValid_nietValid_moduloCheckFaalt() {
        boolean result = validator.isValid(new Kaartnummer("11111111111111111"));
        Assertions.assertEquals(false, result);
    }

}
