package chronoMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RecallEnum {
    @SpireEnum
    public static AbstractPower.PowerType RECALL;

    @SpireEnum
    public static DamageInfo.DamageType RECALL_DAMAGE;
}
