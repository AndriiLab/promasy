package com.github.andriilab.promasy.data.authorization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "password, 1,  19A695F29BE2AAD0DC245ADE0FB952F25670C671D4E130C0F8AA9E00BBB63ED7",
            "password, 2,  CC2E178F5A3B3B0D8864C203BEFAB1AE7698F6E781082ED1D681A2FC02F83CA3",
            "password, -1, B13E849BBE206C065BDA7A25B9FCF9F729A31614E28AFCEF6EAE881F05DFB058",
            "пароль,   1,  A38BC1C3B3893DEF3299860E9898716B26E3A2FE0A24C0DA8893A2AD4FFEB34D"
    })
    void makePass_knownInput_matchesPrecomputedSha256(String password, long salt, String expected) {
        assertThat(PasswordUtils.makePass(password.toCharArray(), salt)).isEqualTo(expected);
    }

    @Test
    void makePass_emptyPassword_hashesOnlyTheSalt() {
        assertThat(PasswordUtils.makePass(new char[0], 0L))
                .isEqualTo("AF5570F5A1810B7AF78CAF4BC70A660F0DF51E42BAF91D4DE5B2328DE0E83DFC");
    }

    @Test
    void makePass_sameInput_isDeterministic() {
        char[] password = "secret".toCharArray();

        assertThat(PasswordUtils.makePass(password, 42L)).isEqualTo(PasswordUtils.makePass(password, 42L));
    }

    @Test
    void makePass_differentSalt_givesDifferentHash() {
        char[] password = "secret".toCharArray();

        assertThat(PasswordUtils.makePass(password, 1L)).isNotEqualTo(PasswordUtils.makePass(password, 2L));
    }

    @Test
    void makePass_differentPassword_givesDifferentHash() {
        assertThat(PasswordUtils.makePass("a".toCharArray(), 1L)).isNotEqualTo(PasswordUtils.makePass("b".toCharArray(), 1L));
    }

    @Test
    void makePass_anyInput_is64UppercaseHexChars() {
        assertThat(PasswordUtils.makePass("secret".toCharArray(), Long.MIN_VALUE)).matches("[0-9A-F]{64}");
    }

    @Test
    void makeSalt_calledTwice_givesDifferentValues() {
        assertThat(PasswordUtils.makeSalt()).isNotEqualTo(PasswordUtils.makeSalt());
    }
}
