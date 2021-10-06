package chronoMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AbstractPowerEnum {
    @SpireEnum
    public static AbstractPower.PowerType RECALL;
}

public class RecallPowerEnum {
    @SpireEnum
    public static DamageInfo.DamageType RECALL;
}
